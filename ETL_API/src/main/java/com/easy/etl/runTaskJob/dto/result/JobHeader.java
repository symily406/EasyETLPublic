package com.easy.etl.runTaskJob.dto.result;

public class JobHeader {
    private String prop;
    private String label;
    private String width;

    public JobHeader() {
        super();
    }

    public JobHeader(String prop, String label, String width) {
        this.prop = prop;
        this.label = label;
        this.width = width;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
