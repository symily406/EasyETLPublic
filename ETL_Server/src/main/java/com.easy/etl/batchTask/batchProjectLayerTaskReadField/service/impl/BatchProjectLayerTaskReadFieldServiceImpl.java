package com.easy.etl.batchTask.batchProjectLayerTaskReadField.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.mapper.BatchProjectLayerTaskReadFieldMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.service.IBatchProjectLayerTaskReadFieldService;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 批处理分层任务来源字段-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class BatchProjectLayerTaskReadFieldServiceImpl extends BaseServiceImpl<BatchProjectLayerTaskReadFieldMapper, BatchProjectLayerTaskReadField> implements IBatchProjectLayerTaskReadFieldService {

    @Override
    public List<BatchProjectLayerTaskReadFieldVo> queryBatchProjectLayerTaskReadFieldAll(BatchProjectLayerTaskReadFieldDTO param) {
        return baseMapper.queryBatchProjectLayerTaskReadFieldAll(param);
    }

    /**
     * 保存或修改Entity
     * @param batchProjectLayerTaskReadField
     */
    @Override
    public boolean saveOrUpdateBatchProjectLayerTaskReadField(BatchProjectLayerTaskReadField batchProjectLayerTaskReadField) {
        return saveOrUpdate(batchProjectLayerTaskReadField);
    }

    /**
     * 根据条件查询
     * @param param
     * @return
     */
    @Override
    public BatchProjectLayerTaskReadFieldVo queryBatchProjectLayerTaskReadFieldByCondition(BatchProjectLayerTaskReadFieldDTO param) {
        return baseMapper.queryBatchProjectLayerTaskReadFieldByCondition(param);
    }

    @Override
    public boolean deleteById(String readFieldId) {
        Map<String,Object> map=new HashMap<>();
        map.put("readFieldId",readFieldId);
        return baseMapper.deleteByCondition("DELETE from TF_BATCH_PROJECT_LAYER_TASK_READ_FIELD WHERE READ_FIELD_ID=:readFieldId",map);
    }
}
