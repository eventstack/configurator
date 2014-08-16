package io.eventstack.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("io.eventstack.rest.resources", "org.codehaus.jackson.jaxrs", "io.eventstack.rest.providers");
        register(io.eventstack.rest.filters.CorsFilter.class);
    }
}
