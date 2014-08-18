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
 * Created by gavin on 8/17/14.
 */
@Path("/dashboard")
public class DashboardResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getDashboard() {
//        Map<String,Object> map = new HashMap<String,Object>();
//
//        return new Viewable("index.mustache", map);

        URI uri = uriInfo.getBaseUriBuilder().path("dashboard.html").build();
        return Response.seeOther(uri).build();
    }

}
