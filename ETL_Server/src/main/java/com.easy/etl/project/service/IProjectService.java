package com.easy.etl.project.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.project.dto.ProjectDTO;
import com.easy.etl.project.entity.Project;
import com.easy.etl.project.vo.ProjectVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目管理-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectService extends IBaseService<Project> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param project
	 */
	public boolean saveOrUpdateProject(Project project,String workSpaces);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ProjectVo> queryProjectPage(IPage<ProjectVo> page, ProjectDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectVo> queryProjectAll(ProjectDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public ProjectVo queryProjectByCondition(ProjectDTO param);
	//================代码生成器生成代码结束=================
}
