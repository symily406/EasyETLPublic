package com.easy.etl.conf.mapper;


import com.easy.etl.conf.entity.ConfYarn;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * yarn配置-接口
 *
 * @author 邵勇
 * @time 2025-10-08
 */
@Mapper
public interface ConfYarnMapper extends MapperDao<ConfYarn> {

}
