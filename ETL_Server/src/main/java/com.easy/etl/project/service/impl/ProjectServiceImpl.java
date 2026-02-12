package com.easy.etl.project.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.project.dto.ProjectDTO;
import com.easy.etl.project.entity.Project;
import com.easy.etl.project.entity.ProjectWorkSpaceLayerRel;
import com.easy.etl.project.mapper.ProjectMapper;
import com.easy.etl.project.mapper.ProjectWorkSpaceLayerRelMapper;
import com.easy.etl.project.service.IProjectService;
import com.easy.etl.project.vo.ProjectVo;
import com.easy.etl.projectDbSource.mapper.ProjectDbSourceMapper;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目管理-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectMapper, Project> implements IProjectService {

	@Autowired
	private ProjectWorkSpaceLayerRelMapper projectWorkSpaceLayerRelMapper;

	@Autowired
	private ProjectDbSourceMapper projectDbSourceMapper;
	/**
	     * 保存或修改Entity
	 * @param project
	 */
	@Override
	public boolean saveOrUpdateProject(Project project,String workSpaces) {

		String projectId=idBulider();
		if (StringUtils.isEmpty(project.getProjectId())){
			project.setProjectId(projectId);
		}
		projectWorkSpaceLayerRelMapper.update(null,new UpdateWrapper<ProjectWorkSpaceLayerRel>().lambda().set(ProjectWorkSpaceLayerRel::getEnable,0).eq(ProjectWorkSpaceLayerRel::getProjectId,project.getProjectId()));

		List<ProjectWorkSpaceLayerRel> workSpaceRels= JsonKit.parseList(workSpaces,ProjectWorkSpaceLayerRel.class);
		for (ProjectWorkSpaceLayerRel workSpaceRel:workSpaceRels) {
			workSpaceRel.setProjectId(project.getProjectId());
			workSpaceRel.setEnable(1);
			if (projectWorkSpaceLayerRelMapper.selectById(workSpaceRel.getProjectWorkSpaceLayerRelId())==null){
				projectWorkSpaceLayerRelMapper.insert(workSpaceRel);
				continue;
			}
			projectWorkSpaceLayerRelMapper.updateById(workSpaceRel);
		}
		return saveOrUpdate(project);
	}


	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<ProjectVo> queryProjectPage(IPage<ProjectVo> page, ProjectDTO param) {
		IPage<ProjectVo> result=page.setRecords(baseMapper.queryProjectPage(page, param));
		return result;
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<ProjectVo> queryProjectAll(ProjectDTO param) {
		return baseMapper.queryProjectAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public ProjectVo queryProjectByCondition(ProjectDTO param) {
		return baseMapper.queryProjectByCondition(param);
	}
}
