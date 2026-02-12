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

    @TableField(value = "APPLICATION_ID")
    private String applicationId;


    /**
     * 数据库类型
     */
    @TableField(value = "DB_TYPE")
    @Length(max = 32, message = "dbType数据库类型不能超过32个字符")
    private String dbType;
    /**
     * 任务类型
     */
    @TableField(value = "TASK_TYPE")
    private Integer taskType;
    /**
     * 任务添加方式
     */
    @TableField(value = "ADD_TYPE")
    private Integer addType;
    /**
     * 任务是否有返回结果
     */
    @TableField(value = "HAS_RESULT")
    private Integer hasResult;
    /**
     * 运行次数
     */
    @TableField(value = "RUN_NUM")
    private Integer runNum;
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
     * 任务监控
     */
    @TableField(value = "IS_MONITOR")
    private Integer isMonitor;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    public ExecuteJob() {
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
     * 数据库类型-数据库字段:DB_TYPE
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * 数据库类型-数据库字段:DB_TYPE
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * 任务类型-数据库字段:TASK_TYPE
     */
    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * 任务添加方式-数据库字段:ADD_TYPE
     */
    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    /**
     * 任务是否有返回结果-数据库字段:HAS_RESULT
     */
    public Integer getHasResult() {
        return hasResult;
    }

    public void setHasResult(Integer hasResult) {
        this.hasResult = hasResult;
    }

    /**
     * 运行次数-数据库字段:RUN_NUM
     */
    public Integer getRunNum() {
        return runNum;
    }

    public void setRunNum(Integer runNum) {
        this.runNum = runNum;
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

    /**
     * 任务监控-数据库字段:IS_MONITOR
     */
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
