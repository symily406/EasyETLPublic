package com.easy.etl.project.vo;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
*  项目工作空间分层关联-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectWorkSpaceLayerRelVo  {

    /**
     * 主键
     */
    private String projectWorkSpaceLayerRelId;

    private String projectWorkSpaceId;

    /**
     * 工作空间
     */
    private String workSpaceId;

    /**
     * 工作空间名称
     */
    private String workSpaceName;

    /**
     * 工作空间任务类型
     */
    private String workSpaceType;

    /**
     * 分层
     */
    private String workSpaceLayerId;

    /**
     * 分层名称
     */
    private String workSpaceLayerName;



    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    public String getWorkSpaceName() {
        return workSpaceName;
    }

    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }

    public void setWorkSpaceLayerId(String workSpaceLayerId) {
        this.workSpaceLayerId = workSpaceLayerId;
    }

    public String getWorkSpaceLayerName() {
        return workSpaceLayerName;
    }

    public void setWorkSpaceLayerName(String workSpaceLayerName) {
        this.workSpaceLayerName = workSpaceLayerName;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }
}
