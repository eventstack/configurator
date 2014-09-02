package io.eventstack.configurator.rest.resources;

import io.eventstack.configurator.rest.dao.AppDao;
import io.eventstack.configurator.rest.entity.App;
import io.eventstack.configurator.rest.entity.Environment;
import io.eventstack.configurator.rest.entity.PropertyDef;
import io.eventstack.configurator.rest.exception.InvalidTokenException;
import io.eventstack.configurator.rest.representations.OperationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.security.AccessControlException;
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

        String userId = SessionUtils.getUserIdFromSession(sid);

        if (userId == null)
            throw new AccessControlException("Not logged in");

        app.setOwners(Arrays.asList(userId));
        new AppDao().create(app);

        return Response.status(Response.Status.CREATED).entity(app).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyApps(@CookieParam("sid") String sid, @Context HttpServletRequest req) throws UnknownHostException, InvalidTokenException {
        String userId = SessionUtils.getUserIdFromSession(sid);

        if (userId == null)
            throw new AccessControlException("Not logged in");

        List<App> myApps = new AppDao().findAppsByUser(userId);
        return Response.ok(myApps).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{appId}")
    public Response getApp(@PathParam("appId") String appId,
                           @CookieParam("sid") String sid, @Context HttpServletRequest req) throws UnknownHostException, InvalidTokenException {
        checkIsOwnerOfApp(appId, SessionUtils.getUserIdFromSession(sid));
        App app = new AppDao().find(appId);
        return Response.ok(app).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{appId}/environments")
    public Response createEnvironment(@PathParam("appId") String appId,
                                      Environment environment,
                                      @CookieParam("sid") String sid) throws UnknownHostException, InvalidTokenException {
        checkIsOwnerOfApp(appId, SessionUtils.getUserIdFromSession(sid));

        new AppDao().createEnvironment(appId, environment);

        return Response.status(Response.Status.CREATED)
                .entity(new OperationResponse(true, "Created environment")).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{appId}/environments/{envId}")
    public Response deleteEnvironment(@PathParam("appId") String appId,
                                      @PathParam("envId") String envId,
                                      @CookieParam("sid") String sid) throws UnknownHostException, InvalidTokenException {
        checkIsOwnerOfApp(appId, SessionUtils.getUserIdFromSession(sid));

        new AppDao().deleteEnvironment(appId, envId);

        return Response.status(Response.Status.OK)
                .entity(new OperationResponse(true, "Deleted environment")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{appId}/properties")
    public Response createProperty(@PathParam("appId") String appId,
                                      PropertyDef property,
                                      @CookieParam("sid") String sid) throws UnknownHostException, InvalidTokenException {
        checkIsOwnerOfApp(appId, SessionUtils.getUserIdFromSession(sid));

        new AppDao().createPropertyDef(appId, property);

        return Response.status(Response.Status.CREATED)
                .entity(new OperationResponse(true, "Created property")).build();
    }

    private void checkIsOwnerOfApp(String appId, String userId) throws AccessControlException {
        if (userId == null)
            throw new AccessControlException("Not logged in");

        App app = new AppDao().find(appId);
        if (!app.getOwners().contains(userId))
            throw new AccessControlException("User is not an owner of the app");
    }
}
