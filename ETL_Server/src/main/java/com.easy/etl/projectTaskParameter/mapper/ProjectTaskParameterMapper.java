package com.easy.etl.projectTaskParameter.mapper;


import java.util.List;

import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务参数-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskParameterMapper extends MapperDao<ProjectTaskParameter> {


}
