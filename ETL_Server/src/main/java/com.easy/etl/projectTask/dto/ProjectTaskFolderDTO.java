package com.easy.etl.projectTask.dto;


import com.easy.etl.projectTask.entity.ProjectTaskFolder;

/**
*  项目任务目录-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskFolderDTO extends ProjectTaskFolder {

    private String staffId;
    private String noTaskFolderId;

    public String getNoTaskFolderId() {
        return noTaskFolderId;
    }

    public void setNoTaskFolderId(String noTaskFolderId) {
        this.noTaskFolderId = noTaskFolderId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
