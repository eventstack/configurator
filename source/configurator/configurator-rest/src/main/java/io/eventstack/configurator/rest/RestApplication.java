package io.eventstack.configurator.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("io.eventstack.configurator.rest.resources", "org.codehaus.jackson.jaxrs", "io.eventstack.rest.providers");
        register(io.eventstack.configurator.rest.filters.CorsFilter.class);
    }
}
