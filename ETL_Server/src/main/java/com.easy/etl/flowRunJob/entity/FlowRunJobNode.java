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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "JOB_RUN_NODE_ID", type = IdType.ASSIGN_ID)
    private String jobRunNodeId;


    @TableField(value = "FLOW_RUN_JOB_ID")
    private String flowRunJobId;

    @TableField(value = "FLOW_JOB_ID")
    private String flowJobId;

    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "WORK_SPACE_TYPE")
    private String workSpaceType;

    @TableField(value = "EXECUTE_NODE_ID")
    private String executeNodeId;

    @TableField(value = "EXECUTE_TASK_ID")
    private String executeTaskId;

    @TableField(value = "NODE_TYPE")
    private Integer nodeType;


    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "PLANE_TIME")
    private Date planeTime;

    @TableField(value = "EXECUTE_TIME")
    private Date executeTime;

    @TableField(value = "COMPLETED_TIME")
    private Date completedTime;

    @TableField(value = "USAGE_TIME")
    private Integer usageTime;



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


    public String getJobRunNodeId() {
        return jobRunNodeId;
    }


    public void setJobRunNodeId(String jobRunNodeId) {
        this.jobRunNodeId = jobRunNodeId;
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


    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public String getWorkSpaceType() {
        return workSpaceType;
    }


    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
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


    public Date getPlaneTime() {
        return planeTime;
    }


    public void setPlaneTime(Date planeTime) {
        this.planeTime = planeTime;
    }


    public Date getExecuteTime() {
        return executeTime;
    }


    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }


    public Date getCompletedTime() {
        return completedTime;
    }


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
