package io.eventstack.configurator.rest.providers;

import io.eventstack.configurator.rest.representations.ErrorResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by gavin on 9/1/14.
 */
public class ExceptionMappers {
    static Response buildSecurityErrorResponse(Integer errorCode, String msg) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode, msg);
        return Response.status(403).
                entity(errorResponse.toJson()).
                type(MediaType.APPLICATION_JSON_TYPE).
                build();
    }
}
