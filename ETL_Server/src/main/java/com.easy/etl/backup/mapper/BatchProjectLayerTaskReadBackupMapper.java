package com.easy.etl.backup.mapper;


import com.easy.etl.backup.entity.BatchProjectLayerTaskReadBackup;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 批处理分层任务来源备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskReadBackupMapper extends MapperDao<BatchProjectLayerTaskReadBackup> {


}
