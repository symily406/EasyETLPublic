package com.easy.etl.projectWorkSpace.dto;


import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;

/**
*  项目工作空间-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectWorkSpaceDTO extends ProjectWorkSpace {

    private String queryProjectId;

    public String getQueryProjectId() {
        return queryProjectId;
    }

    public void setQueryProjectId(String queryProjectId) {
        this.queryProjectId = queryProjectId;
    }
}
