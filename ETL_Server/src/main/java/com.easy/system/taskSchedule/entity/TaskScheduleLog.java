package com.easy.system.taskSchedule.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 任务调度日志-实体类
 *
 * @author 邵勇
 * @table TD_TASK_SCHEDULE_LOG
 * @time 2024-01-17
 */
@TableName(value = "TD_TASK_SCHEDULE_LOG", autoResultMap = true)
public class TaskScheduleLog extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
    private String logId;

    /**
     * 所属任务
     */
    @TableField(value = "TASK_SCHEDULE_ID")
    @NotEmpty(message = "taskScheduleId所属任务不能为空")
    @Length(max = 32, message = "taskScheduleId所属任务不能超过32个字符")
    private String taskScheduleId;
    /**
     * 执行时间
     */
    @TableField(value = "EXECUTE_TIME")
    private Date executeTime;
    /**
     * 执行状态
     */
    @TableField(value = "EXECUTION_STATUS")
    private Integer executionStatus;

    /**
     * 用时
     */
    @TableField(value = "USE_TIME")
    private long useTime;
    /**
     * 执行日志
     */
    @TableField(value = "CONTENT")
    @Length(max = 0, message = "content执行日志不能超过0个字符")
    private String content;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public TaskScheduleLog() {
        super();
    }

    /**
     * 主键-数据库字段:LOG_ID
     */
    public String getLogId() {
        return logId;
    }

    /**
     * 主键-数据库字段:LOG_ID
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }


    /**
     * 所属任务-数据库字段:TASK_SCHEDULE_ID
     */
    public String getTaskScheduleId() {
        return taskScheduleId;
    }

    /**
     * 所属任务-数据库字段:TASK_SCHEDULE_ID
     */
    public void setTaskScheduleId(String taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
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


    /**
     * 执行日志-数据库字段:CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * 执行日志-数据库字段:CONTENT
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Integer getIsDel() {
        return isDel;
    }

    @Override
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }
}
