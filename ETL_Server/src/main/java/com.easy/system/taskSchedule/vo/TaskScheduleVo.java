package com.easy.system.taskSchedule.vo;


import com.easy.system.taskSchedule.entity.TaskSchedule;

/**
*  任务调度-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class TaskScheduleVo extends TaskSchedule {
    private String bootUpDesc;
    private String executionStatusDesc;
    private String levalDesc;
    private String statusDesc;

    public String getBootUpDesc() {
        return bootUpDesc;
    }

    public void setBootUpDesc(String bootUpDesc) {
        this.bootUpDesc = bootUpDesc;
    }

    public String getExecutionStatusDesc() {
        return executionStatusDesc;
    }

    public void setExecutionStatusDesc(String executionStatusDesc) {
        this.executionStatusDesc = executionStatusDesc;
    }

    public String getLevalDesc() {
        return levalDesc;
    }

    public void setLevalDesc(String levalDesc) {
        this.levalDesc = levalDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
