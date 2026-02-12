package com.easy.system.taskSchedule.em;

/**
 * 任务运行状态枚举
 */
public enum RunNodeEnum {
    CURRENT_NODE(1,"当前节点"),
    APPOINT_NODE(2, "指定节点");

    RunNodeEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    private int status;
    private String desc;

    public static RunNodeEnum getEnum(int status)
    {
        for(RunNodeEnum statusEnum : RunNodeEnum.values())
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
