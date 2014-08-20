package io.eventstack.configurator.rest.entity;

import java.util.Date;

/**
 * Created by gavin on 8/17/14.
 */
public class AccessKey {
    private String key;
    private Date createdDt;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }
}
