package io.eventstack.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by gavin on 8/11/14.
 */
@Path("/")
public class RootResource {

    @GET
    public Response getRoot() {
        return Response.ok("Configurator 1.0").build();
    }

}
