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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "LAZY_ID", type = IdType.ASSIGN_ID)
    private String lazyId;

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
     * 添加时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public RunTaskJobLazy() {
        super();
    }

    /**
     * 主键-数据库字段:LAZY_ID
     */
    public String getLazyId() {
        return lazyId;
    }

    /**
     * 主键-数据库字段:LAZY_ID
     */
    public void setLazyId(String lazyId) {
        this.lazyId = lazyId;
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

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
