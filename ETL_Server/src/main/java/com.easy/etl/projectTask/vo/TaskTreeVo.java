package com.easy.etl.projectTask.vo;

import com.easy.etl.projectTask.entity.ProjectTask;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class TaskTreeVo implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProjectTaskTreeVo> taskTree;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProjectTaskVo> taskNodes;

    public List<ProjectTaskVo> getTaskNodes() {
        return taskNodes;
    }

    public void setTaskNodes(List<ProjectTaskVo> taskNodes) {
        this.taskNodes = taskNodes;
    }

    public List<ProjectTaskTreeVo> getTaskTree() {
        return taskTree;
    }

    public void setTaskTree(List<ProjectTaskTreeVo> taskTree) {
        this.taskTree = taskTree;
    }
}
