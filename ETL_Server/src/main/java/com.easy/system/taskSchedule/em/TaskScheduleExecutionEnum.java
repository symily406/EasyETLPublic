package com.easy.system.taskSchedule.em;

/**
 * 任务运行状态枚举
 */
public enum TaskScheduleExecutionEnum {
    EXECUTION_FAILED(-1,"执行失败"),
    TO_BE_EXECUTED(0, "待执行"),
    BEING_EXECUTED(1, "正在执行"),
    COMPLETE(2, "执行成功");

    TaskScheduleExecutionEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    private int status;
    private String desc;

    public static TaskScheduleExecutionEnum getEnum(int status)
    {
        for(TaskScheduleExecutionEnum statusEnum : TaskScheduleExecutionEnum.values())
        {
            if(statusEnum.getStatus() == status)
            {
                return statusEnum;
            }
        }
        return null;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
