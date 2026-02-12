package com.easy.etl.conf.mapper;


import com.easy.etl.conf.entity.ConfSql;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * SQL执行器配置-接口
 * @author 邵勇
 * @time 2025-10-08
 */
@Mapper
public interface ConfSqlMapper extends MapperDao<ConfSql> {


}
