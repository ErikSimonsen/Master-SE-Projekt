package de.dvdrental.resources;

import de.dvdrental.entities.Inventory;
import de.dvdrental.entities.Rental;
import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.InventoryRepository;
import de.dvdrental.repositories.RentalRepository;
import de.dvdrental.repositories.StaffRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.RentalGetProxy;
import de.dvdrental.resources.proxies.RentalPostProxy;
import de.dvdrental.resources.utils.Href;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.time.LocalDateTime;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/rentals")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class RentalsResource extends Resource {

    @Inject
    RentalRepository rentalRepository;
    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    StaffRepository staffRepository;

    @Operation(summary = "Create rental")
    @APIResponse(responseCode = "201", description = "Rental created")
    @APIResponse(responseCode = "404", description = "Inventory or Staff does not exist")
    @APIResponse(responseCode = "400", description = "Not null constraints violation")
    @APIResponse(responseCode = "409", description = "idx_unq_rental_rental_date_inventory_id_customer_id constraint violation")
    @POST
    public Response createRental(@RequestBody(description = "The rental data", required = true,
                                              content = @Content(example = " {\"inventory\":234,\"customer\":217,\"staff\":1,\"date\":\"2020-04-06 15:09\"}"))
                                    @Valid @NotNull RentalPostProxy proxy,
                                 @Context UriInfo uriInfo) {
        Inventory inventory = inventoryRepository.get(proxy.getInventory());
        Staff staff = staffRepository.get(proxy.getStaff());
        if (inventory == null || staff == null)
            return NOT_FOUND_RESPONSE;
        Rental rental = new Rental(proxy.getDate(), inventory, staff, proxy.getCustomer());
        if (rentalRepository.checkIfViolatesConstraint(rental))
            return Response.status(Response.Status.CONFLICT).build();

        rentalRepository.create(rental);
        return createPostResponse(rental.getRentalId(), uriInfo);
    }

    @Operation(summary = "Get rental")
    @APIResponse(responseCode = "200", description = "The rental with Id {id}", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = RentalGetProxy.class)))
    @APIResponse(responseCode = "404", description = "Rental not found")
    @GET
    @Path("/{id}")
    public Response getRental(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Rental rental = rentalRepository.get(id);
        if (rental == null)
            return NOT_FOUND_RESPONSE;

        Href customer = customerUri.map(s -> new Href(UriBuilder.fromPath(s)
                .path("customers")
                .path(Integer.toString(rental.getCustomerId()))
                .build())).orElseGet(Href::new);
        Href film = filmUri.map(s -> new Href(UriBuilder.fromPath(s)
                .path("films")
                .path(Integer.toString(rental.getInventory().getFilmId()))
                .build())).orElseGet(Href::new);
        Href store = new Href(uriInfo.getBaseUriBuilder()
                .path("stores")
                //.path(Integer.toString(rental.getStaff().getStore().getStoreId()))
                .path(Integer.toString(rental.getInventory().getStoreId()))
                .build());

        RentalGetProxy proxy = new RentalGetProxy(customer, film, rental.getRentalDate(), rental.getRentalId(), rental.getReturnDate(), store);
        return createGetResponse(proxy, uriInfo);
    }

    @Operation(summary = "Rental terminated")
    @APIResponse(responseCode = "200", description = "Rental got terminated")
    @APIResponse(responseCode = "404", description = "Rental not found")
    @PUT
    @Path("/{id}/returned")
    public Response returnRental(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Rental rental = rentalRepository.get(id);
        if (rental == null)
            return NOT_FOUND_RESPONSE;
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.update(rental);
        return createOkResponse(uriInfo
                .getBaseUriBuilder()
                .path("rentals")
                .path(Integer.toString(id))
                .build());
    }


}
