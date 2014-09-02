package io.eventstack.configurator.rest.providers;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.security.AccessControlException;

/**
 * Created by gavin on 9/1/14.
 */
@Provider
public class AccessExceptionMapper implements ExceptionMapper<AccessControlException> {

    @Override
    @Produces("application/json")
    public Response toResponse(AccessControlException exception) {
        return ExceptionMappers.buildSecurityErrorResponse(100, exception.getMessage());
    }
}
