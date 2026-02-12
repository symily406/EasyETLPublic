package com.easy.etl.fieldTypeDict.dto;


import com.easy.etl.fieldTypeDict.entity.DbFieldTypeDict;

/**
*  数据库字段类型映射-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class DbFieldTypeDictDTO extends DbFieldTypeDict {
    private String taskId;
    private String dbSourceId;

    private String scheam;


    private String sourceTableName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDbSourceId() {
        return dbSourceId;
    }

    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }

    public String getScheam() {
        return scheam;
    }

    public void setScheam(String scheam) {
        this.scheam = scheam;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }
}
