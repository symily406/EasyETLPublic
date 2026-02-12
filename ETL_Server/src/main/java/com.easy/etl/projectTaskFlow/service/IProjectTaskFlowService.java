package com.easy.etl.projectTaskFlow.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTaskFlow.dto.ProjectTaskFlowDTO;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlow;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowNodeVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowSourceTargetNodeVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目工作流-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskFlowService extends IBaseService<ProjectTaskFlow> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param projectTaskFlow
	 */
	public boolean saveOrUpdateProjectTaskFlow(ProjectTaskFlow projectTaskFlow, ProjectTask task, ProjectTaskSchedue schedue, List<ProjectTaskFlowNode> taskFlowNodes, List<ProjectTaskFlowSourceTargetNode> taskFlowSourceTargetNodes, List<ProjectTaskParameter> projectTaskParameters);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ProjectTaskFlowVo> queryProjectTaskFlowPage(IPage<ProjectTaskFlowVo> page, ProjectTaskFlowDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskFlowVo> queryProjectTaskFlowAll(ProjectTaskFlowDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public ProjectTaskFlowVo queryProjectTaskFlowByCondition(ProjectTaskFlowDTO param);
	//================代码生成器生成代码结束=================

	/**
	 * 提交工作流
	 */
	public boolean submitFlowTask(ProjectTask projectTask, ProjectTaskFlow taskFlow, List<ProjectTaskFlowNodeVo> flowNodes, List<ProjectTaskFlowSourceTargetNodeVo> sourceTargetNodes, List<ProjectTaskParameter> flowParams);

	public String runFlowTask(ProjectTask projectTask,ProjectTaskFlow taskFlow, List<ProjectTaskFlowNodeVo> flowNodes, List<ProjectTaskFlowSourceTargetNodeVo> sourceTargetNodes, List<ProjectTaskParameter> flowParams);

}
