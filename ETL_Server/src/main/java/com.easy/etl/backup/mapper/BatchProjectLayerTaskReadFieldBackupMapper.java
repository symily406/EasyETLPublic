package com.easy.etl.backup.mapper;


import java.util.List;

import com.easy.etl.backup.entity.BatchProjectLayerTaskReadFieldBackup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 批处理分层任务来源字段备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskReadFieldBackupMapper extends MapperDao<BatchProjectLayerTaskReadFieldBackup> {

}
