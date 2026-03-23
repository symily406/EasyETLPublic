package com.easy.etl.projectTask.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

/**
 * 项目任务-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK", autoResultMap = true)
public class ProjectTask extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "TASK_ID", type = IdType.ASSIGN_ID)
    private String taskId;


    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    private String projectWorkSpaceLayerRelId;

    @TableField(value = "PROJECT_WORK_SPACE_ID")
    private String projectWorkSpaceId;

    @TableField(value = "WORK_SPACE_TYPE")
    private String workSpaceType;

    @TableField(value = "TASK_FOLDER_ID")
    private String taskFolderId;

    @TableField(value = "TASK_NAME")
    private String taskName;

    @TableField(value = "REMARK")
    private String remark;

    @TableField(value = "TASK_STEP")
    private Integer taskStep;


    @TableField(value = "CORN_STATUS")
    private Integer cornStatus;

    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    @TableField(value = "START_DATE")
    private String startDate;

    @TableField(value = "END_DATE")
    private String endDate;

    @TableField(value = "CORN")
    private String corn;

    @TableField(value = "DB_SOURCE_ID")
    private String dbSourceId;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField(value = "IS_SUBMIT")
    private Integer isSubmit;


    public ProjectTask() {
        super();
    }


    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
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


    public String getTaskFolderId() {
        return taskFolderId;
    }


    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }


    public String getTaskName() {
        return taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Integer getTaskStep() {
        return taskStep;
    }

    public void setTaskStep(Integer taskStep) {
        this.taskStep = taskStep;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getDbSourceId() {
        return dbSourceId;
    }

    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }

    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }
}
