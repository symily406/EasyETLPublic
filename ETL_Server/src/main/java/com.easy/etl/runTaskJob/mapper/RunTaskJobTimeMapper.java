package com.easy.etl.runTaskJob.mapper;


import java.util.List;

import com.easy.etl.runTaskJob.entity.RunTaskJobTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务运行时间-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface RunTaskJobTimeMapper extends MapperDao<RunTaskJobTime> {


}
