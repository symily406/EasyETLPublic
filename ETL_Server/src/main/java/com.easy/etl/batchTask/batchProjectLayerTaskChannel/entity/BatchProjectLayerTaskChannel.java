package com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * 通道控制-实体类
 *
 * @author 邵勇
 * @table TF_BATCH_PROJECT_LAYER_TASK_CHANNEL
 * @time 2024-01-17
 */
@TableName(value = "TF_BATCH_PROJECT_LAYER_TASK_CHANNEL", autoResultMap = true)
public class BatchProjectLayerTaskChannel extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "CHANNEL_ID", type = IdType.ASSIGN_ID)
    private String channelId;

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
     * 速度
     */
    @TableField(value = "TASK_SPEED")
    private Integer taskSpeed;
    /**
     * 作业并发数
     */
    @TableField(value = "TASK_PROCESS")
    private Integer taskProcess;
    /**
     * 错误记录数
     */
    @TableField(value = "ERROR_RECORD")
    private Integer errorRecord;
    /**
     * 错误比例
     */
    @TableField(value = "PERCENTAGE")
    private BigDecimal percentage = BigDecimal.ZERO;

    @TableField(value = "VERSION")
    private Integer version;


    public BatchProjectLayerTaskChannel() {
        super();
    }

    /**
     * 主键-数据库字段:CHANNEL_ID
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 主键-数据库字段:CHANNEL_ID
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
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
     * 速度-数据库字段:TASK_SPEED
     */
    public Integer getTaskSpeed() {
        return taskSpeed;
    }

    public void setTaskSpeed(Integer taskSpeed) {
        this.taskSpeed = taskSpeed;
    }

    /**
     * 作业并发数-数据库字段:TASK_PROCESS
     */
    public Integer getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(Integer taskProcess) {
        this.taskProcess = taskProcess;
    }

    /**
     * 错误记录数-数据库字段:ERROR_RECORD
     */
    public Integer getErrorRecord() {
        return errorRecord;
    }

    public void setErrorRecord(Integer errorRecord) {
        this.errorRecord = errorRecord;
    }

    /**
     * 错误比例-数据库字段:PERCENTAGE
     */
    public BigDecimal getPercentage() {
        return percentage;
    }

    /**
     * 错误比例-数据库字段:PERCENTAGE
     */
    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
