package io.eventstack.configurator.rest.providers;

import io.eventstack.configurator.rest.exception.InvalidTokenException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

/**
 * Created by gavin on 9/1/14.
 */
@Provider
public class InvalidTokenExceptionMapper implements ExceptionMapper<InvalidTokenException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(InvalidTokenException e) {
        URI uri = uriInfo.getBaseUriBuilder().path("login.html").build();
        return Response.seeOther(uri).build();
    }
}
