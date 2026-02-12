package com.easy.etl.batchTask.batchProjectLayerTaskWrite.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.mapper.BatchProjectLayerTaskWriteMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.service.IBatchProjectLayerTaskWriteService;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo.BatchProjectLayerTaskWriteVo;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity.BatchProjectLayerTaskWriteField;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.mapper.BatchProjectLayerTaskWriteFieldMapper;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批处理分层任务写入-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class BatchProjectLayerTaskWriteServiceImpl extends BaseServiceImpl<BatchProjectLayerTaskWriteMapper, BatchProjectLayerTaskWrite> implements IBatchProjectLayerTaskWriteService {

    @Autowired
    private BatchProjectLayerTaskWriteFieldMapper batchProjectLayerTaskWriteFieldMapper;
    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatchProjectLayerTaskWrite(BatchProjectLayerTaskWrite batchProjectLayerTaskWrite, List<ColumnInfo> columns, int taskStep) {
        //修改任务步骤
        ProjectTask task=new ProjectTask();
        task.setTaskStep(taskStep);
        task.setTaskId(batchProjectLayerTaskWrite.getTaskId());
        projectTaskMapper.updateById(task);

        //修改表格字段
        String writeId=batchProjectLayerTaskWrite.getWriteId();
        if (StringUtils.isEmpty(writeId)){
            writeId=idBulider();
        }

        batchProjectLayerTaskWriteFieldMapper.delete(new LambdaUpdateWrapper<BatchProjectLayerTaskWriteField>()
                .eq(BatchProjectLayerTaskWriteField::getTaskId,batchProjectLayerTaskWrite.getTaskId())
        );

        for (ColumnInfo col:columns) {
            String writeFieldId= SecureUtil.md5(batchProjectLayerTaskWrite.getTaskId().concat(batchProjectLayerTaskWrite.getTableName()).concat(col.getColumnName()).concat(writeId));
            BatchProjectLayerTaskWriteField writeField=new BatchProjectLayerTaskWriteField();
            writeField.setWriteFieldId(writeFieldId);
            writeField.setWriteId(writeId);
            writeField.setTaskId(batchProjectLayerTaskWrite.getTaskId());
            writeField.setProjectId(batchProjectLayerTaskWrite.getProjectId());
            writeField.setProjectWorkSpaceLayerRelId(batchProjectLayerTaskWrite.getProjectWorkSpaceLayerRelId());
            writeField.setProjectWorkSpaceId(batchProjectLayerTaskWrite.getProjectWorkSpaceId());
            writeField.setWorkSpaceType(batchProjectLayerTaskWrite.getWorkSpaceType());
            writeField.setTaskFolderId(batchProjectLayerTaskWrite.getTaskFolderId());
            writeField.setFieldName(col.getColumnName());
            writeField.setFieldType(col.getTypeName());
            writeField.setComment(col.getComment());
            writeField.setColumnSize(col.getColumnSize());
            writeField.setPoint(col.getPoint());
            writeField.setIsPrimaryKey(col.getIsPrimaryKey());
            writeField.setShowOrder(col.getShowOrder());
            batchProjectLayerTaskWriteFieldMapper.insert(writeField);
        }
        batchProjectLayerTaskWrite.setWriteId(writeId);
        return saveOrUpdate(batchProjectLayerTaskWrite);
    }

    @Override
    public BatchProjectLayerTaskWriteVo queryBatchProjectLayerTaskWrite(String taskId) {
        return baseMapper.queryBatchProjectLayerTaskWrite(taskId);
    }
}
