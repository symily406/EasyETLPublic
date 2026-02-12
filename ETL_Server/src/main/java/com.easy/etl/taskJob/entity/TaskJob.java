package com.easy.etl.taskJob.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 待运行任务-实体类
 *
 * @author 邵勇
 * @table TF_TASK_JOB
 * @time 2024-01-17
 */
@TableName(value = "TF_TASK_JOB", autoResultMap = true)
public class TaskJob extends Model<TaskJob> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "JOB_ID", type = IdType.ASSIGN_ID)
    private String jobId;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    @Length(max = 32, message = "taskId任务ID不能超过32个字符")
    private String taskId;


    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    private String projectId;
    /**
     * 工作空间分层
     */
    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    @Length(max = 32, message = "projectWorkSpaceLayerRelId工作空间分层不能超过32个字符")
    private String projectWorkSpaceLayerRelId;
    /**
     * 项目空间ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
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
    @Length(max = 32, message = "taskFolderId目录ID不能超过32个字符")
    private String taskFolderId;
    /**
     * 数据源ID
     */
    @TableField(value = "DB_SOURCE_ID")
    @Length(max = 32, message = "dbSourceId数据源ID不能超过32个字符")
    private String dbSourceId;
    /**
     * 调度状态
     */
    @TableField(value = "CORN_STATUS")
    private Integer cornStatus;
    /**
     * 调度开始时间
     */
    @TableField(value = "START_DATE")
    @Length(max = 16, message = "startDate调度开始时间不能超过16个字符")
    private String startDate;
    /**
     * 调度结束时间
     */
    @TableField(value = "END_DATE")
    @Length(max = 16, message = "endDate调度结束时间不能超过16个字符")
    private String endDate;
    /**
     * 调度时间
     */
    @TableField(value = "CORN")
    @Length(max = 64, message = "corn调度时间不能超过64个字符")
    private Date corn;
    /**
     * 失败策略
     */
    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    @TableField(value = "PRE_SCRIPT")
    private String preScript;

    /**
     * 运行脚本
     */
    @TableField(value = "RUN_SCRIPT")
    private String runScript;

    /**
     * 任务状态
     */
    @TableField(value = "STATUS")
    private Integer status;
    /**
     * 任务运行开始时间
     */
    @TableField(value = "START_TIME")
    private Date startTime;
    /**
     * 任务运行结束时间
     */
    @TableField(value = "END_TIME")
    private Date endTime;
    /**
     * 用时
     */
    @TableField(value = "USAGE_TIME")
    private Long usageTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public TaskJob() {
        super();
    }

    /**
     * 主键-数据库字段:JOB_ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 主键-数据库字段:JOB_ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
     * 数据源ID-数据库字段:DB_SOURCE_ID
     */
    public String getDbSourceId() {
        return dbSourceId;
    }

    /**
     * 数据源ID-数据库字段:DB_SOURCE_ID
     */
    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }

    /**
     * 调度状态-数据库字段:CORN_STATUS
     */
    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }

    /**
     * 调度开始时间-数据库字段:START_DATE
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 调度开始时间-数据库字段:START_DATE
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 调度结束时间-数据库字段:END_DATE
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 调度结束时间-数据库字段:END_DATE
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Date getCorn() {
        return corn;
    }

    public void setCorn(Date corn) {
        this.corn = corn;
    }

    /**
     * 失败策略-数据库字段:FAILURE_POLICY
     */
    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    /**
     * 运行脚本-数据库字段:RUN_SCRIPT
     */
    public String getRunScript() {
        return runScript;
    }

    /**
     * 运行脚本-数据库字段:RUN_SCRIPT
     */
    public void setRunScript(String runScript) {
        this.runScript = runScript;
    }

    /**
     * 任务状态-数据库字段:STATUS
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 任务运行开始时间-数据库字段:START_TIME
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 任务运行开始时间-数据库字段:START_TIME
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 任务运行结束时间-数据库字段:END_TIME
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 任务运行结束时间-数据库字段:END_TIME
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Long getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Long usageTime) {
        this.usageTime = usageTime;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
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

    public String getTaskFolderId() {
        return taskFolderId;
    }

    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }

    public String getPreScript() {
        return preScript;
    }

    public void setPreScript(String preScript) {
        this.preScript = preScript;
    }
}
