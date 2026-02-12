package com.easy.etl.projectTaskFlow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowNode extends  FlowXY {
    private String id;
    private String type;

    private FlowProperties properties;

    private int index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FlowProperties getProperties() {
        return properties;
    }

    public void setProperties(FlowProperties properties) {
        this.properties = properties;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
