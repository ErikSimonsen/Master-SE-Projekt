package de.dvdrental.resources;

import de.dvdrental.entities.Customer;
import de.dvdrental.entities.Payment;
import de.dvdrental.repositories.CustomerRepository;
import de.dvdrental.repositories.PaymentRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.PaymentGetProxy;
import de.dvdrental.resources.proxies.PaymentPostProxy;
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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/payments")
public class PaymentResource extends Resource {
    @Inject
    PaymentRepository paymentRepository;
    @Inject
    CustomerRepository customerRepository;

    @Operation(summary = "Create payment")
    @APIResponse(responseCode = "201", description = "Payment created")
    @APIResponse(responseCode = "404", description = "Customer does not exist")
    @APIResponse(responseCode = "400", description = "Not null constraints violation")
    @POST
    public Response createPayment(@RequestBody(description = "The payment data", required = true, content = @Content(example = "{\"amount\": 3.45, \"customer\": 5,\"date\": \"2020-02-30 11:56\",\"rental\": 10,\"staff\": 1}"))
                                      @Valid @NotNull PaymentPostProxy proxy,
                                  @Context UriInfo uriInfo) {
        Customer customer = customerRepository.get(proxy.getCustomer());
        if (customer == null)
            return NOT_FOUND_RESPONSE;
        Payment payment = new Payment(proxy.getAmount(), proxy.getDate(), customer, proxy.getStaff(), proxy.getRental());
        paymentRepository.create(payment);
        return createPostResponse(payment.getPaymentId(), uriInfo);
    }

    @Operation(summary = "Get payment with id")
    @APIResponse(responseCode = "200", description = "The payment with Id {id}", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = PaymentGetProxy.class)))
    @APIResponse(responseCode = "404", description = "Payment not found")
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Payment payment = paymentRepository.get(id);
        if (payment == null)
            return NOT_FOUND_RESPONSE;

        Href customer = new Href(uriInfo.getBaseUriBuilder()
                .path("customers")
                .path(Integer.toString(payment.getCustomer().getCustomerId()))
                .build());
        Href rental = storeUri.map(s -> new Href(UriBuilder
                .fromPath(s)
                .path("rentals")
                .path(Integer.toString(payment.getRentalId()))
                .build())).orElseGet(Href::new);
        Href staff = storeUri.map(s -> new Href(UriBuilder
                .fromPath(s)
                .path("staff")
                .path(Integer.toString(payment.getStaffId()))
                .build())).orElseGet(Href::new);

        PaymentGetProxy proxy = new PaymentGetProxy(payment.getAmount(), customer, staff, rental, payment.getPaymentId());
        return createGetResponse(proxy, uriInfo);
    }
}
