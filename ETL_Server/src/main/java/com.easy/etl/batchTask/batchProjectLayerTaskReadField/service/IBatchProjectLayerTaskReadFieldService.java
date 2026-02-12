package com.easy.etl.batchTask.batchProjectLayerTaskReadField.service;


import java.io.Serializable;
import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskRead.dto.BatchProjectLayerTaskReadDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 批处理分层任务来源字段-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IBatchProjectLayerTaskReadFieldService extends IBaseService<BatchProjectLayerTaskReadField> {
    public List<BatchProjectLayerTaskReadFieldVo> queryBatchProjectLayerTaskReadFieldAll(BatchProjectLayerTaskReadFieldDTO param);

    /**
     * 保存或修改Entity
     *
     * @param batchProjectLayerTaskReadField
     */
    public boolean saveOrUpdateBatchProjectLayerTaskReadField(BatchProjectLayerTaskReadField batchProjectLayerTaskReadField);

    /**
     * 根据条件查询
     * @param param
     * @return
     */
    public BatchProjectLayerTaskReadFieldVo queryBatchProjectLayerTaskReadFieldByCondition(BatchProjectLayerTaskReadFieldDTO param);

    public boolean deleteById(String id);

}
