package com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 批处理分层任务写入字段-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_WRITE_FIELD
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_WRITE_FIELD", autoResultMap = true)
public class BatchProjectLayerTaskWriteField extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "WRITE_FIELD_ID", type = IdType.ASSIGN_ID)
    private String writeFieldId;


    @TableField(value = "WRITE_ID")
    private String writeId;

    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    private String projectWorkSpaceLayerRelId;

    @TableField(value = "PROJECT_WORK_SPACE_ID")
    private String projectWorkSpaceId;

    @TableField(value = "WORK_SPACE_TYPE")
    private String workSpaceType;

    @TableField(value = "TASK_FOLDER_ID")
    private String taskFolderId;

    @TableField(value = "FIELD_NAME")
    private String fieldName;

    @TableField(value = "FIELD_TYPE")
    private String fieldType;

    @TableField(value = "COMMENT")
    private String comment;

    @TableField(value = "COLUMN_SIZE")
    private String columnSize;

    @TableField(value = "POINT")
    private String point;

    @TableField(value = "IS_PRIMARY_KEY")
    private Integer isPrimaryKey;

    @TableField(value = "FIELD_FORMAT")
    private String fieldFormat;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public BatchProjectLayerTaskWriteField() {
        super();
    }


    public String getWriteFieldId() {
        return writeFieldId;
    }


    public void setWriteFieldId(String writeFieldId) {
        this.writeFieldId = writeFieldId;
    }



    public String getWriteId() {
        return writeId;
    }


    public void setWriteId(String writeId) {
        this.writeId = writeId;
    }


    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }


    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }


    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }


    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }


    public String getWorkSpaceType() {
        return workSpaceType;
    }


    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }


    public String getTaskFolderId() {
        return taskFolderId;
    }


    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }


    public String getFieldName() {
        return fieldName;
    }


    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public String getFieldType() {
        return fieldType;
    }


    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getColumnSize() {
        return columnSize;
    }


    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }


    public String getPoint() {
        return point;
    }


    public void setPoint(String point) {
        this.point = point;
    }


    public Integer getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Integer isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }


    public String getFieldFormat() {
        return fieldFormat;
    }


    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
