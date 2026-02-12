package com.easy.etl.projectTaskParameter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 任务参数-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_PARAMETER
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_PARAMETER", autoResultMap = true)
public class ProjectTaskParameter extends Entity {

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
     * 参数KEY
     */
    @TableField(value = "PARAM_KEY")
    @Length(max = 32, message = "paramKey参数KEY不能超过32个字符")
    private String paramKey;
    /**
     * 参数VALUE
     */
    @TableField(value = "PARAM_VALUE")
    @Length(max = 32, message = "paramValue参数VALUE不能超过32个字符")
    private String paramValue;
    /**
     * 参数类型
     */
    @TableField(value = "PARAM_TYPE")
    private Integer paramType;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public ProjectTaskParameter() {
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

    /**
     * 参数KEY-数据库字段:PARAM_KEY
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 参数KEY-数据库字段:PARAM_KEY
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 参数VALUE-数据库字段:PARAM_VALUE
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 参数VALUE-数据库字段:PARAM_VALUE
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 参数类型-数据库字段:PARAM_TYPE
     */
    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
