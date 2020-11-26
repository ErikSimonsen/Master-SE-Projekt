package de.dvdrental.resources;

import de.dvdrental.entities.Film;
import de.dvdrental.resources.interfaces.Resource;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingResource extends Resource {

    @Operation(summary = "List of all ratings", description = "List of all Ratings")
    @APIResponse(content = @Content(schema = @Schema(implementation = String[].class)))
    @GET
    public Response getAllRatings() {
        String[] ratings = Arrays.stream(Film.Rating.values()).map(Film.Rating::getShortName).toArray(String[]::new);
        return createOkResponse(ratings);
    }
}
