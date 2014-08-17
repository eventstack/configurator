package io.eventstack.configurator.rest.resources;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 8/17/14.
 */
@Path("/dashboard")
public class DashboardResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getRoot() {
        Map<String,Object> map = new HashMap<String,Object>();

        return new Viewable("index.mustache", map);
    }

}
