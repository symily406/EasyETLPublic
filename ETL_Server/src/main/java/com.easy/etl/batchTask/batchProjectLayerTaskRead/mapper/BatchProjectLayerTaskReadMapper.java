package com.easy.etl.batchTask.batchProjectLayerTaskRead.mapper;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.vo.BatchProjectLayerTaskReadVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 批处理分层任务来源-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskReadMapper extends MapperDao<BatchProjectLayerTaskRead> {

    public BatchProjectLayerTaskReadVo queryBatchProjectLayerTaskRead(String taskId);
}
