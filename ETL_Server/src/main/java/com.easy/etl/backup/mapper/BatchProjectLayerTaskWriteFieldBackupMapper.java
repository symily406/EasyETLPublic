package com.easy.etl.backup.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.backup.entity.BatchProjectLayerTaskWriteFieldBackup;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 批处理分层任务写入字段备份-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskWriteFieldBackupMapper extends MapperDao<BatchProjectLayerTaskWriteFieldBackup> {


}
