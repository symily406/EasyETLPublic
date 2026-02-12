package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.projectTaskFlow.entity.FlowJobParameter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 工作流依懒Node-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_JOB_PNODE
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_JOB_PNODE", autoResultMap = true)
public class FlowJobPnode extends Model<FlowJobParameter> implements Serializable {

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
	 * 状态:10:待父级依懒运行,1待运行,2正在运行,0运行成功,-1运行失败
	 */
	@TableField(value = "STATUS")
	private Integer status;


    public FlowJobPnode() {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
