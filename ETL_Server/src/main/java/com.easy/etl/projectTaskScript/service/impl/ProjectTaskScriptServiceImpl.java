package com.easy.etl.projectTaskScript.service.impl;
import java.util.List;
import java.util.Map;

import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.mapper.ProjectTaskScriptMapper;
import com.easy.etl.projectTaskScript.service.IProjectTaskScriptService;
import com.easy.etl.taskJob.mapper.TaskJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务脚本-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskScriptServiceImpl extends BaseServiceImpl<ProjectTaskScriptMapper, ProjectTaskScript> implements IProjectTaskScriptService {

	@Autowired
	private TaskJobMapper taskJobMapper;
	/**
	     * 保存或修改Entity
	 * @param projectTaskScript
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateProjectTaskScript(ProjectTaskScript projectTaskScript) {
		return saveOrUpdate(projectTaskScript);
	}

}
