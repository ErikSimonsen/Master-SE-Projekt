package de.dvdrental.resources;

import de.dvdrental.entities.Category;
import de.dvdrental.repositories.CategoryRepository;
import de.dvdrental.resources.interfaces.Resource;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource extends Resource {
    @Inject
    CategoryRepository categoryRepository;

    @GET
    @Operation(summary = "List of categories")
    @APIResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = String[].class)))
    public Response getCategories() {
        List<Category> categories = categoryRepository.getAll();
        String[] categoryNames = categories.stream()
                .map(Category::getName)
                .toArray(String[]::new);
        return createOkResponse(categoryNames);
    }
}
