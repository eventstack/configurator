package io.eventstack.configurator.rest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by gavin on 8/19/14.
 */
@Path("logout")
public class LogoutResource {
    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getDashboard(@Context HttpServletRequest r) {
        r.getSession().invalidate();
        URI uri = uriInfo.getBaseUriBuilder().path("login.html").build();
        return Response.seeOther(uri).cookie(new NewCookie(new Cookie("sid", ""), "", 0, false)).build();
    }
}
