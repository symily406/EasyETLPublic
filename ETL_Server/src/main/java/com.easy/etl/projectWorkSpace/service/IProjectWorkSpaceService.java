package com.easy.etl.projectWorkSpace.service;


import java.util.List;

import com.easy.etl.projectWorkSpace.dto.ProjectWorkSpaceDTO;
import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;
import com.easy.etl.projectWorkSpace.vo.ProjectWorkSpaceVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目工作空间-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectWorkSpaceService extends IBaseService<ProjectWorkSpace> {

	/**
	   * 保存或修改Entity
	 * @param projectWorkSpace
	 */
	public boolean saveOrUpdateProjectWorkSpace(ProjectWorkSpace projectWorkSpace);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectWorkSpaceVo> queryProjectWorkSpaceAll(ProjectWorkSpaceDTO param);
}
