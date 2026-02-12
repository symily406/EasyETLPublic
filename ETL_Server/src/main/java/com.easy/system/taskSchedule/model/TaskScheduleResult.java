package com.easy.system.taskSchedule.model;

import com.easy.system.taskSchedule.em.TaskScheduleExecutionEnum;
import com.easy.system.taskSchedule.em.TaskScheduleStatusEnum;

import java.io.Serializable;

/**
 * 任务执行结果
 */
public class TaskScheduleResult implements Serializable {
    private String taskScheduleId;
    private long useTimer;

    private String message;

    private TaskScheduleExecutionEnum taskScheduleExecutionEnum;

    public String getTaskScheduleId() {
        return taskScheduleId;
    }

    public void setTaskScheduleId(String taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
    }

    public long getUseTimer() {
        return useTimer;
    }

    public void setUseTimer(long useTimer) {
        this.useTimer = useTimer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TaskScheduleExecutionEnum getTaskScheduleExecutionEnum() {
        return taskScheduleExecutionEnum;
    }

    public void setTaskScheduleExecutionEnum(TaskScheduleExecutionEnum taskScheduleExecutionEnum) {
        this.taskScheduleExecutionEnum = taskScheduleExecutionEnum;
    }
}
