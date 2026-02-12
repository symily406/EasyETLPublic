package com.easy.etl.projectTask.service.impl;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.easy.etl.projectTask.mapper.ProjectTaskLazyMapper;
import com.easy.etl.projectTask.service.IProjectTaskLazyService;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目任务依懒-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskLazyServiceImpl extends BaseServiceImpl<ProjectTaskLazyMapper, ProjectTaskLazy> implements IProjectTaskLazyService {

	/**
	 * 保存或修改Entity
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateProjectTaskLazy(List<ProjectTaskLazy> layzs,String taskId) {
//		baseMapper.delete(new LambdaUpdateWrapper<ProjectTaskLazy>().eq(ProjectTaskLazy::getTaskId,taskId));
		saveOrUpdateBatch(layzs);
		return true;
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<ProjectTaskLazyVo> queryProjectTaskLazyPage(IPage<ProjectTaskLazyVo> page, ProjectTaskLazyDTO param) {
		return page.setRecords(baseMapper.queryProjectTaskLazyPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<ProjectTaskLazyVo> queryProjectTaskLazyAll(ProjectTaskLazyDTO param) {
		return baseMapper.queryProjectTaskLazyAll(param);
	}
}
