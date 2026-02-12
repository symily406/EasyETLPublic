package com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 批处理分层任务写入-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_WRITE
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_WRITE", autoResultMap = true)
public class BatchProjectLayerTaskWrite extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "WRITE_ID", type = IdType.ASSIGN_ID)
    private String writeId;

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
     * 是否是HIVE
     */
    @TableField(value = "IS_HIVE")
    private Integer isHive;

    @TableField(value = "PT")
    private String pt;
    /**
     * 数据源
     */
    @TableField(value = "DB_SOURCE_ID")
    @NotEmpty(message = "dbSourceId数据源不能为空")
    @Length(max = 32, message = "dbSourceId数据源不能超过32个字符")
    private String dbSourceId;

    @TableField(value = "DB_CATALOG")
    private String dbCatalog;

    /**
     * SCHEAM
     */
    @TableField(value = "DB_SCHEMA")
    @Length(max = 64, message = "scheamSCHEAM不能超过64个字符")
    private String dbSchema;
    /**
     * 表名
     */
    @TableField(value = "TABLE_NAME")
    @Length(max = 64, message = "tableName表名不能超过64个字符")
    private String tableName;
    /**
     * 导入前准备语句
     */
    @TableField(value = "PRE_SQL")
    @Length(max = 1024, message = "preSql导入前准备语句不能超过1024个字符")
    private String preSql;
    /**
     * 导入后准备语句
     */
    @TableField(value = "POST_SQL")
    @Length(max = 1024, message = "postSql导入后准备语句不能超过1024个字符")
    private String postSql;
    /**
     * 主键冲突
     */
    @TableField(value = "WRITE_MODE")
    @Length(max = 64, message = "writeMode主键冲突不能超过64个字符")
    private String writeMode;
    /**
     * 写入模式
     */
    @TableField(value = "HIVE_MODE")
    @Length(max = 64, message = "hiveMode写入模式不能超过64个字符")
    private String hiveMode;
    /**
     * 数据过虑条件
     */
    @TableField(value = "QUERY_CONDITION")
    @Length(max = 1024, message = "queryCondition数据过虑条件不能超过1024个字符")
    private String queryCondition;
    /**
     * 切分键
     */
    @TableField(value = "SPLIT_PK")
    @Length(max = 64, message = "splitPk切分键不能超过64个字符")
    private String splitPk;
    /**
     * 高级配置
     */
    @TableField(value = "CONFIGURATION")
    private Integer configuration;

    @TableField(value = "TABLE_PATH")
    private String tablePath;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;

    public BatchProjectLayerTaskWrite() {
        super();
    }

    /**
     * 主键-数据库字段:WRITE_ID
     */
    public String getWriteId() {
        return writeId;
    }

    /**
     * 主键-数据库字段:WRITE_ID
     */
    public void setWriteId(String writeId) {
        this.writeId = writeId;
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
     * 是否是HIVE-数据库字段:IS_HIVE
     */
    public Integer getIsHive() {
        return isHive;
    }

    public void setIsHive(Integer isHive) {
        this.isHive = isHive;
    }

    /**
     * 数据源-数据库字段:DB_SOURCE_ID
     */
    public String getDbSourceId() {
        return dbSourceId;
    }

    /**
     * 数据源-数据库字段:DB_SOURCE_ID
     */
    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }

    /**
     * 表名-数据库字段:TABLE_NAME
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 表名-数据库字段:TABLE_NAME
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 导入前准备语句-数据库字段:PRE_SQL
     */
    public String getPreSql() {
        return preSql;
    }

    /**
     * 导入前准备语句-数据库字段:PRE_SQL
     */
    public void setPreSql(String preSql) {
        this.preSql = preSql;
    }

    /**
     * 导入后准备语句-数据库字段:POST_SQL
     */
    public String getPostSql() {
        return postSql;
    }

    /**
     * 导入后准备语句-数据库字段:POST_SQL
     */
    public void setPostSql(String postSql) {
        this.postSql = postSql;
    }

    /**
     * 主键冲突-数据库字段:WRITE_MODE
     */
    public String getWriteMode() {
        return writeMode;
    }

    /**
     * 主键冲突-数据库字段:WRITE_MODE
     */
    public void setWriteMode(String writeMode) {
        this.writeMode = writeMode;
    }

    /**
     * 写入模式-数据库字段:HIVE_MODE
     */
    public String getHiveMode() {
        return hiveMode;
    }

    /**
     * 写入模式-数据库字段:HIVE_MODE
     */
    public void setHiveMode(String hiveMode) {
        this.hiveMode = hiveMode;
    }

    /**
     * 数据过虑条件-数据库字段:QUERY_CONDITION
     */
    public String getQueryCondition() {
        return queryCondition;
    }

    /**
     * 数据过虑条件-数据库字段:QUERY_CONDITION
     */
    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    /**
     * 切分键-数据库字段:SPLIT_PK
     */
    public String getSplitPk() {
        return splitPk;
    }

    /**
     * 切分键-数据库字段:SPLIT_PK
     */
    public void setSplitPk(String splitPk) {
        this.splitPk = splitPk;
    }

    /**
     * 高级配置-数据库字段:CONFIGURATION
     */
    public Integer getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Integer configuration) {
        this.configuration = configuration;
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


    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
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
