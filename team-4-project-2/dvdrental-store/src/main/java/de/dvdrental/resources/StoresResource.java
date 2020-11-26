package de.dvdrental.resources;

import de.dvdrental.entities.Store;
import de.dvdrental.repositories.StoreRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.StoreProxy;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/stores")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class StoresResource extends Resource {

    @Inject
    StoreRepository storeRepository;

    @Operation(summary = "Number of stores")
    @APIResponse(responseCode = "200", description = "Number of stores")
    @GET
    @Path("/count")
    public Long count() {
        return storeRepository.count();
    }

    @Operation(summary = "Get store")
    @APIResponse(responseCode = "200", description = "The Store with Id {id}",
            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = StoreProxy.class)))
    @APIResponse(responseCode = "404", description = "Store not found")
    @GET
    @Path("/{id}")
    public Response getStore(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Store store = storeRepository.get(id);
        if (store == null)
            return NOT_FOUND_RESPONSE;
        StoreProxy proxy = new StoreProxy(store.getStoreId());
        return createGetResponse(proxy, uriInfo);
    }
}
