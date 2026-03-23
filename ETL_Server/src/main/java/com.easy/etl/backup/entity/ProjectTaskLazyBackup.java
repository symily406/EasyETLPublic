package com.easy.etl.backup.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 项目任务依懒备份-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_LAZY_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_LAZY_BACKUP", autoResultMap = true)
public class ProjectTaskLazyBackup extends ProjectTaskLazy {


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_TASK_LAZY_ID")
    private String backupTaskLazyId;


    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;


    @TableField(value = "BACKUP_LAZY_TASK_ID")
    private String backupLazyTaskId;



    public ProjectTaskLazyBackup() {
        super();
    }

    public String getBackupTaskLazyId() {
        return backupTaskLazyId;
    }

    public void setBackupTaskLazyId(String backupTaskLazyId) {
        this.backupTaskLazyId = backupTaskLazyId;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public String getBackupLazyTaskId() {
        return backupLazyTaskId;
    }

    public void setBackupLazyTaskId(String backupLazyTaskId) {
        this.backupLazyTaskId = backupLazyTaskId;
    }
}
