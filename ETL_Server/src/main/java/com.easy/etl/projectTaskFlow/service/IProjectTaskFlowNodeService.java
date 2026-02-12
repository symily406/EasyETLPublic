package com.easy.etl.projectTaskFlow.service;


import java.util.List;

import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowNodeVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目工作流节点-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskFlowNodeService extends IBaseService<ProjectTaskFlowNode> {

	/**
	   * 保存或修改Entity
	 * @param projectTaskFlowNode
	 */
	public boolean saveOrUpdateProjectTaskFlowNode(ProjectTaskFlowNode projectTaskFlowNode);

	List<ProjectTaskFlowNodeVo> queryFlowNodes(String taskId);

}
