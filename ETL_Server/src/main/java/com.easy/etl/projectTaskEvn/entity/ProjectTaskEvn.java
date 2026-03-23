package com.easy.etl.projectTaskEvn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 任务环境变量-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_EVN
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_EVN", autoResultMap = true)
public class ProjectTaskEvn extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    private String projectWorkSpaceLayerRelId;

    @TableField(value = "PROJECT_WORK_SPACE_ID")
    private String projectWorkSpaceId;

    @TableField(value = "WORK_SPACE_TYPE")
    private String workSpaceType;

    @TableField(value = "TASK_FOLDER_ID")
    private String taskFolderId;

    @TableField(value = "EVN_CONTENT")
    private String evnContent;

    @TableField(value = "VERSION")
    private Integer version;


    public ProjectTaskEvn() {
        super();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }



    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
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


    public String getWorkSpaceType() {
        return workSpaceType;
    }


    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }


    public String getTaskFolderId() {
        return taskFolderId;
    }


    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }

    public String getEvnContent() {
        return evnContent;
    }

    public void setEvnContent(String evnContent) {
        this.evnContent = evnContent;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
