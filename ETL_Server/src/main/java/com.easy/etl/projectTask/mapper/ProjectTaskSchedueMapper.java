package com.easy.etl.projectTask.mapper;


import java.util.List;

import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目任务定时-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskSchedueMapper extends MapperDao<ProjectTaskSchedue> {


}
