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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "CHANNEL_ID", type = IdType.ASSIGN_ID)
    private String channelId;


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

    @TableField(value = "TASK_SPEED")
    private Integer taskSpeed;

    @TableField(value = "TASK_PROCESS")
    private Integer taskProcess;

    @TableField(value = "ERROR_RECORD")
    private Integer errorRecord;

    @TableField(value = "PERCENTAGE")
    private BigDecimal percentage = BigDecimal.ZERO;

    @TableField(value = "VERSION")
    private Integer version;


    public BatchProjectLayerTaskChannel() {
        super();
    }


    public String getChannelId() {
        return channelId;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
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


    public Integer getTaskSpeed() {
        return taskSpeed;
    }

    public void setTaskSpeed(Integer taskSpeed) {
        this.taskSpeed = taskSpeed;
    }


    public Integer getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(Integer taskProcess) {
        this.taskProcess = taskProcess;
    }


    public Integer getErrorRecord() {
        return errorRecord;
    }

    public void setErrorRecord(Integer errorRecord) {
        this.errorRecord = errorRecord;
    }


    public BigDecimal getPercentage() {
        return percentage;
    }


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
