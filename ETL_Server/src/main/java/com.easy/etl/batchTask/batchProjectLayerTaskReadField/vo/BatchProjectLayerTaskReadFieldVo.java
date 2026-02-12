package com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo;


import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import org.apache.commons.lang3.StringUtils;

/**
*  批处理分层任务来源字段-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class BatchProjectLayerTaskReadFieldVo extends BatchProjectLayerTaskReadField {

    private int isNum;

    private String columnType;

    private String backupReadFieldId;

    private String backupReadId;

    private String backupTaskId;


    public int getIsNum() {
        return isNum;
    }

    public void setIsNum(int isNum) {
        this.isNum = isNum;
    }

    public String getColumnType() {
        if(StringUtils.isNotEmpty(getColumnSize())&&StringUtils.isNotEmpty(getPoint())){
            return String.format("%s(%s,%s)",getFieldType(),getColumnSize(),getPoint());
        }
        if(StringUtils.isNotEmpty(getColumnSize())){
            return String.format("%s(%s)",getFieldType(),getColumnSize());
        }
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getBackupReadFieldId() {
        return backupReadFieldId;
    }

    public void setBackupReadFieldId(String backupReadFieldId) {
        this.backupReadFieldId = backupReadFieldId;
    }

    public String getBackupReadId() {
        return backupReadId;
    }

    public void setBackupReadId(String backupReadId) {
        this.backupReadId = backupReadId;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }
}
