package com.easy.etl.projectDbSource.dto;


import com.easy.etl.projectDbSource.entity.ProjectDbSource;

/**
*  引用的数据源-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectDbSourceDTO extends ProjectDbSource {
    private String projectName;
    private String searchKey;
    private String sourceTypeId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }
}
