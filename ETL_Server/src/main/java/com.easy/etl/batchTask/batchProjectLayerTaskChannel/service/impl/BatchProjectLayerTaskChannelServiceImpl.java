package com.easy.etl.batchTask.batchProjectLayerTaskChannel.service.impl;
import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.mapper.BatchProjectLayerTaskChannelMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.service.IBatchProjectLayerTaskChannelService;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通道控制-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class BatchProjectLayerTaskChannelServiceImpl extends BaseServiceImpl<BatchProjectLayerTaskChannelMapper, BatchProjectLayerTaskChannel> implements IBatchProjectLayerTaskChannelService {
	@Autowired
	private ProjectTaskMapper projectTaskMapper;
	/**
	     * 保存或修改Entity
	 * @param batchProjectLayerTaskChannel
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateBatchProjectLayerTaskChannel(BatchProjectLayerTaskChannel batchProjectLayerTaskChannel,int taskStep) {
		//修改任务步骤
		ProjectTask task=new ProjectTask();
		task.setTaskStep(taskStep);
		task.setTaskId(batchProjectLayerTaskChannel.getTaskId());
		projectTaskMapper.updateById(task);
		return saveOrUpdate(batchProjectLayerTaskChannel);
	}


}
