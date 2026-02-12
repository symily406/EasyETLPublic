package com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 批处理分层任务来源字段-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_READ_FIELD
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_READ_FIELD", autoResultMap = true)
public class BatchProjectLayerTaskReadField extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "READ_FIELD_ID", type = IdType.ASSIGN_ID)
    private String readFieldId;

    /**
     * 批处理分层任务来源ID
     */
    @TableField(value = "READ_ID")
    @NotEmpty(message = "readId批处理分层任务来源ID不能为空")
    @Length(max = 64, message = "readId批处理分层任务来源ID不能超过64个字符")
    private String readId;
    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 工作空间分层
     */
    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    @NotEmpty(message = "projectWorkSpaceLayerRelId工作空间分层不能为空")
    @Length(max = 32, message = "projectWorkSpaceLayerRelId工作空间分层不能超过32个字符")
    private String projectWorkSpaceLayerRelId;
    /**
     * 项目空间ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
    @Length(max = 32, message = "projectWorkSpaceId项目空间ID不能超过32个字符")
    private String projectWorkSpaceId;
    /**
     * 工作空间任务类型
     */
    @TableField(value = "WORK_SPACE_TYPE")
    @Length(max = 8, message = "workSpaceType工作空间任务类型不能超过8个字符")
    private String workSpaceType;
    /**
     * 目录ID
     */
    @TableField(value = "TASK_FOLDER_ID")
    @NotEmpty(message = "taskFolderId目录ID不能为空")
    @Length(max = 32, message = "taskFolderId目录ID不能超过32个字符")
    private String taskFolderId;
    /**
     * 字段名
     */
    @TableField(value = "FIELD_NAME")
    @NotEmpty(message = "fieldName字段名不能为空")
    @Length(max = 64, message = "fieldName字段名不能超过64个字符")
    private String fieldName;
    /**
     * 字段类型
     */
    @TableField(value = "FIELD_TYPE")
    @NotEmpty(message = "fieldType字段类型不能为空")
    @Length(max = 64, message = "fieldType字段类型不能超过64个字符")
    private String fieldType;
    /**
     * 字段注释
     */
    @TableField(value = "COMMENT")
    @Length(max = 64, message = "comment字段注释不能超过64个字符")
    private String comment;
    /**
     * 字段长度
     */
    @TableField(value = "COLUMN_SIZE")
    @Length(max = 16, message = "columnSize字段长度不能超过16个字符")
    private String columnSize;
    /**
     * 小数位数
     */
    @TableField(value = "POINT")
    @Length(max = 4, message = "point小数位数不能超过4个字符")
    private String point;
    /**
     * 是否主键
     */
    @TableField(value = "IS_PRIMARY_KEY")
    private Integer isPrimaryKey;
    /**
     * 字段格式化
     */
    @TableField(value = "FIELD_FORMAT")
    @Length(max = 64, message = "fieldFormat字段格式化不能超过64个字符")
    private String fieldFormat;
    /**
     * 是否常量
     */
    @TableField(value = "IS_CONSTANT")
    private Integer isConstant;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public BatchProjectLayerTaskReadField() {
        super();
    }

    /**
     * 主键-数据库字段:READ_FIELD_ID
     */
    public String getReadFieldId() {
        return readFieldId;
    }

    /**
     * 主键-数据库字段:READ_FIELD_ID
     */
    public void setReadFieldId(String readFieldId) {
        this.readFieldId = readFieldId;
    }


    /**
     * 批处理分层任务来源ID-数据库字段:READ_ID
     */
    public String getReadId() {
        return readId;
    }

    /**
     * 批处理分层任务来源ID-数据库字段:READ_ID
     */
    public void setReadId(String readId) {
        this.readId = readId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public String getWorkSpaceType() {
        return workSpaceType;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public String getTaskFolderId() {
        return taskFolderId;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }

    /**
     * 字段名-数据库字段:FIELD_NAME
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 字段名-数据库字段:FIELD_NAME
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 字段类型-数据库字段:FIELD_TYPE
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * 字段类型-数据库字段:FIELD_TYPE
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * 字段注释-数据库字段:COMMENT
     */
    public String getComment() {
        return comment;
    }

    /**
     * 字段注释-数据库字段:COMMENT
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 字段长度-数据库字段:COLUMN_SIZE
     */
    public String getColumnSize() {
        return columnSize;
    }

    /**
     * 字段长度-数据库字段:COLUMN_SIZE
     */
    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    /**
     * 小数位数-数据库字段:POINT
     */
    public String getPoint() {
        return point;
    }

    /**
     * 小数位数-数据库字段:POINT
     */
    public void setPoint(String point) {
        this.point = point;
    }

    /**
     * 是否主键-数据库字段:IS_PRIMARY_KEY
     */
    public Integer getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Integer isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * 字段格式化-数据库字段:FIELD_FORMAT
     */
    public String getFieldFormat() {
        return fieldFormat;
    }

    /**
     * 字段格式化-数据库字段:FIELD_FORMAT
     */
    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    /**
     * 是否常量-数据库字段:IS_CONSTANT
     */
    public Integer getIsConstant() {
        return isConstant;
    }

    public void setIsConstant(Integer isConstant) {
        this.isConstant = isConstant;
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
