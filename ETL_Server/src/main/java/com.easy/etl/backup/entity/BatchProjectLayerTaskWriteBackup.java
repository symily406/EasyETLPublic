package com.easy.etl.backup.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 批处理分层任务写入备份-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_WRITE_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_WRITE_BACKUP", autoResultMap = true)
public class BatchProjectLayerTaskWriteBackup extends BatchProjectLayerTaskWrite {


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_WRITE_ID")
    private String backupWriteId;


    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;




    private Integer isDel;


    public BatchProjectLayerTaskWriteBackup() {
        super();
    }

    public String getBackupWriteId() {
        return backupWriteId;
    }

    public void setBackupWriteId(String backupWriteId) {
        this.backupWriteId = backupWriteId;
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
