package com.easy.etl.flowRunJob.vo;


import com.easy.etl.flowRunJob.entity.FlowRunJob;

import java.util.List;

/**
*  工作流运行JOB-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class FlowRunJobVo extends FlowRunJob {

    private String projectId;

    private String projectName;

    private String projectWorkSpaceLayerRelId;

    private String projectWorkSpaceId;

    private String workSpaceType;

    private String statusName;

    private String addTypeName;

    private String taskName;

    private String taskFolderId;

    private Integer cornStatus;

    private List<FlowRunJobNodeVo> flowRunJobNodeList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskFolderId() {
        return taskFolderId;
    }

    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }

    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getAddTypeName() {
        return addTypeName;
    }

    public void setAddTypeName(String addTypeName) {
        this.addTypeName = addTypeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<FlowRunJobNodeVo> getFlowRunJobNodeList() {
        return flowRunJobNodeList;
    }

    public void setFlowRunJobNodeList(List<FlowRunJobNodeVo> flowRunJobNodeList) {
        this.flowRunJobNodeList = flowRunJobNodeList;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }
}
