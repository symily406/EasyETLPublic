package com.easy.etl.batchTask.batchProjectLayerTaskChannel.service;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 通道控制-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IBatchProjectLayerTaskChannelService extends IBaseService<BatchProjectLayerTaskChannel> {

	/**
	   * 保存或修改Entity
	 * @param batchProjectLayerTaskChannel
	 */
	public boolean saveOrUpdateBatchProjectLayerTaskChannel(BatchProjectLayerTaskChannel batchProjectLayerTaskChannel,int taskStep);

}
