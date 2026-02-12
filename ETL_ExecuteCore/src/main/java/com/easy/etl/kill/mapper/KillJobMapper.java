package com.easy.etl.kill.mapper;


import com.easy.etl.kill.entity.KillJob;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 执行器杀死任务表-接口
 * @author 邵勇
 * @time 2025-10-08
 */
@Mapper
public interface KillJobMapper extends MapperDao<KillJob> {


}
