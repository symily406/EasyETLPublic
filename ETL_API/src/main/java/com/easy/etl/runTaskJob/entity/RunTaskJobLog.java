package com.easy.etl.runTaskJob.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 任务运行日志-实体类
 *
 * @author 邵勇
 * @table TF_RUN_TASK_JOB_LOG
 * @time 2024-01-17
 */
@TableName(value = "TF_RUN_TASK_JOB_LOG", autoResultMap = true)
public class RunTaskJobLog extends Model<RunTaskJobLog> {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
    private String logId;


    @TableField(value = "JOB_ID")
    private String jobId;

    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "LOG_CONTENT")
    private String logContent;


    @TableField(value = "STATUS")
    private Integer status;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    @TableField(value = "IS_DIRTY_RECORD")
    private Integer isDirtyRecord;


    public RunTaskJobLog() {
        super();
    }


    public String getLogId() {
        return logId;
    }


    public void setLogId(String logId) {
        this.logId = logId;
    }



    public String getJobId() {
        return jobId;
    }


    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public String getLogContent() {
        return logContent;
    }


    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDirtyRecord() {
        return isDirtyRecord;
    }

    public void setIsDirtyRecord(Integer isDirtyRecord) {
        this.isDirtyRecord = isDirtyRecord;
    }
}
