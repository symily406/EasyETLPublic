package com.easy.system.taskSchedule.em;

/**
 * 任务运行状态枚举
 */
public enum RunModeEnum {
    SINGLE_SERIAL(1,"单机串行"),
    SINGLE_PARALLEL(2, "单机并行");

    RunModeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    private int status;
    private String desc;

    public static RunModeEnum getEnum(int status)
    {
        for(RunModeEnum statusEnum : RunModeEnum.values())
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
