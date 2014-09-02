package io.eventstack.configurator.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("io.eventstack.configurator.rest.resources", "org.codehaus.jackson.jaxrs", "io.eventstack.configurator.rest.providers");
        register(io.eventstack.configurator.rest.filters.CorsFilter.class);
        register(MustacheMvcFeature.class);
    }
}
