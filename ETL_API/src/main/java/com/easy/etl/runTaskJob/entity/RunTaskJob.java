package com.easy.etl.runTaskJob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务运行-实体类
 *
 * @author 邵勇
 * @table TF_RUN_TASK_JOB
 * @time 2024-01-17
 */
@TableName(value = "TF_RUN_TASK_JOB", autoResultMap = true)
public class RunTaskJob extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "JOB_ID", type = IdType.ASSIGN_ID)
    private String jobId;


    @TableField(value = "TASK_ID")
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

    @TableField(value = "DB_SOURCE_ID")
    private String dbSourceId;

    @TableField(value = "PRE_SCRIPT_CONTENT")
    private String preScriptContent;


    @TableField(value = "SCRIPT_CONTENT")
    private String scriptContent;

    @TableField(value = "EVN_CONTENT")
    private String evnContent;



    @TableField(value = "NEXT_RUN_TIME")
    private Date nextRunTime;

    @TableField(value = "ADD_TYPE")
    private Integer addType;

    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "START_TIME")
    private Date startTime;

    @TableField(value = "END_TIME")
    private Date endTime;


    @TableField(value = "USAGE_TIME")
    private Long usageTime;


    @TableField(value = "READ_RECORDS")
    private BigDecimal readRecords;


    @TableField(value = "READ_WRITE_FAILURES")
    private BigDecimal readWriteFailures;


    @TableField(value = "RUN_NUM")
    private Integer runNum;

    @TableField(value = "IS_RETRY")
    private Integer isRetry;

    @TableField(value = "OLD_JOB_ID")
    private String oldJobId;

    @TableField(value = "DB_TYPE")
    private String dbType;

    @TableField(value = "IP")
    private String ip;

    @TableField(value = "HAS_RESULT")
    private Integer hasResult;

    @TableField(value = "CACHE_RESULT")
    private Integer cacheResult;


    @TableField(value = "CACHE_EXPIRATION_TIME")
    private Date cacheExpirationTime;

    @TableField(value = "IS_DIRTY_RECORD")
    private Integer isDirtyRecord;

    @TableField(value = "TASK_TYPE")
    private Integer taskType;

    @TableField(value = "FLOW_RUN_JOB_ID")
    private String flowRunJobId;

    @TableField(value = "FLOW_JOB_ID")
    private String flowJobId;


    @TableField(value = "EXECUTE_NODE_ID")
    private String executeNodeId;


    @TableField(value = "JOB_RUN_NODE_ID")
    private String jobRunNodeId;


    @TableField(value = "IS_HANDLE")
    private Integer isHandle;


    private String applicationId;


    public RunTaskJob() {
        super();
    }


    public String getJobId() {
        return jobId;
    }


    public void setJobId(String jobId) {
        this.jobId = jobId;
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


    public String getDbSourceId() {
        return dbSourceId;
    }


    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }


    public String getScriptContent() {
        return scriptContent;
    }


    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }


    public Date getNextRunTime() {
        return nextRunTime;
    }


    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }


    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getStartTime() {
        return startTime;
    }


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Date getEndTime() {
        return endTime;
    }


    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }


    public Integer getIsRetry() {
        return isRetry;
    }

    public void setIsRetry(Integer isRetry) {
        this.isRetry = isRetry;
    }


    public String getOldJobId() {
        return oldJobId;
    }


    public void setOldJobId(String oldJobId) {
        this.oldJobId = oldJobId;
    }


    public String getDbType() {
        return dbType;
    }


    public void setDbType(String dbType) {
        this.dbType = dbType;
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public Integer getHasResult() {
        return hasResult;
    }

    public void setHasResult(Integer hasResult) {
        this.hasResult = hasResult;
    }


    public Integer getCacheResult() {
        return cacheResult;
    }

    public void setCacheResult(Integer cacheResult) {
        this.cacheResult = cacheResult;
    }


    public Date getCacheExpirationTime() {
        return cacheExpirationTime;
    }


    public void setCacheExpirationTime(Date cacheExpirationTime) {
        this.cacheExpirationTime = cacheExpirationTime;
    }


    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }

    public String getFlowJobId() {
        return flowJobId;
    }

    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }

    public String getExecuteNodeId() {
        return executeNodeId;
    }

    public void setExecuteNodeId(String executeNodeId) {
        this.executeNodeId = executeNodeId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Integer isHandle) {
        this.isHandle = isHandle;
    }

    public Long getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Long usageTime) {
        this.usageTime = usageTime;
    }

    public BigDecimal getReadRecords() {
        return readRecords;
    }

    public void setReadRecords(BigDecimal readRecords) {
        this.readRecords = readRecords;
    }

    public BigDecimal getReadWriteFailures() {
        return readWriteFailures;
    }

    public void setReadWriteFailures(BigDecimal readWriteFailures) {
        this.readWriteFailures = readWriteFailures;
    }

    public String getJobRunNodeId() {
        return jobRunNodeId;
    }

    public void setJobRunNodeId(String jobRunNodeId) {
        this.jobRunNodeId = jobRunNodeId;
    }

    public String getEvnContent() {
        return evnContent;
    }

    public void setEvnContent(String evnContent) {
        this.evnContent = evnContent;
    }

    public String getPreScriptContent() {
        return preScriptContent;
    }

    public void setPreScriptContent(String preScriptContent) {
        this.preScriptContent = preScriptContent;
    }

    public Integer getIsDirtyRecord() {
        return isDirtyRecord;
    }

    public void setIsDirtyRecord(Integer isDirtyRecord) {
        this.isDirtyRecord = isDirtyRecord;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
