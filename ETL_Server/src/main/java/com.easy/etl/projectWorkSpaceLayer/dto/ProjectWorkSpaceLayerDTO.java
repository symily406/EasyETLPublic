package com.easy.etl.projectWorkSpaceLayer.dto;


import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;

/**
*  项目工作空间分层-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectWorkSpaceLayerDTO extends ProjectWorkSpaceLayer {
    private String queryProjectId;

    public String getQueryProjectId() {
        return queryProjectId;
    }

    public void setQueryProjectId(String queryProjectId) {
        this.queryProjectId = queryProjectId;
    }
}
