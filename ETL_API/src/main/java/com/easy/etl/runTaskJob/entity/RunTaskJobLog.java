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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
    private String logId;

    /**
     * 运任任务ID
     */
    @TableField(value = "JOB_ID")
    @NotEmpty(message = "jobId运任任务ID不能为空")
    @Length(max = 32, message = "jobId运任任务ID不能超过32个字符")
    private String jobId;
    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId任务ID不能为空")
    @Length(max = 32, message = "taskId任务ID不能超过32个字符")
    private String taskId;
    /**
     * 日志内容
     */
    @TableField(value = "LOG_CONTENT")
    private String logContent;

    /**
     * 任务状态1:待分配任务,2:待执行任务,10任务正在执行,20等待上游任务,30:等待任务停止,40:任务已停止-1:执行失败,0任务执行成功
     */
    @TableField(value = "STATUS")
    private Integer status;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    /**
     * 是否有脏数据
     */
    @TableField(value = "IS_DIRTY_RECORD")
    private Integer isDirtyRecord;


    public RunTaskJobLog() {
        super();
    }

    /**
     * 日志ID-数据库字段:LOG_ID
     */
    public String getLogId() {
        return logId;
    }

    /**
     * 日志ID-数据库字段:LOG_ID
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }


    /**
     * 运任任务ID-数据库字段:JOB_ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 运任任务ID-数据库字段:JOB_ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * 任务ID-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 日志内容-数据库字段:LOG_CONTENT
     */
    public String getLogContent() {
        return logContent;
    }

    /**
     * 日志内容-数据库字段:LOG_CONTENT
     */
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
