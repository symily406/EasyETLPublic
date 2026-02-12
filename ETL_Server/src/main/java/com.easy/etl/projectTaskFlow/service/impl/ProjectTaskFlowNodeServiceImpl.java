package com.easy.etl.projectTaskFlow.service.impl;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import com.easy.etl.projectTaskFlow.mapper.ProjectTaskFlowNodeMapper;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowNodeService;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowNodeVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 项目工作流节点-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskFlowNodeServiceImpl extends BaseServiceImpl<ProjectTaskFlowNodeMapper, ProjectTaskFlowNode> implements IProjectTaskFlowNodeService {

	/**
	     * 保存或修改Entity
	 * @param projectTaskFlowNode
	 */
	@Override
	public boolean saveOrUpdateProjectTaskFlowNode(ProjectTaskFlowNode projectTaskFlowNode) {
		return saveOrUpdate(projectTaskFlowNode);
	}

	@Override
	public List<ProjectTaskFlowNodeVo> queryFlowNodes(String taskId) {
		return baseMapper.queryProjectTaskFlowNode(taskId);
	}
}
