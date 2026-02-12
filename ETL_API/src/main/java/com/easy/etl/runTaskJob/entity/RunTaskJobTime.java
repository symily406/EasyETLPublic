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
import java.util.Date;

/**
 * 任务运行时间-实体类
 *
 * @author 邵勇
 * @table TF_RUN_TASK_JOB_TIME
 * @time 2024-01-17
 */
@TableName(value = "TF_RUN_TASK_JOB_TIME", autoResultMap = true)
public class RunTaskJobTime extends Model<RunTaskJobTime> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "JOB_ID", type = IdType.ASSIGN_ID)
    private String jobId;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId任务ID不能为空")
    @Length(max = 32, message = "taskId任务ID不能超过32个字符")
    private String taskId;
    /**
     * 任务运行开始时间
     */
    @TableField(value = "START_TIME")
    private Date startTime;
    /**
     * 任务运行结束时间
     */
    @TableField(value = "END_TIME")
    private Date endTime;
    /**
     * 用时
     */
    @TableField(value = "USAGE_TIME")
    private long usageTime;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    public RunTaskJobTime() {
        super();
    }

    /**
     * 运行任务ID-数据库字段:JOB_ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 运行任务ID-数据库字段:JOB_ID
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
     * 任务运行开始时间-数据库字段:START_TIME
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 任务运行开始时间-数据库字段:START_TIME
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 任务运行结束时间-数据库字段:END_TIME
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 任务运行结束时间-数据库字段:END_TIME
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 用时-数据库字段:USAGE_TIME
     */
    public long getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(long usageTime) {
        this.usageTime = usageTime;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }
}
