package com.easy.etl.projectTaskFlow.mapper;


import com.easy.etl.projectTaskFlow.entity.FlowJobContent;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工作流JOB_内容-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface FlowJobContentMapper extends MapperDao<FlowJobContent> {


}
