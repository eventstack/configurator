package io.eventstack.configurator.rest.resources;

import io.eventstack.configurator.rest.s3.S3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 8/11/14.
 */
@Path("configs")
public class ConfigResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigResource.class);

    @GET
    @Path("{app}/{env}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfig(@PathParam("app") String app, @PathParam("env") String env) {

        String key = String.format("%s/%s", app, env);
        S3Client client = S3Client.getInstance();
        try {
            LOGGER.info("retrieving config for {}", key);
            Map<String, String> map = client.read(key);
            return Response.ok(map).build();
        } catch (Exception e) {
            LOGGER.warn("Unable to get config for " + key, e);
        }

        return Response.ok(new HashMap<String, String>()).build();
    }

    @POST
    @Path("{app}/{env}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response writeConfig(@PathParam("app") String app, @PathParam("env") String env,
                                Map<String, String> config) {

        String key = String.format("%s/%s", app, env);
        S3Client client = S3Client.getInstance();

        boolean success = false;
        try {
            client.writeMap(key, config);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("success", success);

        return Response.ok(response).build();
    }
}
