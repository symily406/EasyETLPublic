package com.easy.etl.backup.vo;


import com.easy.etl.backup.entity.ProjectTaskBackup;

/**
*  项目任务备份-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskBackupVo extends ProjectTaskBackup {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
