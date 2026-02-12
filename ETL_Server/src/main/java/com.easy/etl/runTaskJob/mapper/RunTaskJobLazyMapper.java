package com.easy.etl.runTaskJob.mapper;


import com.easy.etl.runTaskJob.entity.RunTaskJobLazy;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务运行依懒-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface RunTaskJobLazyMapper extends MapperDao<RunTaskJobLazy> {

}
