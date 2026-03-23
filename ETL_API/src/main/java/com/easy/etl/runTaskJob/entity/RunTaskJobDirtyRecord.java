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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "DIRTY_RECORD_ID", type = IdType.ASSIGN_ID)
    private String dirtyRecordId;


    @TableField(value = "JOB_ID")
    private String jobId;

    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "DIRTY_EXCEPTION")
    private String dirtyException;

    @TableField(value = "DIRTY_RECORD")
    private String dirtyRecord;

    @TableField(value = "DIRTY_RECORD_TYPE")
    private String dirtyRecordType;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public RunTaskJobDirtyRecord() {
        super();
    }


    public String getDirtyRecordId() {
        return dirtyRecordId;
    }


    public void setDirtyRecordId(String dirtyRecordId) {
        this.dirtyRecordId = dirtyRecordId;
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


    public String getDirtyException() {
        return dirtyException;
    }


    public void setDirtyException(String dirtyException) {
        this.dirtyException = dirtyException;
    }


    public String getDirtyRecord() {
        return dirtyRecord;
    }


    public void setDirtyRecord(String dirtyRecord) {
        this.dirtyRecord = dirtyRecord;
    }


    public String getDirtyRecordType() {
        return dirtyRecordType;
    }


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
