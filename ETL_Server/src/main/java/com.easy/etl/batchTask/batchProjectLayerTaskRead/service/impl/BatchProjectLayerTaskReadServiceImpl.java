package com.easy.etl.batchTask.batchProjectLayerTaskRead.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.mapper.BatchProjectLayerTaskReadMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.service.IBatchProjectLayerTaskReadService;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.mapper.BatchProjectLayerTaskReadFieldMapper;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 批处理分层任务来源-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class BatchProjectLayerTaskReadServiceImpl extends BaseServiceImpl<BatchProjectLayerTaskReadMapper, BatchProjectLayerTaskRead> implements IBatchProjectLayerTaskReadService {
	@Autowired
	private BatchProjectLayerTaskReadFieldMapper batchProjectLayerTaskReadFieldMapper;
	@Autowired
	private ProjectTaskMapper projectTaskMapper;
	/**
	 * 保存或修改Entity
	 * @param batchProjectLayerTaskRead
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOrUpdateBatchProjectLayerTaskRead(BatchProjectLayerTaskRead batchProjectLayerTaskRead,List<ColumnInfo> columns,int taskStep) {
		//修改任务步骤
		ProjectTask task=new ProjectTask();
		task.setTaskStep(taskStep);
		task.setTaskId(batchProjectLayerTaskRead.getTaskId());
		projectTaskMapper.updateById(task);

		//修改表格字段
		String readId=batchProjectLayerTaskRead.getReadId();
		if (StringUtils.isEmpty(readId)){
			readId=idBulider();
		}

		batchProjectLayerTaskReadFieldMapper.delete(
				new LambdaUpdateWrapper<BatchProjectLayerTaskReadField>()
						.eq(BatchProjectLayerTaskReadField::getTaskId,batchProjectLayerTaskRead.getTaskId())
		);


		for (ColumnInfo col:columns) {
			String readFieldId= SecureUtil.md5(batchProjectLayerTaskRead.getTaskId().concat(batchProjectLayerTaskRead.getTableName()).concat(col.getColumnName()));
			BatchProjectLayerTaskReadField readField=new BatchProjectLayerTaskReadField();
			readField.setReadFieldId(readFieldId);
			readField.setReadId(readId);
			readField.setTaskId(batchProjectLayerTaskRead.getTaskId());
			readField.setProjectId(batchProjectLayerTaskRead.getProjectId());
			readField.setProjectWorkSpaceLayerRelId(batchProjectLayerTaskRead.getProjectWorkSpaceLayerRelId());
			readField.setProjectWorkSpaceId(batchProjectLayerTaskRead.getProjectWorkSpaceId());
			readField.setWorkSpaceType(batchProjectLayerTaskRead.getWorkSpaceType());
			readField.setTaskFolderId(batchProjectLayerTaskRead.getTaskFolderId());
			readField.setFieldName(col.getColumnName());
			readField.setFieldType(col.getTypeName());
			readField.setFieldFormat(col.getReadFieldFormat());
			readField.setComment(col.getComment());
			readField.setColumnSize(col.getColumnSize());
			readField.setPoint(col.getPoint());
			readField.setIsPrimaryKey(col.getIsPrimaryKey());
			readField.setShowOrder(col.getShowOrder());
			readField.setIsConstant(0);
			readField.setEnable(1);
			batchProjectLayerTaskReadFieldMapper.insert(readField);
		}
		batchProjectLayerTaskRead.setReadId(readId);
		//修改任务
		return saveOrUpdate(batchProjectLayerTaskRead);
	}

}
