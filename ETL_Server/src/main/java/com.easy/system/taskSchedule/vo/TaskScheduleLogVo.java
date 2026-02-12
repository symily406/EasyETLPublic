package com.easy.system.taskSchedule.vo;


import com.easy.system.taskSchedule.entity.TaskScheduleLog;

/**
*  任务调度日志-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class TaskScheduleLogVo extends TaskScheduleLog {
    private String executionStatusDesc;

    public String getExecutionStatusDesc() {
        return executionStatusDesc;
    }

    public void setExecutionStatusDesc(String executionStatusDesc) {
        this.executionStatusDesc = executionStatusDesc;
    }
}
