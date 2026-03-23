package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目工作流-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_FLOW
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_FLOW", autoResultMap = true)
public class ProjectTaskFlow extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "TASK_ID", type = IdType.ASSIGN_ID)
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

    @TableField(value = "FLOW_CONTENT")
    private String flowContent;

    @TableField(value = "VERSION")
    private Integer version;




    public ProjectTaskFlow() {
        super();
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


    public String getFlowContent() {
        return flowContent;
    }


    public void setFlowContent(String flowContent) {
        this.flowContent = flowContent;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
