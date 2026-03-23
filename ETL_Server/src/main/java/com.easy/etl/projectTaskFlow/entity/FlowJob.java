package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 工作流JOB-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_JOB
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_JOB", autoResultMap = true)
public class FlowJob extends Entity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "FLOW_JOB_ID", type = IdType.ASSIGN_ID)
    private String flowJobId;


    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "FLOW_RUN_JOB_ID")
    private String flowRunJobId;

    @TableField(value = "CORN_STATUS")
    private Integer cornStatus;

    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "START_DATE")
    private String startDate;

    @TableField(value = "END_DATE")
    private String endDate;

    @TableField(value = "CORN")
    private String corn;


    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;


    @TableField(value = "NEXT_EXECUTE_TIME")
    private Date nextExecuteTime;

    @TableField(value = "EXECUTE_CYCLE")
    private Integer executeCycle;


    public FlowJob() {
        super();
    }


    public String getFlowJobId() {
        return flowJobId;
    }


    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }



    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getCorn() {
        return corn;
    }


    public void setCorn(String corn) {
        this.corn = corn;
    }


    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }


    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    public Integer getExecuteCycle() {
        return executeCycle;
    }

    public void setExecuteCycle(Integer executeCycle) {
        this.executeCycle = executeCycle;
    }

    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }

    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }
}
