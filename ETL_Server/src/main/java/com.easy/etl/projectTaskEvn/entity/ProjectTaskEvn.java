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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 工作空间分层
     */
    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    @NotEmpty(message = "projectWorkSpaceLayerRelId工作空间分层不能为空")
    @Length(max = 32, message = "projectWorkSpaceLayerRelId工作空间分层不能超过32个字符")
    private String projectWorkSpaceLayerRelId;
    /**
     * 项目空间ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
    @Length(max = 32, message = "projectWorkSpaceId项目空间ID不能超过32个字符")
    private String projectWorkSpaceId;
    /**
     * 工作空间任务类型
     */
    @TableField(value = "WORK_SPACE_TYPE")
    @Length(max = 8, message = "workSpaceType工作空间任务类型不能超过8个字符")
    private String workSpaceType;
    /**
     * 目录ID
     */
    @TableField(value = "TASK_FOLDER_ID")
    @NotEmpty(message = "taskFolderId目录ID不能为空")
    @Length(max = 32, message = "taskFolderId目录ID不能超过32个字符")
    private String taskFolderId;
    /**
     * 环境变量值
     */
    @TableField(value = "EVN_CONTENT")
    private String evnContent;

    @TableField(value = "VERSION")
    private Integer version;


    public ProjectTaskEvn() {
        super();
    }

    /**
     * ID-数据库字段:ID
     */
    public String getId() {
        return id;
    }

    /**
     * ID-数据库字段:ID
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public String getWorkSpaceType() {
        return workSpaceType;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public String getTaskFolderId() {
        return taskFolderId;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
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
