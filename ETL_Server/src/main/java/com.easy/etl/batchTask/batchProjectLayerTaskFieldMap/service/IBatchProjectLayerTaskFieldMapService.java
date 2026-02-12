package com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.service;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 批处理分层字段映射-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IBatchProjectLayerTaskFieldMapService extends IBaseService<BatchProjectLayerTaskFieldMap> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 */
	public boolean saveOrUpdateBatchProjectLayerTaskFieldMap(String taskId,List<BatchProjectLayerTaskFieldMap> list,int taskStep);



	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<BatchProjectLayerTaskFieldMapVo> queryBatchProjectLayerTaskFieldMapAll(BatchProjectLayerTaskFieldMapDTO param);

}
