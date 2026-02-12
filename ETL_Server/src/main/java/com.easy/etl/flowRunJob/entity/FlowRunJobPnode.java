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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 归必执行工和流
     */
    @TableField(value = "FLOW_RUN_JOB_ID")
    @Length(max = 32, message = "flowRunJobId归必执行工和流不能超过32个字符")
    private String flowRunJobId;
    /**
     * 归属工作流
     */
    @TableField(value = "FLOW_JOB_ID")
    @NotEmpty(message = "flowJobId归属工作流不能为空")
    @Length(max = 32, message = "flowJobId归属工作流不能超过32个字符")
    private String flowJobId;
    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 来源节点
     */
    @TableField(value = "SOURCE_NODE_ID")
    @Length(max = 32, message = "sourceNodeId来源节点不能超过32个字符")
    private String sourceNodeId;
    /**
     * 来源节点任务
     */
    @TableField(value = "SOURCE_TASK_ID")
    @Length(max = 32, message = "sourceTaskId来源节点任务不能超过32个字符")
    private String sourceTaskId;
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
     * 状态
     */
    @TableField(value = "STATUS")
    private Integer status;

    /**
     * 下级节点状态是否更新,1:已更新
     */
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

    /**
     * 主键-数据库字段:ID
     */
    public String getId() {
        return id;
    }

    /**
     * 主键-数据库字段:ID
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 归必执行工和流-数据库字段:FLOW_RUN_JOB_ID
     */
    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    /**
     * 归必执行工和流-数据库字段:FLOW_RUN_JOB_ID
     */
    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }

    /**
     * 归属工作流-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 归属工作流-数据库字段:FLOW_JOB_ID
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
     * 来源节点-数据库字段:SOURCE_NODE_ID
     */
    public String getSourceNodeId() {
        return sourceNodeId;
    }

    /**
     * 来源节点-数据库字段:SOURCE_NODE_ID
     */
    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    /**
     * 来源节点任务-数据库字段:SOURCE_TASK_ID
     */
    public String getSourceTaskId() {
        return sourceTaskId;
    }

    /**
     * 来源节点任务-数据库字段:SOURCE_TASK_ID
     */
    public void setSourceTaskId(String sourceTaskId) {
        this.sourceTaskId = sourceTaskId;
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
     * 状态-数据库字段:STATUS
     */
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
