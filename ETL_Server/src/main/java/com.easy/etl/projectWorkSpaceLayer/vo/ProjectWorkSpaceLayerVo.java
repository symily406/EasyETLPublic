package com.easy.etl.projectWorkSpaceLayer.vo;


import cn.hutool.crypto.SecureUtil;
import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
*  项目工作空间分层-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectWorkSpaceLayerVo extends ProjectWorkSpaceLayer {

    private String projectId;

    private String projectWorkSpaceId;

    private String id;

    private String workSpaceId;

    private String workSpaceType;

    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private String label;

    private String name;



    public String getLabel() {
        return getWorkSpaceLayerName();
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return getWorkSpaceLayerName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }
}
