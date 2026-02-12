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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "JOB_NODE_ID", type = IdType.ASSIGN_ID)
    private String jobNodeId;

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
     * 执行脚本内容
     */
    @TableField(value = "SCRIPT_CONTENT")
    @Length(max = 0, message = "scriptContent执行脚本内容不能超过0个字符")
    private String scriptContent;

    /**
     * 状态:10:待父级依懒运行,1待运行,2正在运行,0运行成功,-1运行失败
     */
    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "SEQ_INDEX")
    private Integer seqIndex;


    public FlowJobNode() {
        super();
    }

    /**
     * 主键-数据库字段:JOB_NODE_ID
     */
    public String getJobNodeId() {
        return jobNodeId;
    }

    /**
     * 主键-数据库字段:JOB_NODE_ID
     */
    public void setJobNodeId(String jobNodeId) {
        this.jobNodeId = jobNodeId;
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
     * 执行脚本内容-数据库字段:SCRIPT_CONTENT
     */
    public String getScriptContent() {
        return scriptContent;
    }

    /**
     * 执行脚本内容-数据库字段:SCRIPT_CONTENT
     */
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
