package de.dvdrental.resources;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Payment;
import de.dvdrental.repositories.AddressRepository;
import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.CustomerProxy;
import de.dvdrental.resources.utils.Href;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/customers")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class CustomerResource extends Resource {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    AddressRepository addressRepository;

    @Operation(summary = "Create a new customer.", description = "Create a new customer.")
    @APIResponse(responseCode = "201", description = "Customer created.")
    @APIResponse(responseCode = "404", description = "Address not found")
    @APIResponse(responseCode = "400", description = "NotNull contraints violation.")
    @POST
    public Response createCustomer(@Parameter(required = true) @QueryParam("address") @NotNull Integer addressId,
                                   @Parameter(required = true) @QueryParam("store") @NotNull Integer storeId,
                                   @RequestBody(description = "The customer data", required = true)
                                       @Valid @NotNull CustomerProxy proxy,
                                   @Context UriInfo uriInfo) {
        Address address = addressRepository.get(addressId);
        if (address == null)
            return NOT_FOUND_RESPONSE;

        LocalDate timestamp = proxy.getCreateDate();
        if (timestamp == null)
            timestamp = LocalDate.now();

        Customer customer = new Customer(storeId,
                proxy.getFirstName(),
                proxy.getLastName(),
                proxy.getEmail(),
                proxy.getActivebool(),
                timestamp,
                proxy.getActive(),
                address);
        customerRepository.create(customer);
        return createPostResponse(customer.getCustomerId(), uriInfo);
    }

    @Operation(summary = "Get customer with Id")
    @APIResponse(responseCode = "200", description = "The customer with Id {id}",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CustomerProxy.class)))
    @APIResponse(responseCode = "404", description = "Customer not found")
    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Customer customer = customerRepository.get(id);
        if (customer == null)
            return NOT_FOUND_RESPONSE;

        URI address = uriInfo.getBaseUriBuilder()
                                .path("addresses")
                                .path(Integer.toString(customer.getAddress().getAddressId()))
                                .build();

        Href store = storeUri.map(s -> new Href(UriBuilder.fromPath(s)
                .path("stores")
                .path(Integer.toString(customer.getStoreID()))
                .build())).orElseGet(Href::new);
        CustomerProxy proxy = new CustomerProxy(customer.getActive(), customer.isActivebool(), new Href(address),
                 customer.getCreateDate(), customer.getEmail(), customer.getFirstName(), customer.getCustomerId(), customer.getLastName(), store);

        return createGetResponse(proxy, uriInfo);
    }

    @Operation(summary = "Payments of customer with Id")
    @APIResponse(responseCode = "200", description = "Payments of customer with Id")
    @APIResponse(responseCode = "404", description = "Customer not found")
    @GET
    @Path("/{id}/payments")
    public Response getPayments(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Customer customer = customerRepository.get(id);
        if (customer == null)
            return NOT_FOUND_RESPONSE;
        List<Payment> payments = customerRepository.getPayments(customer);
        List<Href> paymentHrefs = payments.stream().map(payment -> uriInfo.getBaseUriBuilder()
                                                                          .path("payments")
                                                                          .path(Integer.toString(payment.getPaymentId()))
                                                                          .build())
                                                   .map(Href::new)
                                                   .collect(Collectors.toList());
        return Response.ok(paymentHrefs).build();
    }

    @Operation(summary = "Number of customers")
    @APIResponse(responseCode = "200", description = "Number of customers")
    @GET
    @Path("/count")
    public Long count() {
        return customerRepository.count();
    }
}
