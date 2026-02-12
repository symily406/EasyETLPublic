package com.easy.etl.batchTask.batchProjectLayerTaskChannel.mapper;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 通道控制-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskChannelMapper extends MapperDao<BatchProjectLayerTaskChannel> {


}
