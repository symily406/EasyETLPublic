package com.easy.etl.taskJob.mapper;


import java.util.List;

import com.easy.etl.taskJob.entity.TaskJobRelyOn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 待运行任务依赖-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface TaskJobRelyOnMapper extends MapperDao<TaskJobRelyOn> {


}
