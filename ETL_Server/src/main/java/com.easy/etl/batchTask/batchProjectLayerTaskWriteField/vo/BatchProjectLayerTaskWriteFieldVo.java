package com.easy.etl.batchTask.batchProjectLayerTaskWriteField.vo;


import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity.BatchProjectLayerTaskWriteField;
import org.apache.commons.lang3.StringUtils;

/**
*  批处理分层任务写入字段-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class BatchProjectLayerTaskWriteFieldVo extends BatchProjectLayerTaskWriteField {
    private String columnType;

    public String getColumnType() {
        if(StringUtils.isNotEmpty(getColumnSize())&&StringUtils.isNotEmpty(getPoint())){
            return String.format("%s(%s,%s)",getFieldType(),getColumnSize(),getPoint());
        }
        if(StringUtils.isNotEmpty(getColumnSize())){
            return String.format("%s(%s)",getFieldType(),getColumnSize());
        }
        return getFieldType();
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
