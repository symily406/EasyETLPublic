package com.easy.etl.projectDbSource.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectDbSource.dto.ProjectDbSourceDTO;
import com.easy.etl.projectDbSource.entity.ProjectDbSource;
import com.easy.etl.projectDbSource.mapper.ProjectDbSourceMapper;
import com.easy.etl.projectDbSource.service.IProjectDbSourceService;
import com.easy.etl.projectDbSource.vo.ProjectDbSourceVo;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 引用的数据源-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectDbSourceServiceImpl extends BaseServiceImpl<ProjectDbSourceMapper, ProjectDbSource> implements IProjectDbSourceService {

	/**
	     * 保存或修改Entity
	 * @param dbJson
	 */
	@Override
	public boolean saveOrUpdateProjectDbSource(String dbJson) {
		List<ProjectDbSource> list= JsonKit.parseList(dbJson,ProjectDbSource.class);
		for (ProjectDbSource projectDbSource:list) {
			projectDbSource.setIsDel(1);
			saveOrUpdate(projectDbSource);
		}
		return true;
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<ProjectDbSourceVo> queryProjectDbSourcePage(IPage<ProjectDbSourceVo> page, ProjectDbSourceDTO param) {
		return page.setRecords(baseMapper.queryProjectDbSourcePage(page, param));
	}

	@Override
	public List<ProjectDbSourceVo> queryProjectDbSourceAll(ProjectDbSourceDTO param) {
		return baseMapper.queryProjectDbSourceAll(param);
	}

}
