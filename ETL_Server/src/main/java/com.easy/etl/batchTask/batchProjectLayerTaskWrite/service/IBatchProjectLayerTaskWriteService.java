package com.easy.etl.batchTask.batchProjectLayerTaskWrite.service;


import java.util.List;

import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo.BatchProjectLayerTaskWriteVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 批处理分层任务写入-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IBatchProjectLayerTaskWriteService extends IBaseService<BatchProjectLayerTaskWrite> {

    /**
     * 保存或修改Entity
     * @param batchProjectLayerTaskWrite
     */
    public boolean saveOrUpdateBatchProjectLayerTaskWrite(BatchProjectLayerTaskWrite batchProjectLayerTaskWrite, List<ColumnInfo> columns, int taskStep);

    public BatchProjectLayerTaskWriteVo queryBatchProjectLayerTaskWrite(String taskId);

}
