package de.dvdrental.resources;

import de.dvdrental.entities.Address;
import de.dvdrental.entities.City;
import de.dvdrental.entities.Country;
import de.dvdrental.repositories.AddressRepository;
import de.dvdrental.repositories.CityRepository;
import de.dvdrental.repositories.CountryRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.AddressProxy;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/addresses")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AddressResource extends Resource {

    @Inject
    AddressRepository addressRepository;
    @Inject
    CityRepository cityRepository;
    @Inject
    CountryRepository countryRepository;

    @Operation(summary = "Create a new address")
    @APIResponse(responseCode = "201", description = "Address created")
    @APIResponse(responseCode = "400", description = "NotNull contraints violation.")
    @APIResponse(responseCode = "404", description = "Country and/or city do not exist")
    @POST
    public Response createAddress(@RequestBody(description = "The address data", required = true)
                                  @Valid @NotNull AddressProxy addressProxy,
                                  @Context UriInfo uriInfo) {
        Country country = countryRepository.getCountryByName(addressProxy.getCountry());
        if (country == null)
            return NOT_FOUND_RESPONSE;

        City city = cityRepository.getCityByNameAndCountry(addressProxy.getCity(), country);
        if (city == null)
            return NOT_FOUND_RESPONSE;

        Address address = new Address(addressProxy.getAddress(), addressProxy.getAddress2(), addressProxy.getDistrict(),
                                      addressProxy.getPostalCode(), addressProxy.getPhone(), city);
        addressRepository.create(address);
        return createPostResponse(address.getAddressId(), uriInfo);
    }

    @Operation(summary = "Get address with Id")
    @APIResponse(responseCode = "200", description = "The address with Id {id}", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = AddressProxy.class)))
    @APIResponse(responseCode = "404", description = "Address not found")
    @GET
    @Path("/{id}")
    public Response getAddress(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Address address = addressRepository.get(id);
        if (address == null)
            return NOT_FOUND_RESPONSE;
        AddressProxy proxy = new AddressProxy(address.getAddress(), address.getAddress2(), address.getCity().getCity(),
                address.getCity().getCountry().getCountry(), address.getDistrict(), address.getAddressId(),
                address.getPhone(), address.getPostalCode());
        return createGetResponse(proxy, uriInfo);
    }
}
