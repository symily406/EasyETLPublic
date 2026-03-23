package com.easy.etl.flowRunJob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 工作流运行依懒node-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_RUN_JOB_PNODE
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_RUN_JOB_PNODE", autoResultMap = true)
public class FlowRunJobPnode extends Model<FlowRunJobPnode> {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @TableField(value = "FLOW_RUN_JOB_ID")
    private String flowRunJobId;

    @TableField(value = "FLOW_JOB_ID")
    private String flowJobId;

    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "SOURCE_NODE_ID")
    private String sourceNodeId;

    @TableField(value = "SOURCE_TASK_ID")
    private String sourceTaskId;

    @TableField(value = "EXECUTE_NODE_ID")
    private String executeNodeId;

    @TableField(value = "EXECUTE_TASK_ID")
    private String executeTaskId;

    @TableField(value = "STATUS")
    private Integer status;


    @TableField(value = "NEXT_NODE_STATUS")
    private Integer nextNodeStatus;

    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }


    public FlowRunJobPnode() {
        super();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
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


    public String getSourceNodeId() {
        return sourceNodeId;
    }


    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }


    public String getSourceTaskId() {
        return sourceTaskId;
    }


    public void setSourceTaskId(String sourceTaskId) {
        this.sourceTaskId = sourceTaskId;
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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNextNodeStatus() {
        return nextNodeStatus;
    }

    public void setNextNodeStatus(Integer nextNodeStatus) {
        this.nextNodeStatus = nextNodeStatus;
    }
}
