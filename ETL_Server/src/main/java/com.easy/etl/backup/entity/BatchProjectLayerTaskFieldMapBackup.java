package com.easy.etl.backup.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 批处理分层字段映射备份-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_FIELD_MAP_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_FIELD_MAP_BACKUP", autoResultMap = true)
public class BatchProjectLayerTaskFieldMapBackup extends BatchProjectLayerTaskFieldMap {


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_ID")
    private String backupId;


    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;


    @TableField(value = "BACKUP_READ_ID")
    private String backupReadId;


    @TableField(value = "BACKUP_WRITE_ID")
    private String backupWriteId;


    @TableField(value = "BACKUP_READ_FIELD_ID")
    private String backupReadFieldId;


    @TableField(value = "BACKUP_WRITE_FIELD_ID")
    private String backupWriteFieldId;


    private Integer isDel;


    public BatchProjectLayerTaskFieldMapBackup() {
        super();
    }

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public String getBackupReadId() {
        return backupReadId;
    }

    public void setBackupReadId(String backupReadId) {
        this.backupReadId = backupReadId;
    }

    public String getBackupWriteId() {
        return backupWriteId;
    }

    public void setBackupWriteId(String backupWriteId) {
        this.backupWriteId = backupWriteId;
    }

    public String getBackupReadFieldId() {
        return backupReadFieldId;
    }

    public void setBackupReadFieldId(String backupReadFieldId) {
        this.backupReadFieldId = backupReadFieldId;
    }

    public String getBackupWriteFieldId() {
        return backupWriteFieldId;
    }

    public void setBackupWriteFieldId(String backupWriteFieldId) {
        this.backupWriteFieldId = backupWriteFieldId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
