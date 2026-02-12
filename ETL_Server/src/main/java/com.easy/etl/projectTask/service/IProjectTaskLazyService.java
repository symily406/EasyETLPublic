package com.easy.etl.projectTask.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目任务依懒-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectTaskLazyService extends IBaseService<ProjectTaskLazy> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param projectTaskLazy
	 */
	public boolean saveOrUpdateProjectTaskLazy(List<ProjectTaskLazy> layzs,String taskId);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ProjectTaskLazyVo> queryProjectTaskLazyPage(IPage<ProjectTaskLazyVo> page, ProjectTaskLazyDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskLazyVo> queryProjectTaskLazyAll(ProjectTaskLazyDTO param);

	//================代码生成器生成代码结束=================
}
