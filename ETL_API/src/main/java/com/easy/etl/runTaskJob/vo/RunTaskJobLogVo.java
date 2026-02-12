package com.easy.etl.runTaskJob.vo;


import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
*  任务运行日志-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunTaskJobLogVo extends RunTaskJobLog {
    private String taskType;
    private int status;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

}
