package com.easy.etl.projectMember.vo;

import java.math.BigDecimal;

public class ProjectMemberWorkSpaceVo {


    private String projectId;

    private String projectWorkSpaceId;

    private String workSpaceName;

    private String workSpaceType;

    private String workSpaceLayerName;

    private String projectWorkSpaceLayerRelId;

    private BigDecimal taskNum = BigDecimal.ZERO;

    private BigDecimal failTaskNum = BigDecimal.ZERO;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    public String getWorkSpaceName() {
        return workSpaceName;
    }

    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public String getWorkSpaceLayerName() {
        return workSpaceLayerName;
    }

    public void setWorkSpaceLayerName(String workSpaceLayerName) {
        this.workSpaceLayerName = workSpaceLayerName;
    }

    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public BigDecimal getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(BigDecimal taskNum) {
        this.taskNum = taskNum;
    }

    public BigDecimal getFailTaskNum() {
        return failTaskNum;
    }

    public void setFailTaskNum(BigDecimal failTaskNum) {
        this.failTaskNum = failTaskNum;
    }
}
