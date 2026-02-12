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
 * 任务同步脏数据-实体类
 *
 * @author 邵勇
 * @table TF_RUN_TASK_JOB_DIRTY_RECORD
 * @time 2025-10-08
 */
@TableName(value = "TF_RUN_TASK_JOB_DIRTY_RECORD", autoResultMap = true)
public class RunTaskJobDirtyRecord extends Model<RunTaskJobDirtyRecord> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "DIRTY_RECORD_ID", type = IdType.ASSIGN_ID)
    private String dirtyRecordId;

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
     * 错误信息
     */
    @TableField(value = "DIRTY_EXCEPTION")
    @Length(max = 0, message = "dirtyException错误信息不能超过0个字符")
    private String dirtyException;
    /**
     * 数据
     */
    @TableField(value = "DIRTY_RECORD")
    @Length(max = 0, message = "dirtyRecord数据不能超过0个字符")
    private String dirtyRecord;
    /**
     * 类型
     */
    @TableField(value = "DIRTY_RECORD_TYPE")
    @Length(max = 32, message = "dirtyRecordType类型不能超过32个字符")
    private String dirtyRecordType;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public RunTaskJobDirtyRecord() {
        super();
    }

    /**
     * 主键-数据库字段:DIRTY_RECORD_ID
     */
    public String getDirtyRecordId() {
        return dirtyRecordId;
    }

    /**
     * 主键-数据库字段:DIRTY_RECORD_ID
     */
    public void setDirtyRecordId(String dirtyRecordId) {
        this.dirtyRecordId = dirtyRecordId;
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
     * 错误信息-数据库字段:DIRTY_EXCEPTION
     */
    public String getDirtyException() {
        return dirtyException;
    }

    /**
     * 错误信息-数据库字段:DIRTY_EXCEPTION
     */
    public void setDirtyException(String dirtyException) {
        this.dirtyException = dirtyException;
    }

    /**
     * 数据-数据库字段:DIRTY_RECORD
     */
    public String getDirtyRecord() {
        return dirtyRecord;
    }

    /**
     * 数据-数据库字段:DIRTY_RECORD
     */
    public void setDirtyRecord(String dirtyRecord) {
        this.dirtyRecord = dirtyRecord;
    }

    /**
     * 类型-数据库字段:DIRTY_RECORD_TYPE
     */
    public String getDirtyRecordType() {
        return dirtyRecordType;
    }

    /**
     * 类型-数据库字段:DIRTY_RECORD_TYPE
     */
    public void setDirtyRecordType(String dirtyRecordType) {
        this.dirtyRecordType = dirtyRecordType;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
