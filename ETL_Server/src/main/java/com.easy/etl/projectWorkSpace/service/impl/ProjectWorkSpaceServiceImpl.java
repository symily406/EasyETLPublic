package com.easy.etl.projectWorkSpace.service.impl;
import java.util.List;

import com.easy.etl.projectWorkSpace.dto.ProjectWorkSpaceDTO;
import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;
import com.easy.etl.projectWorkSpace.mapper.ProjectWorkSpaceMapper;
import com.easy.etl.projectWorkSpace.service.IProjectWorkSpaceService;
import com.easy.etl.projectWorkSpace.vo.ProjectWorkSpaceVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 项目工作空间-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectWorkSpaceServiceImpl extends BaseServiceImpl<ProjectWorkSpaceMapper, ProjectWorkSpace> implements IProjectWorkSpaceService {

	/**
	     * 保存或修改Entity
	 * @param projectWorkSpace
	 */
	@Override
	public boolean saveOrUpdateProjectWorkSpace(ProjectWorkSpace projectWorkSpace) {
		return saveOrUpdate(projectWorkSpace);
	}


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<ProjectWorkSpaceVo> queryProjectWorkSpaceAll(ProjectWorkSpaceDTO param) {
		return baseMapper.queryProjectWorkSpaceAll(param);
	}

}
