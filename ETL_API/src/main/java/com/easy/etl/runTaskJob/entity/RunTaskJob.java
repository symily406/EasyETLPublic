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
    @NotEmpty(message = "taskId任务ID不能为空")
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
     * 数据源
     */
    @TableField(value = "DB_SOURCE_ID")
    @Length(max = 32, message = "dbSourceId数据源不能超过32个字符")
    private String dbSourceId;

    @TableField(value = "PRE_SCRIPT_CONTENT")
    private String preScriptContent;

    /**
     * 脚本或sql内容
     */
    @TableField(value = "SCRIPT_CONTENT")
    private String scriptContent;

    @TableField(value = "EVN_CONTENT")
    private String evnContent;


    /**
     * 下次运行时间
     */
    @TableField(value = "NEXT_RUN_TIME")
    private Date nextRunTime;
    /**
     * 任务添加方式
     */
    @TableField(value = "ADD_TYPE")
    private Integer addType;
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

    /**
     * 读出记录总数,数据同步有效
     */
    @TableField(value = "READ_RECORDS")
    private BigDecimal readRecords;

    /**
     * 读写失败总数,数据同步有效
     */
    @TableField(value = "READ_WRITE_FAILURES")
    private BigDecimal readWriteFailures;

    /**
     * 运行次数
     */
    @TableField(value = "RUN_NUM")
    private Integer runNum;
    /**
     * 是否重试
     */
    @TableField(value = "IS_RETRY")
    private Integer isRetry;
    /**
     * 原运行任务
     */
    @TableField(value = "OLD_JOB_ID")
    @Length(max = 32, message = "oldJobId原运行任务不能超过32个字符")
    private String oldJobId;
    /**
     * 数据库类型
     */
    @TableField(value = "DB_TYPE")
    @Length(max = 32, message = "dbType数据库类型不能超过32个字符")
    private String dbType;
    /**
     * 任务执行服务器IP
     */
    @TableField(value = "IP")
    @Length(max = 32, message = "ip任务执行服务器IP不能超过32个字符")
    private String ip;
    /**
     * 任务是否有返回结果
     */
    @TableField(value = "HAS_RESULT")
    private Integer hasResult;
    /**
     * 缓存中是否有运行结果
     */
    @TableField(value = "CACHE_RESULT")
    private Integer cacheResult;

    /**
     * 缓存结果过期时间
     */
    @TableField(value = "CACHE_EXPIRATION_TIME")
    private Date cacheExpirationTime;
    /**
     * 缓存结果过期时间
     */
    @TableField(value = "IS_DIRTY_RECORD")
    private Integer isDirtyRecord;

    @TableField(value = "TASK_TYPE")
    private Integer taskType;
    /**
     * 归属执行工和流
     */
    @TableField(value = "FLOW_RUN_JOB_ID")
    @Length(max = 32, message = "flowRunJobId归属执行工和流不能超过32个字符")
    private String flowRunJobId;
    /**
     * 归属工作流NODE
     */
    @TableField(value = "FLOW_JOB_ID")
    @Length(max = 32, message = "flowJobId归属工作流NODE不能超过32个字符")
    private String flowJobId;

    /**
     * 执行节点ID
     */
    @TableField(value = "EXECUTE_NODE_ID")
    @Length(max = 32, message = "executeNodeId执行节点ID不能超过32个字符")
    private String executeNodeId;

    /**
     * 工作流运行节点
     */
    @TableField(value = "JOB_RUN_NODE_ID")
    private String jobRunNodeId;

    /**
     * 是否已处理1:已处理
     */
    @TableField(value = "IS_HANDLE")
    private Integer isHandle;

    /**
     * 应用ID
     */
    private String applicationId;


    public RunTaskJob() {
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


    /**
     * 任务ID-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID-数据库字段:TASK_ID
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
     * 数据源-数据库字段:DB_SOURCE_ID
     */
    public String getDbSourceId() {
        return dbSourceId;
    }

    /**
     * 数据源-数据库字段:DB_SOURCE_ID
     */
    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }

    /**
     * 脚本或sql内容-数据库字段:SCRIPT_CONTENT
     */
    public String getScriptContent() {
        return scriptContent;
    }

    /**
     * 脚本或sql内容-数据库字段:SCRIPT_CONTENT
     */
    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    /**
     * 下次运行时间-数据库字段:NEXT_RUN_TIME
     */
    public Date getNextRunTime() {
        return nextRunTime;
    }

    /**
     * 下次运行时间-数据库字段:NEXT_RUN_TIME
     */
    public void setNextRunTime(Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    /**
     * 任务添加方式-数据库字段:ADD_TYPE
     */
    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
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

    /**
     * 运行次数-数据库字段:RUN_NUM
     */
    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }

    /**
     * 是否重试-数据库字段:IS_RETRY
     */
    public Integer getIsRetry() {
        return isRetry;
    }

    public void setIsRetry(Integer isRetry) {
        this.isRetry = isRetry;
    }

    /**
     * 原运行任务-数据库字段:OLD_JOB_ID
     */
    public String getOldJobId() {
        return oldJobId;
    }

    /**
     * 原运行任务-数据库字段:OLD_JOB_ID
     */
    public void setOldJobId(String oldJobId) {
        this.oldJobId = oldJobId;
    }

    /**
     * 数据库类型-数据库字段:DB_TYPE
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * 数据库类型-数据库字段:DB_TYPE
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * 任务执行服务器IP-数据库字段:IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 任务执行服务器IP-数据库字段:IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 任务是否有返回结果-数据库字段:HAS_RESULT
     */
    public Integer getHasResult() {
        return hasResult;
    }

    public void setHasResult(Integer hasResult) {
        this.hasResult = hasResult;
    }

    /**
     * 缓存中是否有运行结果-数据库字段:CACHE_RESULT
     */
    public Integer getCacheResult() {
        return cacheResult;
    }

    public void setCacheResult(Integer cacheResult) {
        this.cacheResult = cacheResult;
    }

    /**
     * 缓存结果过期时间-数据库字段:CACHE_EXPIRATION_TIME
     */
    public Date getCacheExpirationTime() {
        return cacheExpirationTime;
    }

    /**
     * 缓存结果过期时间-数据库字段:CACHE_EXPIRATION_TIME
     */
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
