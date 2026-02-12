package com.easy.etl.projectTaskEvn.service.impl;
import java.util.List;

import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.easy.etl.projectTaskEvn.mapper.ProjectTaskEvnMapper;
import com.easy.etl.projectTaskEvn.service.IProjectTaskEvnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务环境变量-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskEvnServiceImpl extends BaseServiceImpl<ProjectTaskEvnMapper, ProjectTaskEvn> implements IProjectTaskEvnService {
	@Autowired
	private ProjectTaskMapper projectTaskMapper;
	/**
	     * 保存或修改Entity
	 * @param projectTaskEvn
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateProjectTaskEvn(ProjectTaskEvn projectTaskEvn,int taskStep) {
		//修改任务步骤
		ProjectTask task=new ProjectTask();
		task.setTaskStep(taskStep);
		task.setTaskId(projectTaskEvn.getTaskId());
		projectTaskMapper.updateById(task);
		return saveOrUpdate(projectTaskEvn);
	}

}
