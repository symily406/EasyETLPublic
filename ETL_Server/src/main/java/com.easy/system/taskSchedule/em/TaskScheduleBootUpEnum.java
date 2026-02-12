package com.easy.system.taskSchedule.em;

/**
 * 随系统起动枚举
 */
public enum TaskScheduleBootUpEnum {
    START(1, "自动启动"),
    STOP(2, "手动启动");

    TaskScheduleBootUpEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    private int status;
    private String desc;

    public static TaskScheduleBootUpEnum getEnum(int status)
    {
        for(TaskScheduleBootUpEnum statusEnum : TaskScheduleBootUpEnum.values())
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
