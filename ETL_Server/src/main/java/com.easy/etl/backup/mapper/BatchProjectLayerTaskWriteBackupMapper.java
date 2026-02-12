package com.easy.etl.backup.mapper;


import com.easy.etl.backup.entity.BatchProjectLayerTaskWriteBackup;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 批处理分层任务写入备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskWriteBackupMapper extends MapperDao<BatchProjectLayerTaskWriteBackup> {

}
