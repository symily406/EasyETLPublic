package com.easy.etl.projectTaskEvn.service;


import java.util.List;

import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务环境变量-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskEvnService extends IBaseService<ProjectTaskEvn> {

	/**
	   * 保存或修改Entity
	 * @param projectTaskEvn
	 */
	public boolean saveOrUpdateProjectTaskEvn(ProjectTaskEvn projectTaskEvn,int taskStep);


}
