package de.dvdrental.resources;

import de.dvdrental.entities.Actor;
import de.dvdrental.repositories.ActorRepository;
import de.dvdrental.resources.interfaces.Resource;
import de.dvdrental.resources.proxies.ActorFilmsProxy;
import de.dvdrental.resources.proxies.ActorGetProxy;
import de.dvdrental.resources.proxies.ActorPostProxy;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/actors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActorResource extends Resource {
    @Inject
    ActorRepository actorRepository;

    @GET
    @Operation(summary = "List of actors", description = "Returns list of actors (max 100)")
    @APIResponse(responseCode = "200", description = "List of Actors returned.",
            content = @Content(schema = @Schema(description = "POJO that represents an actor.", implementation = ActorGetProxy.class)))
    public Response getActors(@Context UriInfo uriInfo) {
        List<Actor> actors = actorRepository.getAll(100);
        List<ActorGetProxy> actorProxies = actors.stream().map(actor -> {
            URI filmsUri = uriInfo.getBaseUriBuilder()
                    .path("actors")
                    .path(Integer.toString(actor.getActorId()))
                    .path("films").build();
            return new ActorGetProxy(actor.getFirstName(), actor.getLastName(), actor.getActorId(), new Href(filmsUri));
        }).collect(Collectors.toList());
        return createOkResponse(actorProxies);
    }

    @POST
    @Operation(summary = "Create new Actor")
    @APIResponse(responseCode = "200", description = "OK")
    public Response createActor(@RequestBody(description = "Actor data") @Valid @NotNull ActorPostProxy actorProxy, @Context UriInfo uriInfo) {
        Actor actor = new Actor(actorProxy.getFirstName(), actorProxy.getLastName());
        actorRepository.create(actor);
        return createPostResponse(actor.getActorId(), uriInfo);
    }

    @Path("/count")
    @GET
    @Operation(summary = "Number of actors")
    @APIResponse(responseCode = "200", description = "OK")
    public Long countActors() {
        return actorRepository.count();
    }

    @Path("/{id}")
    @GET
    @Operation(summary = "Actor with Id")
    @APIResponse(responseCode = "200", description = "The actor with Id {id}",
            content = @Content(schema = @Schema(description = "POJO that represents an actor.", implementation = ActorGetProxy.class)))
    @APIResponse(responseCode = "404", description = "Actor not found")
    public Response getActor(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Actor actor = actorRepository.get(id);
        if (actor == null)
            return NOT_FOUND_RESPONSE;
        URI filmsUri = uriInfo.getBaseUriBuilder()
                .path("actors")
                .path(Integer.toString(actor.getActorId()))
                .path("films").build();
        ActorGetProxy actorProxy = new ActorGetProxy(actor.getFirstName(), actor.getLastName(), actor.getActorId(), new Href(filmsUri));
        return createGetResponse(actorProxy, uriInfo);
    }

    @Path("/{id}")
    @PUT
    @Operation(summary = "Update actor")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "Actor not found") //not in API-Specification
    public Response updateActor(@PathParam("id") Integer id, @Valid @NotNull ActorPostProxy actorProxy, @Context UriInfo uriInfo) {
        Actor actor = actorRepository.get(id);
        if (actor == null)
            return NOT_FOUND_RESPONSE;
        actor.setFirstName(actorProxy.getFirstName());
        actor.setLastName(actorProxy.getLastName());
        actorRepository.update(actor);
        return EMPTY_OK_RESPONSE;
    }

    @Path("/{id}")
    @DELETE
    @Operation(summary = "Delete actor with Id")
    @APIResponse(description = "Actor deleted")
    @APIResponse(responseCode = "404", description = "Actor not found")
    public Response deleteActor(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Actor actor = actorRepository.get(id);
        if (actor == null)
            return NOT_FOUND_RESPONSE;
        actorRepository.delete(actor);
        return EMPTY_OK_RESPONSE;
    }

    @Path("/{id}/films")
    @GET
    @Operation(summary = "Films of actor with Id")
    @APIResponse(responseCode = "200", description = "Films of actor with Id {id}")
    @APIResponse(responseCode = "404", description = "Actor not found")
    //APIResponse not further specified (especially Schema)
    public Response getActorFilms(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Actor actor = actorRepository.get(id);
        if (actor == null)
            return NOT_FOUND_RESPONSE;
        //for each Film, create a new ActorFilm Proxy Object and fill it with values of the Film
        List<ActorFilmsProxy> actorFilmProxies = actor.getFilms().stream().map(film ->  {
            URI filmUri = uriInfo.getBaseUriBuilder()
                    .path("films")
                    .path(Integer.toString(film.getFilmId()))
                    .build();
            return new ActorFilmsProxy(film.getTitle(), filmUri);
        }).collect(Collectors.toList());
        return createOkResponse(actorFilmProxies);
    }
}
