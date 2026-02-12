package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目工作流节点-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_FLOW_NODE
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_FLOW_NODE", autoResultMap = true)
public class ProjectTaskFlowNode extends Model<ProjectTaskFlowNode> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "NODE_ID", type = IdType.ASSIGN_ID)
    private String nodeId;

    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;

    @TableField(value = "EXECUTE_TASK_ID")
    private String executeTaskId;
    /**
     * 节点类型
     */
    @TableField(value = "NODE_TYPE")
    private Integer nodeType;

    @TableField(value = "SEQ_INDEX")
    private Integer seqIndex;


    public ProjectTaskFlowNode() {
        super();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Integer getSeqIndex() {
        return seqIndex;
    }

    public void setSeqIndex(Integer seqIndex) {
        this.seqIndex = seqIndex;
    }
}
