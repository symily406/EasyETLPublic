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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "TASK_ID", type = IdType.ASSIGN_ID)
    private String taskId;

    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 工作空间分层
     */
    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    @NotEmpty(message = "projectWorkSpaceLayerRelId工作空间分层不能为空")
    @Length(max = 32, message = "projectWorkSpaceLayerRelId工作空间分层不能超过32个字符")
    private String projectWorkSpaceLayerRelId;
    /**
     * 项目空间ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
    @Length(max = 32, message = "projectWorkSpaceId项目空间ID不能超过32个字符")
    private String projectWorkSpaceId;
    /**
     * 工作空间任务类型
     */
    @TableField(value = "WORK_SPACE_TYPE")
    @Length(max = 8, message = "workSpaceType工作空间任务类型不能超过8个字符")
    private String workSpaceType;
    /**
     * 目录ID
     */
    @TableField(value = "TASK_FOLDER_ID")
    @NotEmpty(message = "taskFolderId目录ID不能为空")
    @Length(max = 32, message = "taskFolderId目录ID不能超过32个字符")
    private String taskFolderId;
    /**
     * 任务名称
     */
    @TableField(value = "TASK_NAME")
    @Length(max = 32, message = "taskName任务名称不能超过32个字符")
    private String taskName;
    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @Length(max = 512, message = "remark备注不能超过512个字符")
    private String remark;
    /**
     * 步骤
     */
    @TableField(value = "TASK_STEP")
    private Integer taskStep;

    /**
     * 状态1:开启调度
     */
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

    /**
     * 主键-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 主键-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public String getWorkSpaceType() {
        return workSpaceType;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public String getTaskFolderId() {
        return taskFolderId;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }

    /**
     * 任务名称-数据库字段:TASK_NAME
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务名称-数据库字段:TASK_NAME
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 备注-数据库字段:REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注-数据库字段:REMARK
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 步骤-数据库字段:TASK_STEP
     */
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
