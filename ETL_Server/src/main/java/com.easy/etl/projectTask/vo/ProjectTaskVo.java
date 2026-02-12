package com.easy.etl.projectTask.vo;


import com.easy.etl.projectTask.entity.ProjectTask;

/**
 * 项目任务-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskVo extends ProjectTask {
    private String userName;

    private String workSpaceTypeDesc;

    private String projectName;

    private String backupTaskId;

    private int leval = 3;

    private int isEnableCluster;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkSpaceTypeDesc() {
        return workSpaceTypeDesc;
    }

    public void setWorkSpaceTypeDesc(String workSpaceTypeDesc) {
        this.workSpaceTypeDesc = workSpaceTypeDesc;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBackupTaskId() {
        return backupTaskId;
    }

    public void setBackupTaskId(String backupTaskId) {
        this.backupTaskId = backupTaskId;
    }

    public int getLeval() {
        return leval;
    }

    public void setLeval(int leval) {
        this.leval = leval;
    }

    public int getIsEnableCluster() {
        return isEnableCluster;
    }

    public void setIsEnableCluster(int isEnableCluster) {
        this.isEnableCluster = isEnableCluster;
    }
}
