package io.eventstack.configurator.rest.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gavin on 8/17/14.
 */
public class Environment implements Persistable {
    private String id;
    private boolean encrypted;
    private List<AccessKey> accessKeys;

    public Environment(Map<String, Object> map) {
        id = (String) map.get("id");
        encrypted = (Boolean) map.get("encrypted");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AccessKey> getAccessKeys() {
        return accessKeys;
    }

    public void setAccessKeys(List<AccessKey> accessKeys) {
        this.accessKeys = accessKeys;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        if (id != null)
            map.put("id", id);

        map.put("encrypted", Boolean.valueOf(encrypted));

        return map;
    }
}
