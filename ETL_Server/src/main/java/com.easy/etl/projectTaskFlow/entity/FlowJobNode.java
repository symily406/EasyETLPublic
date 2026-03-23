package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 工作流Node-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_JOB_NODE
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_JOB_NODE", autoResultMap = true)
public class FlowJobNode extends Model<FlowJobNode> implements Serializable {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "JOB_NODE_ID", type = IdType.ASSIGN_ID)
    private String jobNodeId;


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

    @TableField(value = "SCRIPT_CONTENT")
    private String scriptContent;


    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "SEQ_INDEX")
    private Integer seqIndex;


    public FlowJobNode() {
        super();
    }


    public String getJobNodeId() {
        return jobNodeId;
    }


    public void setJobNodeId(String jobNodeId) {
        this.jobNodeId = jobNodeId;
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


    public String getScriptContent() {
        return scriptContent;
    }


    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSeqIndex() {
        return seqIndex;
    }

    public void setSeqIndex(Integer seqIndex) {
        this.seqIndex = seqIndex;
    }
}
