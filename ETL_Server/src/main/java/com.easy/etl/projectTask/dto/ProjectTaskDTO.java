package com.easy.etl.projectTask.dto;


import com.easy.etl.projectTask.entity.ProjectTask;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
*  项目任务-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskDTO extends ProjectTask {

    private List<String> taskIds;

    private String keyword;

    private int showFolderOnly;

    private List<String> projectIds;


    private String orderBy;

    private List<String> noWorkSpaceTypes;

    private String currentTaskId;




    public int getShowFolderOnly() {
        return showFolderOnly;
    }

    public void setShowFolderOnly(int showFolderOnly) {
        this.showFolderOnly = showFolderOnly;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = Arrays.asList(StringUtils.split(projectIds,","));
    }
    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    public String getOrderBy() {
        if (StringUtils.isBlank(orderBy)){
            return "D.SHOW_ORDER DESC,D.ADD_TIME ASC";
        }
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<String> getNoWorkSpaceTypes() {
        return noWorkSpaceTypes;
    }

    public void setNoWorkSpaceTypes(List<String> noWorkSpaceTypes) {
        this.noWorkSpaceTypes = noWorkSpaceTypes;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }
}
