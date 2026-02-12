package com.easy.system.taskSchedule.em;

/**
 * 任务运行状态枚举
 */
public enum TaskScheduleStatusEnum {
    TO_BE_EXECUTED(0, "待执行"),
    BEING_EXECUTED(1, "正在执行"),
    STOPPING(2, "停止中"),
    STOP_EXECUTED(-1, "停止执行");

    TaskScheduleStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    private int status;
    private String desc;

    public static TaskScheduleStatusEnum getEnum(int status)
    {
        for(TaskScheduleStatusEnum statusEnum : TaskScheduleStatusEnum.values())
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
