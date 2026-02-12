package com.easy.system.taskSchedule.em;

/**
 * 任务级别枚举
 */
public enum TaskScheduleLevalEnum {
    SYSTEM(99999, "系统"),
    INTERNAL(9999, "内置"),
    NORMAL(1, "用户自定议");

    TaskScheduleLevalEnum(int leval, String desc) {
        this.leval = leval;
        this.desc = desc;
    }
    private int leval;
    private String desc;

    public static TaskScheduleLevalEnum getEnum(int leval)
    {
        for(TaskScheduleLevalEnum statusEnum : TaskScheduleLevalEnum.values())
        {
            if(statusEnum.getLeval() == leval)
            {
                return statusEnum;
            }
        }
        return null;
    }

    public int getLeval() {
        return leval;
    }

    public void setLeval(int leval) {
        this.leval = leval;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
