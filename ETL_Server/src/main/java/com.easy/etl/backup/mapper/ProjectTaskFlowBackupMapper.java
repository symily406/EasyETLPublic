package com.easy.etl.backup.mapper;


import com.easy.etl.backup.entity.ProjectTaskFlowBackup;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目工作流备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskFlowBackupMapper extends MapperDao<ProjectTaskFlowBackup> {

}
