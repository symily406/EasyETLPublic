package com.easy.etl.projectTaskScript.service;


import java.util.List;
import java.util.Map;

import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.vo.ProjectTaskScriptVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务脚本-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskScriptService extends IBaseService<ProjectTaskScript> {

	/**
	   * 保存或修改Entity
	 * @param projectTaskScript
	 */
	public boolean saveOrUpdateProjectTaskScript(ProjectTaskScript projectTaskScript);


}
