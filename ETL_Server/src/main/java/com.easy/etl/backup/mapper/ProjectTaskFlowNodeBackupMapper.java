package com.easy.etl.backup.mapper;

import com.easy.etl.backup.entity.ProjectTaskFlowNodeBackup;
import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目工作流节点备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskFlowNodeBackupMapper extends MapperDao<ProjectTaskFlowNodeBackup> {

}
