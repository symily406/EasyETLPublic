package com.easy.etl.projectTaskFlow.service.impl;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import com.easy.etl.projectTaskFlow.mapper.ProjectTaskFlowSourceTargetNodeMapper;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowSourceTargetNodeService;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowSourceTargetNodeVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 项目工作流节点关系-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskFlowSourceTargetNodeServiceImpl extends BaseServiceImpl<ProjectTaskFlowSourceTargetNodeMapper, ProjectTaskFlowSourceTargetNode> implements IProjectTaskFlowSourceTargetNodeService {

	/**
	     * 保存或修改Entity
	 * @param projectTaskFlowSourceTargetNode
	 */
	@Override
	public boolean saveOrUpdateProjectTaskFlowSourceTargetNode(ProjectTaskFlowSourceTargetNode projectTaskFlowSourceTargetNode) {
		return saveOrUpdate(projectTaskFlowSourceTargetNode);
	}

	@Override
	public List<ProjectTaskFlowSourceTargetNodeVo> queryProjectTaskFlowSourceTargetNodes(String taskId) {
		return baseMapper.queryProjectTaskFlowSourceTargetNodes(taskId);
	}
}
