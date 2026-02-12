package com.easy.etl.runTaskJob.vo;


import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
*  任务运行-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunTaskJobVo extends RunTaskJob {
    /**
     * 用时
     */
    private long timer;
    /**
     * 执行结果
     */
    private String result;

    private String message;

    private String workSpaceTypeName;

    private String statusName;

    private String addTypeName;

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWorkSpaceTypeName() {
        return workSpaceTypeName;
    }

    public void setWorkSpaceTypeName(String workSpaceTypeName) {
        this.workSpaceTypeName = workSpaceTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getAddTypeName() {
        return addTypeName;
    }

    public void setAddTypeName(String addTypeName) {
        this.addTypeName = addTypeName;
    }
}
