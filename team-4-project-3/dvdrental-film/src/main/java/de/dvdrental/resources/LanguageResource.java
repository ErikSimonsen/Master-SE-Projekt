package de.dvdrental.resources;

import de.dvdrental.entities.Language;
import de.dvdrental.repositories.LanguageRepository;
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

@Path("/languages")
@Produces(MediaType.APPLICATION_JSON)
public class LanguageResource extends Resource {
    @Inject
    LanguageRepository languageRepository;

    @GET
    @Operation(summary = "List of languages")
    @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String[].class)))
    public Response getLanguages() {
        List<Language> languages = languageRepository.getAll();
        String[] languageNames = languages.stream()
                .map(Language::getName)
                .toArray(String[]::new);
        return createOkResponse(languageNames);
    }
}
