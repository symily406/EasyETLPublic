package com.easy.etl.flowRunJob.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 工作流运行Node-实体类
 *
 * @author 邵勇
 * @table TF_FLOWRUN_JOB_NODE
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_RUN_JOB_NODE", autoResultMap = true)
public class FlowRunJobNode extends Model<FlowRunJobNode> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "JOB_RUN_NODE_ID", type = IdType.ASSIGN_ID)
    private String jobRunNodeId;

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
    @NotEmpty(message = "flowJobId归属工作流NODE不能为空")
    @Length(max = 32, message = "flowJobId归属工作流NODE不能超过32个字符")
    private String flowJobId;
    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 工作空间任务类型
     */
    @TableField(value = "WORK_SPACE_TYPE")
    @Length(max = 8, message = "workSpaceType工作空间任务类型不能超过8个字符")
    private String workSpaceType;
    /**
     * 执行节点ID
     */
    @TableField(value = "EXECUTE_NODE_ID")
    @Length(max = 32, message = "executeNodeId执行节点ID不能超过32个字符")
    private String executeNodeId;
    /**
     * 执行任务ID
     */
    @TableField(value = "EXECUTE_TASK_ID")
    @Length(max = 32, message = "executeTaskId执行任务ID不能超过32个字符")
    private String executeTaskId;
    /**
     * 节点类型
     */
    @TableField(value = "NODE_TYPE")
    private Integer nodeType;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private Integer status;
    /**
     * 计划执行时间
     */
    @TableField(value = "PLANE_TIME")
    private Date planeTime;
    /**
     * 执行时间
     */
    @TableField(value = "EXECUTE_TIME")
    private Date executeTime;
    /**
     * 完成时间
     */
    @TableField(value = "COMPLETED_TIME")
    private Date completedTime;

    @TableField(value = "USAGE_TIME")
    private Integer usageTime;


    /**
     * 添加时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    @TableField(value = "DB_SOURCE_ID")
    private String dbSourceId;

    @TableField(value = "SCRIPT_CONTENT")
    private String scriptContent;

    @TableField(value = "SEQ_INDEX")
    private Integer seqIndex;


    public FlowRunJobNode() {
        super();
    }

    /**
     * 主键-数据库字段:JOB_RUN_NODE_ID
     */
    public String getJobRunNodeId() {
        return jobRunNodeId;
    }

    /**
     * 主键-数据库字段:JOB_RUN_NODE_ID
     */
    public void setJobRunNodeId(String jobRunNodeId) {
        this.jobRunNodeId = jobRunNodeId;
    }


    /**
     * 归属执行工和流-数据库字段:FLOW_RUN_JOB_ID
     */
    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    /**
     * 归属执行工和流-数据库字段:FLOW_RUN_JOB_ID
     */
    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }

    /**
     * 归属工作流NODE-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 归属工作流NODE-数据库字段:FLOW_JOB_ID
     */
    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
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
     * 执行节点ID-数据库字段:EXECUTE_NODE_ID
     */
    public String getExecuteNodeId() {
        return executeNodeId;
    }

    /**
     * 执行节点ID-数据库字段:EXECUTE_NODE_ID
     */
    public void setExecuteNodeId(String executeNodeId) {
        this.executeNodeId = executeNodeId;
    }

    /**
     * 执行任务ID-数据库字段:EXECUTE_TASK_ID
     */
    public String getExecuteTaskId() {
        return executeTaskId;
    }

    /**
     * 执行任务ID-数据库字段:EXECUTE_TASK_ID
     */
    public void setExecuteTaskId(String executeTaskId) {
        this.executeTaskId = executeTaskId;
    }

    /**
     * 节点类型-数据库字段:NODE_TYPE
     */
    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }


    /**
     * 状态-数据库字段:STATUS
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 计划执行时间-数据库字段:PLANE_TIME
     */
    public Date getPlaneTime() {
        return planeTime;
    }

    /**
     * 计划执行时间-数据库字段:PLANE_TIME
     */
    public void setPlaneTime(Date planeTime) {
        this.planeTime = planeTime;
    }

    /**
     * 执行时间-数据库字段:EXECUTE_TIME
     */
    public Date getExecuteTime() {
        return executeTime;
    }

    /**
     * 执行时间-数据库字段:EXECUTE_TIME
     */
    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    /**
     * 完成时间-数据库字段:COMPLETED_TIME
     */
    public Date getCompletedTime() {
        return completedTime;
    }

    /**
     * 完成时间-数据库字段:COMPLETED_TIME
     */
    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Integer getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Integer usageTime) {
        this.usageTime = usageTime;
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

    public Integer getSeqIndex() {
        return seqIndex;
    }

    public void setSeqIndex(Integer seqIndex) {
        this.seqIndex = seqIndex;
    }
}
