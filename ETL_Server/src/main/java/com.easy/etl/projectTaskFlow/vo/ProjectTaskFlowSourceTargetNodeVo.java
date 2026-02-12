package com.easy.etl.projectTaskFlow.vo;



import java.io.Serializable;

/**
*  项目工作流节点关系-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskFlowSourceTargetNodeVo implements Serializable {
    private String taskId;

    private String executeNodeId;

    private String executeTaskId;

    private String sourceNodeId;

    private String sourceTaskId;

    private String workSpaceType;

    private Integer nodeType;

    private Integer status;

    private String scriptContent;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExecuteNodeId() {
        return executeNodeId;
    }

    public void setExecuteNodeId(String executeNodeId) {
        this.executeNodeId = executeNodeId;
    }

    public String getExecuteTaskId() {
        return executeTaskId;
    }

    public void setExecuteTaskId(String executeTaskId) {
        this.executeTaskId = executeTaskId;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public String getSourceTaskId() {
        return sourceTaskId;
    }

    public void setSourceTaskId(String sourceTaskId) {
        this.sourceTaskId = sourceTaskId;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }
}
