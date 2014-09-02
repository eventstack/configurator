package io.eventstack.configurator.rest.resources;

import io.eventstack.configurator.rest.exception.InvalidTokenException;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 8/17/14.
 */
@Path("/dashboard")
public class DashboardResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard(@CookieParam("sid") String sid) throws UnknownHostException, InvalidTokenException {

        SessionUtils.assertLoggedIn(sid);

        Map<String, Object> map = new HashMap<String, Object>();

        return new Viewable("dashboard.mustache", map);
    }

}
