package com.easy.etl.execute.mapper;


import com.easy.etl.execute.entity.ExecuteJob;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ExecuteJobMapper extends MapperDao<ExecuteJob> {

}
