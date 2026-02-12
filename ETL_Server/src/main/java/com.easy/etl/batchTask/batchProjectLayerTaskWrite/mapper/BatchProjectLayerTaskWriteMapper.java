package com.easy.etl.batchTask.batchProjectLayerTaskWrite.mapper;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo.BatchProjectLayerTaskWriteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 批处理分层任务写入-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskWriteMapper extends MapperDao<BatchProjectLayerTaskWrite> {
    public BatchProjectLayerTaskWriteVo queryBatchProjectLayerTaskWrite(String taskId);
}
