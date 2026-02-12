package com.easy.etl.projectTaskEvn.mapper;


import java.util.List;

import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务环境变量-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskEvnMapper extends MapperDao<ProjectTaskEvn> {


}
