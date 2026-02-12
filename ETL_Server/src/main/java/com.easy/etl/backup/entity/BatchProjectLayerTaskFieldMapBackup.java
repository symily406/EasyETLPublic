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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;



    /**
     * 来源ID
     */
    @TableField(value = "BACKUP_ID")
    @Length(max = 32, message = "backupId来源ID不能超过32个字符")
    private String backupId;

    /**
     * 备份归属任务
     */
    @TableField(value = "BACKUP_TASK_ID")
    @NotEmpty(message = "backupTaskId备份归属任务不能为空")
    @Length(max = 32, message = "backupTaskId备份归属任务不能超过32个字符")
    private String backupTaskId;

    /**
     * 备份任务来源ID
     */
    @TableField(value = "BACKUP_READ_ID")
    @Length(max = 64, message = "backupReadId备份任务来源ID不能超过64个字符")
    private String backupReadId;

    /**
     * 备份任务写入ID
     */
    @TableField(value = "BACKUP_WRITE_ID")
    @Length(max = 32, message = "backupWriteId备份任务写入ID不能超过32个字符")
    private String backupWriteId;

    /**
     * 备份来源字段ID
     */
    @TableField(value = "BACKUP_READ_FIELD_ID")
    @Length(max = 32, message = "backupReadFieldId备份来源字段ID不能超过32个字符")
    private String backupReadFieldId;

    /**
     * 备份写入字段
     */
    @TableField(value = "BACKUP_WRITE_FIELD_ID")
    @Length(max = 32, message = "backupWriteFieldId备份写入字段不能超过32个字符")
    private String backupWriteFieldId;

    /**
     * 删除标识0:已删,1:正常
     */
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
