package com.easy.etl.projectTask.vo;


import com.easy.etl.projectTask.entity.ProjectTaskLazy;

import java.util.Date;
import java.util.List;

/**
*  项目任务依懒-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskLazyVo extends ProjectTaskLazy {
    private String taskName;

    private String corn;

    private List<Date> runTimes;

    private String backupTaskLazyId;

    private String backupTaskId;

    private String backupLazyTaskId;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public List<Date> getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(List<Date> runTimes) {
        this.runTimes = runTimes;
    }
}
