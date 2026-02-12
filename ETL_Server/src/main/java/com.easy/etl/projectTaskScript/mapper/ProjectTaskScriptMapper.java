package com.easy.etl.projectTaskScript.mapper;


import java.util.List;

import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务脚本-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskScriptMapper extends MapperDao<ProjectTaskScript> {


}
