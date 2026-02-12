package com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo;


import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;

/**
 * 批处理分层字段映射-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class BatchProjectLayerTaskFieldMapVo extends BatchProjectLayerTaskFieldMap {
    private String readFieldName;
    private String readFieldType;
    private String readFieldFormat;
    private String readColumnSize;
    private String readPoint;
    private String writeFieldName;
    private String writeFieldType;
    private String writeColumnSize;
    private String writePoint;
    private String sourceId;
    private String targetId;

    public String getReadFieldName() {
        return readFieldName;
    }

    public void setReadFieldName(String readFieldName) {
        this.readFieldName = readFieldName;
    }

    public String getReadFieldType() {
        return readFieldType;
    }

    public void setReadFieldType(String readFieldType) {
        this.readFieldType = readFieldType;
    }

    public String getReadFieldFormat() {
        return readFieldFormat;
    }

    public void setReadFieldFormat(String readFieldFormat) {
        this.readFieldFormat = readFieldFormat;
    }

    public String getWriteFieldName() {
        return writeFieldName;
    }

    public void setWriteFieldName(String writeFieldName) {
        this.writeFieldName = writeFieldName;
    }

    public String getWriteFieldType() {
        return writeFieldType;
    }

    public void setWriteFieldType(String writeFieldType) {
        this.writeFieldType = writeFieldType;
    }

    public String getSourceId() {
        return getReadFieldId();
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return getWriteFieldId();
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getReadColumnSize() {
        return readColumnSize;
    }

    public void setReadColumnSize(String readColumnSize) {
        this.readColumnSize = readColumnSize;
    }

    public String getWriteColumnSize() {
        return writeColumnSize;
    }

    public void setWriteColumnSize(String writeColumnSize) {
        this.writeColumnSize = writeColumnSize;
    }

    public String getWritePoint() {
        return writePoint;
    }

    public void setWritePoint(String writePoint) {
        this.writePoint = writePoint;
    }

    public String getReadPoint() {
        return readPoint;
    }

    public void setReadPoint(String readPoint) {
        this.readPoint = readPoint;
    }
}
