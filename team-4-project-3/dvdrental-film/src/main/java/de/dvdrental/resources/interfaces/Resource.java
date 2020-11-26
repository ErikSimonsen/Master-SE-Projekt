package de.dvdrental.resources.interfaces;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public abstract class Resource {
    protected final Response NOT_FOUND_RESPONSE = Response.status(Response.Status.NOT_FOUND).build();
    protected final Response EMPTY_OK_RESPONSE = Response.ok().build();

    public Response createGetResponse(Object entity, UriInfo uriInfo) {
        Link link = Link.fromUri(uriInfo.getAbsolutePath())
                .rel("self")
                .type("GET")
                .build();

        return Response
                .ok(entity)
                .links(link)
                .build();
    }


    /**
     * Returns a Response with the Status code 201 and the Location of the newly created Resource in the HTTP-Location Field
     */
    public Response createPostResponse(int createdResourceId, UriInfo uriInfo) {
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(createdResourceId));
        URI createdResourceLocation = builder.build();
        return Response.created(createdResourceLocation).build();
    }

    public Response createOkResponse(Object object) {
        return Response.ok(object).build();
    }
}
