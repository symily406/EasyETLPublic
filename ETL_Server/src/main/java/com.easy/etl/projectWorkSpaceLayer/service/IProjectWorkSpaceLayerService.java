package com.easy.etl.projectWorkSpaceLayer.service;


import java.util.List;

import com.easy.etl.projectWorkSpaceLayer.dto.ProjectWorkSpaceLayerDTO;
import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目工作空间分层-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectWorkSpaceLayerService extends IBaseService<ProjectWorkSpaceLayer> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param projectWorkSpaceLayer
	 */
	public boolean saveOrUpdateProjectWorkSpaceLayer(ProjectWorkSpaceLayer projectWorkSpaceLayer);


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectWorkSpaceLayerVo> queryProjectWorkSpaceLayerAll(ProjectWorkSpaceLayerDTO param);

}
