package io.eventstack.configurator.rest.entity;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gavin on 8/17/14.
 */
public class App implements Persistable {
    private String id;
    private String name;
    private List<String> owners;
    private List<Environment> environments;

    public App() {
    }

    public App(Map<String, Object> map) {
        if (map.get("_id") != null) {
            id = map.get("_id").toString();
        }

        name = (String) map.get("name");

        if (map.get("owners") != null) {
            owners = (List<String>) map.get("owners");
        }

        environments = new ArrayList<Environment>();
        if (map.get("environments") != null) {
            BasicDBList list = (BasicDBList) map.get("environments");
            for (Object object : list.toArray()) {
                DBObject dbObject = (DBObject) object;
                environments.add(new Environment(dbObject.toMap()));
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        if (id != null) {
            map.put("_id", id);
        }

        map.put("name", name);

        if (owners != null)
            map.put("owners", owners);

        return map;
    }

    public String toString() {
        return new StringBuilder()
                .append("id=")
                .append(id)
                .append(";name=")
                .append(name)
                .append(";owners=")
                .append(owners != null ? Arrays.deepToString(owners.toArray()) : "")
                .toString();
    }
}
