package com.easy.etl.runTaskJob.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 任务运行依懒-实体类
 *
 * @author 邵勇
 * @table TF_RUN_TASK_JOB_LAZY
 * @time 2024-01-17
 */
@TableName(value = "TF_RUN_TASK_JOB_LAZY", autoResultMap = true)
public class RunTaskJobLazy extends Model<RunTaskJobLazy> {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "LAZY_ID", type = IdType.ASSIGN_ID)
    private String lazyId;


    @TableField(value = "JOB_ID")
    private String jobId;

    @TableField(value = "TASK_ID")
    private String taskId;


    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public RunTaskJobLazy() {
        super();
    }


    public String getLazyId() {
        return lazyId;
    }


    public void setLazyId(String lazyId) {
        this.lazyId = lazyId;
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

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
