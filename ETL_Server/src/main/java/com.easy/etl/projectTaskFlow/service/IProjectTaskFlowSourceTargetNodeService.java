package com.easy.etl.projectTaskFlow.service;


import java.util.List;

import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowSourceTargetNodeVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目工作流节点关系-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskFlowSourceTargetNodeService extends IBaseService<ProjectTaskFlowSourceTargetNode> {

	/**
	   * 保存或修改Entity
	 * @param projectTaskFlowSourceTargetNode
	 */
	public boolean saveOrUpdateProjectTaskFlowSourceTargetNode(ProjectTaskFlowSourceTargetNode projectTaskFlowSourceTargetNode);


	public List<ProjectTaskFlowSourceTargetNodeVo> queryProjectTaskFlowSourceTargetNodes(String taskId);
}
