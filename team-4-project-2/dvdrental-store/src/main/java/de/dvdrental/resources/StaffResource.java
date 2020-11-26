package de.dvdrental.resources;

import de.dvdrental.entities.Staff;
import de.dvdrental.repositories.StaffRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.StaffProxy;
import org.apache.commons.codec.binary.Hex;
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

@Path("/staff")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class StaffResource extends Resource {

    @Inject
    StaffRepository staffRepository;

    @Operation(summary = "Get staff")
    @APIResponse(responseCode = "200", description = "The staff with Id {id}", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = StaffProxy.class)))
    @APIResponse(responseCode = "404", description = "Staff not found")
    @GET
    @Path("/{id}")
    public Response getStaff(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Staff staff = staffRepository.get(id);
        if (staff == null)
            return NOT_FOUND_RESPONSE;

        String picture = staff.getPicture() != null ? Hex.encodeHexString(staff.getPicture()) : "";

        StaffProxy proxy = new StaffProxy(staff.isActive(), staff.getEmail(), staff.getFirstName(), staff.getStaffId(), staff.getLastName(), staff.getPassword(), picture, staff.getUsername());
        return createGetResponse(proxy, uriInfo);
    }
}
