package com.easy.etl.flowRunJob.entity;

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
 * 工作流运行JOB-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_RUN_JOB
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_RUN_JOB", autoResultMap = true)
public class FlowRunJob extends Model<FlowRunJob> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;




    /**
     * 主键
     */
    @TableId(value = "FLOW_RUN_JOB_ID", type = IdType.ASSIGN_ID)
    @TableField(value = "FLOW_RUN_JOB_ID")
    @Length(max = 32, message = "flowRunJobId主键不能超过32个字符")
    private String flowRunJobId;

    @TableField(value = "FLOW_JOB_ID")
    private String flowJobId;
    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private Integer status;
    /**
     * 执行时间
     */
    @TableField(value = "EXECUTE_TIME")
    private Date executeTime;


    @TableField(value = "PLANE_TIME")
    private Date planeTime;

    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;


    @TableField(value = "EXECUTE_CYCLE")
    private Integer executeCycle;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "COMPLETED_TIME")
    private Date completedTime;

    @TableField(value = "FAILED_TASK_NUM")
    private Integer failedTaskNum;

    @TableField(value = "USAGE_TIME")
    private Integer usageTime;

    @TableField(value = "ADD_TYPE")
    private Integer addType;



    public FlowRunJob() {
        super();
    }

    /**
     * 归属工作流JOB-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 归属工作流JOB-数据库字段:FLOW_JOB_ID
     */
    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }


    /**
     * 主键-数据库字段:FLOW_RUN_JOB_ID
     */
    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    /**
     * 主键-数据库字段:FLOW_RUN_JOB_ID
     */
    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 状态-数据库字段:STATUS
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 执行时间-数据库字段:EXECUTE_TIME
     */
    public Date getExecuteTime() {
        return executeTime;
    }

    /**
     * 执行时间-数据库字段:EXECUTE_TIME
     */
    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getPlaneTime() {
        return planeTime;
    }

    public void setPlaneTime(Date planeTime) {
        this.planeTime = planeTime;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Integer getExecuteCycle() {
        return executeCycle;
    }

    public void setExecuteCycle(Integer executeCycle) {
        this.executeCycle = executeCycle;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public Integer getFailedTaskNum() {
        return failedTaskNum;
    }

    public void setFailedTaskNum(Integer failedTaskNum) {
        this.failedTaskNum = failedTaskNum;
    }

    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    public Integer getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Integer usageTime) {
        this.usageTime = usageTime;
    }

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }
}
