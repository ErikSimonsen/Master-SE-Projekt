package de.dvdrental.resources;

import de.dvdrental.entities.City;
import de.dvdrental.repositories.CityRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.CityProxy;
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

@Path("/city")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class CityResource extends Resource {

    @Inject
    CityRepository cityRepository;

    @Operation(summary = "Get all cities")
    @APIResponse(responseCode = "200", description = "All cities", content = @Content(schema = @Schema(implementation = CityProxy[].class)))
    @GET
    public Response getAllCities() {
        List<CityProxy> proxies = cityRepository.getAll().stream()
                .map(city -> new CityProxy(city.getCity(), city.getCountry().getCountry(), city.getCityId())).collect(Collectors.toList());
        return createOkResponse(proxies);
    }

    @Operation(summary = "Get city with Id")
    @APIResponse(responseCode = "200", description = "The city with Id {id}", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CityProxy.class)))
    @APIResponse(responseCode = "404", description = "City not found")
    @GET
    @Path("/{id}")
    public Response getAddress(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        City city = cityRepository.get(id);
        if (city == null)
            return NOT_FOUND_RESPONSE;
        CityProxy proxy = new CityProxy(city.getCity(), city.getCountry().getCountry(), city.getCityId());
        return createGetResponse(proxy, uriInfo);

    }
}
