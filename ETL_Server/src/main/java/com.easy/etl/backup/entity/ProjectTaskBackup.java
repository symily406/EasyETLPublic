package com.easy.etl.backup.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import com.easy.etl.projectTask.entity.ProjectTask;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 项目任务备份-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_BACKUP
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_BACKUP", autoResultMap = true)
public class ProjectTaskBackup extends ProjectTask {


    private static final long serialVersionUID = 1492831266398250588L;




    @TableField(value = "BACKUP_TASK_ID")
    private String backupTaskId;


    private Integer isDel;
    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public ProjectTaskBackup() {
        super();
    }


    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }
}
