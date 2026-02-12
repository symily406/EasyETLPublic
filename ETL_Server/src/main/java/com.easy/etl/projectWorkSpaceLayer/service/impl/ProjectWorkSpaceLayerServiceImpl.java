package com.easy.etl.projectWorkSpaceLayer.service.impl;

import com.easy.etl.projectWorkSpaceLayer.dto.ProjectWorkSpaceLayerDTO;
import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;
import com.easy.etl.projectWorkSpaceLayer.mapper.ProjectWorkSpaceLayerMapper;
import com.easy.etl.projectWorkSpaceLayer.service.IProjectWorkSpaceLayerService;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目工作空间分层-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectWorkSpaceLayerServiceImpl extends BaseServiceImpl<ProjectWorkSpaceLayerMapper, ProjectWorkSpaceLayer> implements IProjectWorkSpaceLayerService {

	/**
	     * 保存或修改Entity
	 * @param projectWorkSpaceLayer
	 */
	@Override
	public boolean saveOrUpdateProjectWorkSpaceLayer(ProjectWorkSpaceLayer projectWorkSpaceLayer) {
		return saveOrUpdate(projectWorkSpaceLayer);
	}


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<ProjectWorkSpaceLayerVo> queryProjectWorkSpaceLayerAll(ProjectWorkSpaceLayerDTO param) {
		return baseMapper.queryProjectWorkSpaceLayerAll(param);
	}
}
