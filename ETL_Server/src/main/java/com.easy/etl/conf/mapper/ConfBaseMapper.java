package com.easy.etl.conf.mapper;


import com.easy.etl.conf.entity.ConfBase;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基础配置-接口
 * @author 邵勇
 * @time 2025-10-08
 */
@Mapper
public interface ConfBaseMapper extends MapperDao<ConfBase> {


}
