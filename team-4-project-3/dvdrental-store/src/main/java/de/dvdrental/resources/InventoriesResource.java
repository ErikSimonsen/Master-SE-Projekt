package de.dvdrental.resources;

import de.dvdrental.entities.Inventory;
import de.dvdrental.repositories.InventoryRepository;
import de.dvdrental.repositories.StoreRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.InventoryStatusProxy;
import de.dvdrental.resources.proxies.InventoryProxy;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/inventories")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class InventoriesResource extends Resource {
    @Inject
    InventoryRepository inventoryRepository;
    @Inject
    StoreRepository storeRepository;

    @Operation(summary = "Get inventory with Id")
    @APIResponse(responseCode = "200", description = "Inventory with Id {id}", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = InventoryProxy.class)))
    @APIResponse(responseCode = "404", description = "Inventory not found")
    @GET
    @Path("/{id}")
    public Response getInventory(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Inventory inventory = inventoryRepository.get(id);
        if (inventory == null)
            return NOT_FOUND_RESPONSE;
        InventoryProxy proxy = new InventoryProxy(inventory.getInventoryId());
        return createGetResponse(proxy, uriInfo);
    }

    @Operation(summary = "Create a new inventory")
    @APIResponse(responseCode = "201", description = "Inventory created")
    @APIResponse(responseCode = "404", description = "Store not found")
    @POST
    public Response createInventory(@QueryParam("storeId") Integer storeId, @QueryParam("filmId") Integer filmId, @Context UriInfo uriInfo) {
        if (!storeRepository.existsById(storeId))
            return NOT_FOUND_RESPONSE;
        Inventory inventory = new Inventory(filmId, storeId);
        inventoryRepository.create(inventory);
        return createPostResponse(inventory.getInventoryId(), uriInfo);
    }

    @Operation(summary = "Get inventory status of a given film and store")
    @APIResponse(responseCode = "200", description = "inventory status of a given film and store", content = @Content(schema = @Schema(implementation = InventoryStatusProxy.class)))
    //@APIResponse(responseCode = "404", description = "Film has no inventory")
    @GET
    @Path("/film/{filmId}/store/{storeId}")
    public Response getFilmStatus(@PathParam("filmId") Integer filmId, @PathParam("storeId") Integer storeId, @Context UriInfo uriInfo) {
        long stockNumber = inventoryRepository.getStockNumber(filmId, storeId);
        long numberOfRentals = inventoryRepository.getNumberOfRents(filmId, storeId);
        boolean available = numberOfRentals < stockNumber;
        List<InventoryProxy> freeInventories = inventoryRepository
                .getFreeInventory(filmId, storeId).stream()
                .map(inventory -> new InventoryProxy(inventory.getInventoryId()))
                .collect(Collectors.toList());
        InventoryStatusProxy proxy = new InventoryStatusProxy(numberOfRentals, stockNumber, available, freeInventories);
        return createGetResponse(proxy, uriInfo);

    }
}
