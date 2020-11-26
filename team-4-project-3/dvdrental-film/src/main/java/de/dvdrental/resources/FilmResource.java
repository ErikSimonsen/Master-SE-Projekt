package de.dvdrental.resources;

import de.dvdrental.entities.Actor;
import de.dvdrental.entities.Category;
import de.dvdrental.entities.Film;
import de.dvdrental.entities.Language;
import de.dvdrental.repositories.ActorRepository;
import de.dvdrental.repositories.CategoryRepository;
import de.dvdrental.repositories.FilmRepository;
import de.dvdrental.repositories.LanguageRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.FilmActorsProxy;
import de.dvdrental.resources.proxies.FilmProxy;
import de.dvdrental.resources.utils.Href;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/films")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilmResource extends Resource {
    @Inject
    FilmRepository filmRepository;
    @Inject
    LanguageRepository languageRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ActorRepository actorRepository;

    @GET
    @Path("/search/{search}")
    public Response searchFilms(@PathParam("search") String search,
                                @QueryParam("limit") @Max(100) @DefaultValue(value = "100") Integer limit,
                                @QueryParam("offset") @DefaultValue("0") Integer offset,
                                @Context UriInfo uriInfo){
        List<Film> films = filmRepository.searchAll(search, limit, offset);
        List<FilmProxy> filmProxies = films.stream().map(film -> {
            URI filmsUri = uriInfo.getBaseUriBuilder()
                    .path("films")
                    .path(Integer.toString(film.getFilmId()))
                    .path("actors").build();
            return new FilmProxy(film, new Href(filmsUri));
        }).collect(Collectors.toList());
        return createOkResponse(filmProxies);
    }

    @GET
    @Operation(summary = "List of films", description = "Returns list of films (max 100)")
    @APIResponse(content = @Content(schema = @Schema(description = "POJO that represents a film.", implementation = FilmProxy.class)))
    public Response getFilms(@QueryParam("limit") @Max(100) @DefaultValue(value = "100") Integer limit,
                             @QueryParam("offset") @DefaultValue("0") Integer offset, @Context UriInfo uriInfo) {
        List<Film> films = filmRepository.getAll(limit, offset);
        List<FilmProxy> filmProxies = films.stream().map(film -> {
            URI filmsUri = uriInfo.getBaseUriBuilder()
                    .path("films")
                    .path(Integer.toString(film.getFilmId()))
                    .path("actors").build();
            return new FilmProxy(film, new Href(filmsUri));
        }).collect(Collectors.toList());
        return createOkResponse(filmProxies);
    }

    @POST
    @Operation(summary = "Create a new film", description = "Create a new film. Use /films/{id}/actor/{actorId} to add an actor.")
    @APIResponse(responseCode = "201", description = "Film created")
    @APIResponse(responseCode = "404", description = "Language not found")
    public Response createFilm(@RequestBody(required = true) @Valid @NotNull FilmProxy filmProxy, @Context UriInfo uriInfo) {
        Language language = languageRepository.getByName(filmProxy.getLanguage());
        if (language == null)
            return NOT_FOUND_RESPONSE;
        Film film = new Film();
        film.setTitle(filmProxy.getTitle());
        film.setDescription(filmProxy.getDescription());
        film.setReleaseYear(Year.of(filmProxy.getReleaseYear()));
        film.setRentalRate(filmProxy.getRentalRate());
        film.setRentalDuration(filmProxy.getRentalDuration());
        film.setReplacementCost(filmProxy.getReplacementCost());
        film.setLength(filmProxy.getLength());
        film.setRating(Film.Rating.fromString(filmProxy.getRating()));
        film.setLanguage(language);
        Set<Category> categoryHashSet = filmProxy.getCategories().stream()
                                                .map(s ->  categoryRepository.getByName(s))
                                                .collect(Collectors.toSet());
        film.setCategories(categoryHashSet);
        filmRepository.create(film);
        return createPostResponse(film.getFilmId(), uriInfo);
    }

    @Path("/count")
    @GET
    @Operation(summary = "Number of Films")
    @APIResponse(responseCode = "200", description = "OK")
    public Long countFilms() {
        return filmRepository.count();
    }

    @Path("/{id}")
    @GET
    @Operation(summary = "Get film with Id")
    @APIResponse(description = "The film with Id {id}", content = @Content(schema = @Schema(implementation = FilmProxy.class)))
    @APIResponse(responseCode = "404", description = "Film not found.")
    public Response getFilm(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Film film = filmRepository.get(id);
        if (film == null)
            return NOT_FOUND_RESPONSE;
        URI filmActorsUri = uriInfo.getBaseUriBuilder()
                .path("films")
                .path(Integer.toString(film.getFilmId()))
                .path("actors").build();
        FilmProxy filmProxy = new FilmProxy(film, new Href(filmActorsUri));
        return createGetResponse(filmProxy, uriInfo);
    }

    @Path("/{id}/actors")
    @GET
    @Operation(summary = "Actors of film with Id")
    @APIResponse(description = "Actors of film with Id")
    @APIResponse(responseCode = "404", description = "Film not found")
    public Response getFilmActors(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Film film = filmRepository.get(id);
        if (film == null)
            return NOT_FOUND_RESPONSE;
        List<FilmActorsProxy> filmActorsProxyList = film.getActors().stream().map(actor -> {
            URI filmActorsUri = uriInfo.getBaseUriBuilder()
                    .path("actors")
                    .path(Integer.toString(actor.getActorId()))
                    .build();
            return new FilmActorsProxy(actor, filmActorsUri);
        }).collect(Collectors.toList());
        return createOkResponse(filmActorsProxyList);
    }

    @Path("/{id}/actors/{actorId}")
    @PUT
    @Operation(summary = "Add actor to film.")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "film or actor not found")
    public Response addActor(@PathParam("id") Integer id, @PathParam("actorId") Integer actorId, @Context UriInfo uriInfo) {
        Film film = filmRepository.get(id);
        Actor actor = actorRepository.get(actorId);
        if (film == null || actor == null)
            return NOT_FOUND_RESPONSE;
        film.getActors().add(actor);
        filmRepository.update(film);
        return EMPTY_OK_RESPONSE;
    }

    @Path("/{id}/categories/{category}")
    @PUT
    @Operation(summary = "Add category to film.")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "film or category not found")
    public Response addCategory(@PathParam("id") Integer id, @PathParam("category") String categoryName, @Context UriInfo uriInfo) {
        Film film = filmRepository.get(id);
        Category category = categoryRepository.getByName(categoryName);
        if (film == null || category == null)
            return NOT_FOUND_RESPONSE;
        film.getCategories().add(category);
        filmRepository.update(film);
        return EMPTY_OK_RESPONSE;
    }
}
