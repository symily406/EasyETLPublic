package com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

/**
 * 批处理分层字段映射-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_FIELD_MAP
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_FIELD_MAP", autoResultMap = true)
public class BatchProjectLayerTaskFieldMap extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

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
     * 任务来源ID
     */
    @TableField(value = "READ_ID")
    @NotEmpty(message = "readId任务来源ID不能为空")
    @Length(max = 64, message = "readId任务来源ID不能超过64个字符")
    private String readId;
    /**
     * 任务写入ID
     */
    @TableField(value = "WRITE_ID")
    @NotEmpty(message = "writeId任务写入ID不能为空")
    @Length(max = 64, message = "writeId任务写入ID不能超过64个字符")
    private String writeId;
    /**
     * 来源字段
     */
    @TableField(value = "READ_FIELD_ID")
    @NotEmpty(message = "readFieldId来源字段不能为空")
    @Length(max = 32, message = "readFieldId来源字段不能超过32个字符")
    private String readFieldId;
    /**
     * 写入字段
     */
    @TableField(value = "WRITE_FIELD_ID")
    @NotEmpty(message = "writeFieldId写入字段不能为空")
    @Length(max = 32, message = "writeFieldId写入字段不能超过32个字符")
    private String writeFieldId;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public BatchProjectLayerTaskFieldMap() {
        super();
    }

    /**
     * 主键-数据库字段:ID
     */
    public String getId() {
        return id;
    }

    /**
     * 主键-数据库字段:ID
     */
    public void setId(String id) {
        this.id = id;
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
     * 任务来源ID-数据库字段:READ_ID
     */
    public String getReadId() {
        return readId;
    }

    /**
     * 任务来源ID-数据库字段:READ_ID
     */
    public void setReadId(String readId) {
        this.readId = readId;
    }

    /**
     * 任务写入ID-数据库字段:WRITE_ID
     */
    public String getWriteId() {
        return writeId;
    }

    /**
     * 任务写入ID-数据库字段:WRITE_ID
     */
    public void setWriteId(String writeId) {
        this.writeId = writeId;
    }

    /**
     * 来源字段-数据库字段:READ_FIELD_ID
     */
    public String getReadFieldId() {
        return readFieldId;
    }

    /**
     * 来源字段-数据库字段:READ_FIELD_ID
     */
    public void setReadFieldId(String readFieldId) {
        this.readFieldId = readFieldId;
    }

    /**
     * 写入字段-数据库字段:WRITE_FIELD_ID
     */
    public String getWriteFieldId() {
        return writeFieldId;
    }

    /**
     * 写入字段-数据库字段:WRITE_FIELD_ID
     */
    public void setWriteFieldId(String writeFieldId) {
        this.writeFieldId = writeFieldId;
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
