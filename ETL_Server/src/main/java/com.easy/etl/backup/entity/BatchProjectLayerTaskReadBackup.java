package com.easy.etl.backup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 批处理分层任务来源备份-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_READ_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_READ_BACKUP", autoResultMap = true)
public class BatchProjectLayerTaskReadBackup extends BatchProjectLayerTaskRead {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;



    /**
     * 备份来源ID
     */
    @TableField(value = "BACKUP_READ_ID")
    @Length(max = 64, message = "backupReadId备份来源ID不能超过64个字符")
    private String backupReadId;

    /**
     * 备份归属任务
     */
    @TableField(value = "BACKUP_TASK_ID")
    @NotEmpty(message = "backupTaskId备份归属任务不能为空")
    @Length(max = 32, message = "backupTaskId备份归属任务不能超过32个字符")
    private String backupTaskId;



    /**
     * 删除标识0:已删,1:正常
     */
    private Integer isDel;


    public BatchProjectLayerTaskReadBackup() {
        super();
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

    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
