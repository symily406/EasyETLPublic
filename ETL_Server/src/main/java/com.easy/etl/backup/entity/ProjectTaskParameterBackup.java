package com.easy.etl.backup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 任务环境变量备份-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_PARAMETER_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_PARAMETER_BACKUP", autoResultMap = true)
public class ProjectTaskParameterBackup extends ProjectTaskParameter {


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_ID")
    private String backupId;


    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;


    private Integer isDel;


    public ProjectTaskParameterBackup() {
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

    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
