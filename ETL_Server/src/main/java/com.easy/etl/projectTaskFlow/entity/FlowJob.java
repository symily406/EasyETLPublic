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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "FLOW_JOB_ID", type = IdType.ASSIGN_ID)
    private String flowJobId;

    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;

    @TableField(value = "FLOW_RUN_JOB_ID")
    private String flowRunJobId;
    /**
     * 调度状态
     */
    @TableField(value = "CORN_STATUS")
    private Integer cornStatus;
    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private Integer status;
    /**
     * 调度开始时间
     */
    @TableField(value = "START_DATE")
    @Length(max = 16, message = "startDate调度开始时间不能超过16个字符")
    private String startDate;
    /**
     * 调度结束时间
     */
    @TableField(value = "END_DATE")
    @Length(max = 16, message = "endDate调度结束时间不能超过16个字符")
    private String endDate;
    /**
     * 调度时间
     */
    @TableField(value = "CORN")
    @Length(max = 64, message = "corn调度时间不能超过64个字符")
    private String corn;


    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    /**
     * 下次执行时间
     */
    @TableField(value = "NEXT_EXECUTE_TIME")
    private Date nextExecuteTime;

    @TableField(value = "EXECUTE_CYCLE")
    private Integer executeCycle;


    public FlowJob() {
        super();
    }

    /**
     * 主键-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 主键-数据库字段:FLOW_JOB_ID
     */
    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
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
     * 调度状态-数据库字段:CORN_STATUS
     */
    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
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
     * 调度开始时间-数据库字段:START_DATE
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 调度开始时间-数据库字段:START_DATE
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 调度结束时间-数据库字段:END_DATE
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 调度结束时间-数据库字段:END_DATE
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 调度时间-数据库字段:CORN
     */
    public String getCorn() {
        return corn;
    }

    /**
     * 调度时间-数据库字段:CORN
     */
    public void setCorn(String corn) {
        this.corn = corn;
    }

    /**
     * 下次执行时间-数据库字段:NEXT_EXECUTE_TIME
     */
    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    /**
     * 下次执行时间-数据库字段:NEXT_EXECUTE_TIME
     */
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
