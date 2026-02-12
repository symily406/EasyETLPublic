package com.easy.etl.backup.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.backup.entity.ProjectTaskSchedueBackup;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目任务定时备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskSchedueBackupMapper extends MapperDao<ProjectTaskSchedueBackup> {

}
