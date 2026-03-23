package com.easy.etl.taskJob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 待运行任务依赖-实体类
 *
 * @author 邵勇
 * @table TF_TASK_JOB_RELY_ON
 * @time 2024-01-17
 */
@TableName(value = "TF_TASK_JOB_RELY_ON", autoResultMap = true)
public class TaskJobRelyOn extends Model<TaskJobRelyOn> implements Serializable {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "LAZY_ID", type = IdType.ASSIGN_ID)
    private String lazyId;


    @TableField(value = "JOB_ID")
    private String jobId;


    @TableField(value = "LAZY_JOB_ID")
    private String lazyJobId;

    @TableField(value = "LAZY_TASK_ID")
    private String lazyTaskId;

    @TableField(value = "LAZY_JOB_CORN")
    private String lazyJobCorn;

    @TableField(value = "TAKS_ID")
    private String taksId;


    @TableField(value = "STATUS")
    private Integer status;


    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    @TableField(value = "LAZY_TASK_ENABLE")
    private Integer lazyTaskEnable;

    @TableField(value = "IS_HANDLE")
    private Integer isHandle;


    public TaskJobRelyOn() {
        super();
    }


    public String getJobId() {
        return jobId;
    }


    public void setJobId(String jobId) {
        this.jobId = jobId;
    }



    public String getLazyJobId() {
        return lazyJobId;
    }


    public void setLazyJobId(String lazyJobId) {
        this.lazyJobId = lazyJobId;
    }


    public String getLazyTaskId() {
        return lazyTaskId;
    }


    public void setLazyTaskId(String lazyTaskId) {
        this.lazyTaskId = lazyTaskId;
    }


    public String getLazyJobCorn() {
        return lazyJobCorn;
    }


    public void setLazyJobCorn(String lazyJobCorn) {
        this.lazyJobCorn = lazyJobCorn;
    }


    public String getTaksId() {
        return taksId;
    }


    public void setTaksId(String taksId) {
        this.taksId = taksId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    public Integer getLazyTaskEnable() {
        return lazyTaskEnable;
    }

    public void setLazyTaskEnable(Integer lazyTaskEnable) {
        this.lazyTaskEnable = lazyTaskEnable;
    }

    public Integer getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Integer isHandle) {
        this.isHandle = isHandle;
    }

    public String getLazyId() {
        return lazyId;
    }

    public void setLazyId(String lazyId) {
        this.lazyId = lazyId;
    }
}
