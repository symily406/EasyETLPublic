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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "LOG_ID", type = IdType.ASSIGN_ID)
    private String logId;


    @TableField(value = "TASK_SCHEDULE_ID")
    private String taskScheduleId;

    @TableField(value = "EXECUTE_TIME")
    private Date executeTime;

    @TableField(value = "EXECUTION_STATUS")
    private Integer executionStatus;


    @TableField(value = "USE_TIME")
    private long useTime;

    @TableField(value = "CONTENT")
    private String content;

    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public TaskScheduleLog() {
        super();
    }


    public String getLogId() {
        return logId;
    }


    public void setLogId(String logId) {
        this.logId = logId;
    }



    public String getTaskScheduleId() {
        return taskScheduleId;
    }


    public void setTaskScheduleId(String taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
    }


    public Date getExecuteTime() {
        return executeTime;
    }


    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }



    public String getContent() {
        return content;
    }


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
