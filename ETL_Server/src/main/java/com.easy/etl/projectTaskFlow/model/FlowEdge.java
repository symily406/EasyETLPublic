package com.easy.etl.projectTaskFlow.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FlowEdge {
    // 唯一标识符
    private String id;

    // 线条类型
    private String type;

    @JsonProperty("properties")
    private FlowProperties flowProperties;

    // 源节点ID
    private String sourceNodeId;

    // 目标节点ID
    private String targetNodeId;

    private FlowXY startPoint;

    private List<FlowXY> pointsList;

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

    public FlowProperties getFlowProperties() {
        return flowProperties;
    }

    public void setFlowProperties(FlowProperties flowProperties) {
        this.flowProperties = flowProperties;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public FlowXY getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(FlowXY startPoint) {
        this.startPoint = startPoint;
    }

    public List<FlowXY> getPointsList() {
        return pointsList;
    }

    public void setPointsList(List<FlowXY> pointsList) {
        this.pointsList = pointsList;
    }
}
