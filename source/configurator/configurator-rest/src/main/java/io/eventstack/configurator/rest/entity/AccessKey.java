package io.eventstack.configurator.rest.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Basically an API key to access a given environments configuration
 * <p/>
 * Created by gavin on 8/17/14.
 */
public class AccessKey implements Persistable {
    private String name;
    private String accessKey;
    private String secretKey;

    public AccessKey() {
    }

    public AccessKey(String name, String accessKey, String secretKey) {
        this.name = name;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public AccessKey(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.accessKey = (String) map.get("accessKey");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("accessKey", accessKey);
        map.put("secretKey", secretKey);
        return map;
    }
}
