package io.eventstack.configurator.rest.resources;

import io.eventstack.configurator.rest.dao.AppDao;
import io.eventstack.configurator.rest.dao.UserSessionDao;
import io.eventstack.configurator.rest.entity.App;
import io.eventstack.configurator.rest.entity.UserSession;
import io.eventstack.configurator.rest.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gavin on 8/17/14.
 */
@Path("apps")
public class AppsResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createApp(App app, @CookieParam("sid") String sid, @Context HttpServletRequest req) throws UnknownHostException, InvalidTokenException {

        System.out.println("sid=" + sid);

//        Cookie[] cookies = req.getCookies();
//        for (Cookie cookie : cookies) {
//            System.out.println(cookie.getName());
//        }

        UserSession userSession = new UserSessionDao().find(sid);
        if (userSession == null)
            throw new InvalidTokenException("invalid token");

        app.setOwners(Arrays.asList(userSession.getUserId()));
        new AppDao().create(app);

        return Response.status(Response.Status.CREATED).entity(app).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyApps(@CookieParam("sid") String sid, @Context HttpServletRequest req) throws UnknownHostException, InvalidTokenException {
        System.out.println("sid=" + sid);

        UserSession userSession = new UserSessionDao().find(sid);
        if (userSession == null)
            throw new InvalidTokenException("invalid token");

        List<App> myApps = new AppDao().findAppsByUser(userSession.getUserId());
        return Response.ok(myApps).build();
    }
}
