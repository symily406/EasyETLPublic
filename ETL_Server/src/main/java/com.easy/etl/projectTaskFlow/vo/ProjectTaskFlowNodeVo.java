package com.easy.etl.projectTaskFlow.vo;

import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;

public class ProjectTaskFlowNodeVo extends ProjectTaskFlowNode {
    private String executeNodeId;
    private String scriptContent;

    private String workSpaceType;

    private String dbSourceId;

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public String getExecuteNodeId() {
        return executeNodeId;
    }

    public void setExecuteNodeId(String executeNodeId) {
        this.executeNodeId = executeNodeId;
    }

    public String getDbSourceId() {
        return dbSourceId;
    }

    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }
}
