package io.eventstack.configurator.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by gavin on 8/11/14.
 */
@Path("/")
public class RootResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getRoot() {
        URI uri = uriInfo.getBaseUriBuilder().path("login.html").build();
        return Response.seeOther(uri).build();
    }

}
