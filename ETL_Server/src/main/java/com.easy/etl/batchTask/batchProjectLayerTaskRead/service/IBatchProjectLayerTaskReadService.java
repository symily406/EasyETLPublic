package com.easy.etl.batchTask.batchProjectLayerTaskRead.service;


import java.util.List;

import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 批处理分层任务来源-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IBatchProjectLayerTaskReadService extends IBaseService<BatchProjectLayerTaskRead> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param batchProjectLayerTaskRead
	 */
	public boolean saveOrUpdateBatchProjectLayerTaskRead(BatchProjectLayerTaskRead batchProjectLayerTaskRead,List<ColumnInfo> columns,int taskStep);

	//================代码生成器生成代码结束=================
}
