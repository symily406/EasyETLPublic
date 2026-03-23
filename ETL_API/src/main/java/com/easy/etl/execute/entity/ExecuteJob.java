package com.easy.etl.execute.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 执行器任务表-实体类
 *
 * @author 邵勇
 * @table TF_EXECUTE_JOB
 * @time 2025-10-08
 */
@TableName(value = "TF_EXECUTE_JOB", autoResultMap = true)
public class ExecuteJob implements Serializable {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "JOB_ID", type = IdType.ASSIGN_ID)
    private String jobId;


    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "APPLICATION_ID")
    private String applicationId;



    @TableField(value = "DB_TYPE")
    private String dbType;

    @TableField(value = "TASK_TYPE")
    private Integer taskType;

    @TableField(value = "ADD_TYPE")
    private Integer addType;

    @TableField(value = "HAS_RESULT")
    private Integer hasResult;

    @TableField(value = "RUN_NUM")
    private Integer runNum;

    @TableField(value = "IP")
    private String ip;

    @TableField(value = "COMPLETION_TIME")
    private Date completionTime;

    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "IS_MONITOR")
    private Integer isMonitor;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public ExecuteJob() {
        super();
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


    public String getDbType() {
        return dbType;
    }


    public void setDbType(String dbType) {
        this.dbType = dbType;
    }


    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }


    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }


    public Integer getHasResult() {
        return hasResult;
    }

    public void setHasResult(Integer hasResult) {
        this.hasResult = hasResult;
    }


    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public Date getCompletionTime() {
        return completionTime;
    }


    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(Integer isMonitor) {
        this.isMonitor = isMonitor;
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
