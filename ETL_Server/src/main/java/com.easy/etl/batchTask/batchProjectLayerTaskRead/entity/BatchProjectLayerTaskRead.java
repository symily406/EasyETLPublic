package com.easy.etl.batchTask.batchProjectLayerTaskRead.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 批处理分层任务来源-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_READ
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_READ", autoResultMap = true)
public class BatchProjectLayerTaskRead extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "READ_ID", type = IdType.ASSIGN_ID)
    private String readId;


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

    @TableField(value = "IS_HIVE")
    private Integer isHive;

    @TableField(value = "DB_SOURCE_ID")
    private String dbSourceId;

    @TableField(value = "DB_CATALOG")
    private String dbCatalog;


    @TableField(value = "DB_SCHEMA")
    private String dbSchema;

    @TableField(value = "TABLE_NAME")
    private String tableName;

    @TableField(value = "QUERY_CONDITION")
    private String queryCondition;

    @TableField(value = "SPLIT_PK")
    private String splitPk;

    @TableField(value = "FETCH_SIZE")
    private Integer fetchSize;

    @TableField(value = "TABLE_PATH")
    private String tablePath;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;



    public BatchProjectLayerTaskRead() {
        super();
    }


    public String getReadId() {
        return readId;
    }


    public void setReadId(String readId) {
        this.readId = readId;
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


    public Integer getIsHive() {
        return isHive;
    }

    public void setIsHive(Integer isHive) {
        this.isHive = isHive;
    }


    public String getDbSourceId() {
        return dbSourceId;
    }


    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }



    public String getTableName() {
        return tableName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getQueryCondition() {
        return queryCondition;
    }


    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }


    public String getSplitPk() {
        return splitPk;
    }


    public void setSplitPk(String splitPk) {
        this.splitPk = splitPk;
    }


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }


    public String getTablePath() {
        return tablePath;
    }

    public void setTablePath(String tablePath) {
        this.tablePath = tablePath;
    }

    public String getDbCatalog() {
        return dbCatalog;
    }

    public void setDbCatalog(String dbCatalog) {
        this.dbCatalog = dbCatalog;
    }

    public String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }
}
