package io.eventstack.configurator.rest.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gavin on 9/1/14.
 */
public class PropertyDef implements Persistable {
    private String name;
    private String description;
    private PropertyType type;
    private boolean required;

    public PropertyDef() {
    }

    public PropertyDef(Map<String, Object> map) {
        name = (String) map.get("name");
        description = (String) map.get("description");
        type = PropertyType.valueOf((String) map.get("type"));
        required = Boolean.parseBoolean((String) map.get("required"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", name);
        map.put("description", description);
        map.put("type", type.name());
        map.put("required", Boolean.toString(required));

        return map;
    }
}
