package com.easy.etl.projectTaskFlow.model;

import java.util.List;

public class Flow {
    List<FlowNode> nodes;
    List<FlowEdge> edges;

    public List<FlowNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<FlowNode> nodes) {
        this.nodes = nodes;
    }

    public List<FlowEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<FlowEdge> edges) {
        this.edges = edges;
    }
}
