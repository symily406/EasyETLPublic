package com.easy.etl.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目工作空间分层关联-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_WORK_SPACE_LAYER_REL
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_WORK_SPACE_LAYER_REL", autoResultMap = true)
public class ProjectWorkSpaceLayerRel extends Entity {

    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "PROJECT_WORK_SPACE_LAYER_REL_ID", type = IdType.ASSIGN_ID)
    private String projectWorkSpaceLayerRelId;


    @TableField(value = "PROJECT_WORK_SPACE_ID")
    private String projectWorkSpaceId;


    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "WORK_SPACE_ID")
    private String workSpaceId;

    @TableField(value = "WORK_SPACE_LAYER_ID")
    private String workSpaceLayerId;


    @TableField(value = "ENABLE", fill = FieldFill.INSERT)
    private Integer enable;



    public ProjectWorkSpaceLayerRel() {
        super();
    }


    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }


    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }


    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getWorkSpaceId() {
        return workSpaceId;
    }


    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }


    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }


    public void setWorkSpaceLayerId(String workSpaceLayerId) {
        this.workSpaceLayerId = workSpaceLayerId;
    }

    @Override
    public Integer getEnable() {
        return enable;
    }

    @Override
    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
