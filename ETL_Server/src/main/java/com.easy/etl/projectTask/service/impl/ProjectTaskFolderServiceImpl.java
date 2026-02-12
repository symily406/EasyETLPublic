package com.easy.etl.projectTask.service.impl;

import com.easy.etl.projectTask.entity.ProjectTaskFolder;
import com.easy.etl.projectTask.mapper.ProjectTaskFolderMapper;
import com.easy.etl.projectTask.service.IProjectTaskFolderService;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 项目任务目录-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskFolderServiceImpl extends BaseServiceImpl<ProjectTaskFolderMapper, ProjectTaskFolder> implements IProjectTaskFolderService {

}
