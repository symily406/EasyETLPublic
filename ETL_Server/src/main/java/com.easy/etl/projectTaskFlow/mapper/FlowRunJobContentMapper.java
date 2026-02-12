package com.easy.etl.projectTaskFlow.mapper;


import java.util.List;

import com.easy.etl.projectTaskFlow.entity.FlowRunJobContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 工作流运行JOB_内容-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface FlowRunJobContentMapper extends MapperDao<FlowRunJobContent> {

}
