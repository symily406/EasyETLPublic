package com.easy.etl.kill.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 执行器杀死任务表-实体类
 *
 * @author 邵勇
 * @table TF_KILL_JOB
 * @time 2025-10-08
 */
@TableName(value = "TF_KILL_JOB", autoResultMap = true)
public class KillJob implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "JOB_ID", type = IdType.ASSIGN_ID)
    private String jobId;

    @TableField(value = "APPLICATION_ID")
    private String applicationId;

    /**
     * 执行器IP
     */
    @TableField(value = "IP")
    @Length(max = 64, message = "ip执行器IP不能超过64个字符")
    private String ip;
    /**
     * 完成时间
     */
    @TableField(value = "COMPLETION_TIME")
    private Date completionTime;
    /**
     * 执行状态
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

    public KillJob() {
        super();
    }

    /**
     * 主键-数据库字段:JOB_ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 主键-数据库字段:JOB_ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


    /**
     * 执行器IP-数据库字段:IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 执行器IP-数据库字段:IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 完成时间-数据库字段:COMPLETION_TIME
     */
    public Date getCompletionTime() {
        return completionTime;
    }

    /**
     * 完成时间-数据库字段:COMPLETION_TIME
     */
    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    /**
     * 执行状态-数据库字段:STATUS
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
