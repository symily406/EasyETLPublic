package com.easy.etl.backup.mapper;


import java.util.List;

import com.easy.etl.backup.entity.ProjectTaskEvnBackup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务环境变量变量-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskEvnBackupMapper extends MapperDao<ProjectTaskEvnBackup> {

}
