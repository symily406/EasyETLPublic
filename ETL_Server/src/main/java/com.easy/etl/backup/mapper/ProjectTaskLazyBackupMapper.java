package com.easy.etl.backup.mapper;


import java.util.List;

import com.easy.etl.backup.entity.ProjectTaskLazyBackup;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目任务依懒备份-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskLazyBackupMapper extends MapperDao<ProjectTaskLazyBackup> {


}
