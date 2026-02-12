package com.easy.etl.backup.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import org.hibernate.validator.constraints.Length;

/**
 * 通道控制备份-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_CHANNEL_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_CHANNEL_BACKUP", autoResultMap = true)
public class BatchProjectLayerTaskChannelBackup extends BatchProjectLayerTaskChannel {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;


    /**
     * 来源ID
     */
    @TableField(value = "BACKUP_CHANNEL_ID")
    @Length(max = 32, message = "backupChannelId来源ID不能超过32个字符")
    private String backupChannelId;

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


    public BatchProjectLayerTaskChannelBackup() {
        super();
    }

    public String getBackupChannelId() {
        return backupChannelId;
    }

    public void setBackupChannelId(String backupChannelId) {
        this.backupChannelId = backupChannelId;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
