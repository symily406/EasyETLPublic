package com.easy.etl.backup.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import org.hibernate.validator.constraints.Length;

/**
 * 任务脚本备份-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_SCRIPT_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_SCRIPT_BACKUP", autoResultMap = true)
public class ProjectTaskScriptBackup extends ProjectTaskScript {


    private static final long serialVersionUID = 1492831266398250588L;



    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;

    @TableField(value = "VERSION")
    private Integer version;

    private Integer isDel;
    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public ProjectTaskScriptBackup() {
        super();
    }


    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
