package io.eventstack.rest.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

        String reqHead = containerRequestContext.getHeaderString("Access-Control-Request-Headers");
        if (null != reqHead && !reqHead.equals(null)) {
            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", reqHead);
        }
    }
}