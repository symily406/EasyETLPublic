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
*归属任务
*/
@TableField(value = "TASK_ID")
@NotEmpty(message = "taskId归属任务不能为空")
@Length(max =32,message ="taskId归属任务不能超过32个字符")
private String taskId;
/**
*来源节点
*/
@TableField(value = "SOURCE_NODE_ID")
@Length(max =64,message ="sourceNodeId来源节点不能超过64个字符")
private String sourceNodeId;
/**
*目标节点
*/
@TableField(value = "TARGET_NODE_ID")
@Length(max =64,message ="targetNodeId目标节点不能超过64个字符")
private String targetNodeId;


	public ProjectTaskFlowSourceTargetNode() {
	     super();
	}

	/**
*主键-数据库字段:ID
*/
public String getId()
{
return id;
}
/**
*主键-数据库字段:ID
*/
public void setId(String id) {
this.id=id;
}


	/**
*归属任务-数据库字段:TASK_ID
*/
public String getTaskId()
{
return taskId;
}
/**
*归属任务-数据库字段:TASK_ID
*/
public void setTaskId(String taskId) {
this.taskId=taskId;
}
/**
*来源节点-数据库字段:SOURCE_NODE_ID
*/
public String getSourceNodeId()
{
return sourceNodeId;
}
/**
*来源节点-数据库字段:SOURCE_NODE_ID
*/
public void setSourceNodeId(String sourceNodeId) {
this.sourceNodeId=sourceNodeId;
}
/**
*目标节点-数据库字段:TARGET_NODE_ID
*/
public String getTargetNodeId()
{
return targetNodeId;
}
/**
*目标节点-数据库字段:TARGET_NODE_ID
*/
public void setTargetNodeId(String targetNodeId) {
this.targetNodeId=targetNodeId;
}


}
