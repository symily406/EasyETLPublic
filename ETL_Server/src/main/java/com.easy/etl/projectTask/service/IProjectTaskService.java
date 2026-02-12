package com.easy.etl.projectTask.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskFolderDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.vo.ProjectTaskTreeVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import com.easy.etl.projectTask.vo.TaskTreeVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目任务-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskService extends IBaseService<ProjectTask> {

	/**
	   * 保存或修改Entity
	 * @param projectTask
	 */
	public boolean saveOrUpdateProjectTask(ProjectTask projectTask);

	public boolean saveTask(ProjectTask projectTask, ProjectTaskSchedue taskSchedue, String scriptContent, List<ProjectTaskParameter> projectTaskParameters,String evnContent, int taskStep);


	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ProjectTaskVo> queryProjectTaskPage(IPage<ProjectTaskVo> page, ProjectTaskDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskVo> queryProjectTaskAll(ProjectTaskDTO param);


	TaskTreeVo queryProjectTaskTree(ProjectTaskDTO param, ProjectMemberDTO memberDTO,ProjectTaskFolderDTO taskFolderDTO);


	/**
	 * 创建dataxjson
	 * @param taskId 任务id
	 * @param submitJob 是否提交任务1:提交任务
	 * @param parseParams 是否解析参数1:解析参数
	 * @return
	 */
	String createDataxJson(String taskId,int submitJob,int parseParams);

	ProjectTaskVo queryProjectTaskById(String taskId);
}
