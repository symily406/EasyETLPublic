package com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.mapper.BatchProjectLayerTaskFieldMapMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.service.IBatchProjectLayerTaskFieldMapService;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 批处理分层字段映射-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class BatchProjectLayerTaskFieldMapServiceImpl extends BaseServiceImpl<BatchProjectLayerTaskFieldMapMapper, BatchProjectLayerTaskFieldMap> implements IBatchProjectLayerTaskFieldMapService {

	@Autowired
	private ProjectTaskMapper projectTaskMapper;
	/**
	     * 保存或修改Entity
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateBatchProjectLayerTaskFieldMap(String taskId,List<BatchProjectLayerTaskFieldMap> list,int taskStep) {
		baseMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskFieldMap>()
				.eq(BatchProjectLayerTaskFieldMap::getTaskId,taskId)
		);
//修改任务步骤
		ProjectTask task=new ProjectTask();
		task.setTaskStep(taskStep);
		task.setTaskId(taskId);
		projectTaskMapper.updateById(task);
		return saveBatch(list);
	}


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<BatchProjectLayerTaskFieldMapVo> queryBatchProjectLayerTaskFieldMapAll(BatchProjectLayerTaskFieldMapDTO param) {
		return baseMapper.queryBatchProjectLayerTaskFieldMapAll(param);
	}


}
