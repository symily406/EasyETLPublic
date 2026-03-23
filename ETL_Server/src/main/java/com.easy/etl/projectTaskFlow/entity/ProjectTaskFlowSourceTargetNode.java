package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目工作流节点关系-实体类
 * @table TF_PROJECT_TASK_FLOW_SOURCE_TARGET_NODE
 * @author 邵勇
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_FLOW_SOURCE_TARGET_NODE", autoResultMap = true)
public class ProjectTaskFlowSourceTargetNode   extends Model<ProjectTaskFlowSourceTargetNode> {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;


@TableField(value = "TASK_ID")
private String taskId;

@TableField(value = "SOURCE_NODE_ID")
private String sourceNodeId;

@TableField(value = "TARGET_NODE_ID")
private String targetNodeId;


	public ProjectTaskFlowSourceTargetNode() {
	     super();
	}


public String getId()
{
return id;
}

public void setId(String id) {
this.id=id;
}



public String getTaskId()
{
return taskId;
}

public void setTaskId(String taskId) {
this.taskId=taskId;
}

public String getSourceNodeId()
{
return sourceNodeId;
}

public void setSourceNodeId(String sourceNodeId) {
this.sourceNodeId=sourceNodeId;
}

public String getTargetNodeId()
{
return targetNodeId;
}

public void setTargetNodeId(String targetNodeId) {
this.targetNodeId=targetNodeId;
}


}
