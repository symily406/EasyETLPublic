package com.easy.etl.projectTaskParameter.service;


import java.util.List;
import java.util.Map;

import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务参数-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskParameterService extends IBaseService<ProjectTaskParameter> {

	/**
	   * 保存或修改Entity
	 * @param projectTaskParameter
	 */
	public boolean saveOrUpdateProjectTaskParameter(String taskId,List<ProjectTaskParameter> projectTaskParameter,int taskStep);


	public List<ProjectTaskParameter> queryParametersByTaskId(ProjectTaskDTO param);

	public  Map<String,String> parameters(String taskId);

	public  Map<String,String> dynamicParameters(String taskId);
}
