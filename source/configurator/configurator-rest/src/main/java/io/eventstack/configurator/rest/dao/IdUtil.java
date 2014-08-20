package io.eventstack.configurator.rest.dao;

import java.util.UUID;

/**
 * Created by gavin on 8/17/14.
 */
public class IdUtil {
    public static String generateAppId() {
        return UUID.randomUUID().toString();
    }

    public static String generateAccessKey() {
        return UUID.randomUUID().toString();
    }

    private IdUtil() {}
}
