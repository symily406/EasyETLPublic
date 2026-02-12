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

    /**
     * 主键
     */
    @TableId(value = "PROJECT_WORK_SPACE_LAYER_REL_ID", type = IdType.ASSIGN_ID)
    private String projectWorkSpaceLayerRelId;

    /**
     * 项目空间
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
    @NotEmpty(message = "projectWorkSpaceId项目空间ID不能为空")
    @Length(max = 32, message = "projectWorkSpaceId项目空间ID不能超过32个字符")
    private String projectWorkSpaceId;

    /**
     * 项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId项目不能为空")
    @Length(max = 32, message = "projectId项目不能超过32个字符")
    private String projectId;
    /**
     * 工作空间
     */
    @TableField(value = "WORK_SPACE_ID")
    @NotEmpty(message = "workSpaceId工作空间不能为空")
    @Length(max = 32, message = "workSpaceId工作空间不能超过32个字符")
    private String workSpaceId;
    /**
     * 分层
     */
    @TableField(value = "WORK_SPACE_LAYER_ID")
    @NotEmpty(message = "workSpaceLayerId分层不能为空")
    @Length(max = 32, message = "workSpaceLayerId分层不能超过32个字符")
    private String workSpaceLayerId;

    /**
     * 启用:0未启用,1:启用
     */
    @TableField(value = "ENABLE", fill = FieldFill.INSERT)
    private Integer enable;



    public ProjectWorkSpaceLayerRel() {
        super();
    }

    /**
     * 主键-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    /**
     * 主键-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    /**
     * 项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 工作空间-数据库字段:WORK_SPACE_ID
     */
    public String getWorkSpaceId() {
        return workSpaceId;
    }

    /**
     * 工作空间-数据库字段:WORK_SPACE_ID
     */
    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    /**
     * 分层-数据库字段:WORK_SPACE_LAYER_ID
     */
    public String getWorkSpaceLayerId() {
        return workSpaceLayerId;
    }

    /**
     * 分层-数据库字段:WORK_SPACE_LAYER_ID
     */
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
