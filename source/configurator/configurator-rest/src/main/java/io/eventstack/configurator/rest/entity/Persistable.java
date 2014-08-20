package io.eventstack.configurator.rest.entity;

import java.util.Map;

/**
 * Created by gavin on 8/17/14.
 */
public interface Persistable {
    Map<String,Object> toMap();
}
