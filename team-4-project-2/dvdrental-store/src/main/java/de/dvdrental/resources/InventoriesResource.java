package de.dvdrental.resources;

import de.dvdrental.entities.Inventory;
import de.dvdrental.repositories.InventoryRepository;
import de.dvdrental.resources.interfaces.Resource;
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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/inventories")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class InventoriesResource extends Resource {
    @Inject
    InventoryRepository inventoryRepository;

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


}
