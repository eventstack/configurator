package io.eventstack.configurator.rest.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 8/17/14.
 */
public class UserSession implements Persistable {
    private String id;
    private String userId;
    private Date createdDt;

    public UserSession() {
    }

    public UserSession(Map<String, Object> map) {
        if (map.get("_id") != null)
            id = map.get("_id").toString();

        userId = (String) map.get("userId");
        createdDt = (Date) map.get("createdDt");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("createdDt", createdDt);
        return map;
    }
}
