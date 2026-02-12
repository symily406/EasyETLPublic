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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "LAZY_ID", type = IdType.ASSIGN_ID)
    private String lazyId;


    @TableField(value = "JOB_ID")
    private String jobId;

    /**
     * 依赖JOB_ID
     */
    @TableField(value = "LAZY_JOB_ID")
    @Length(max = 32, message = "lazyJobId依赖JOB_ID不能超过32个字符")
    private String lazyJobId;
    /**
     * 依赖任务ID
     */
    @TableField(value = "LAZY_TASK_ID")
    @Length(max = 32, message = "lazyTaskId依赖任务ID不能超过32个字符")
    private String lazyTaskId;
    /**
     * 依赖任务运行时间
     */
    @TableField(value = "LAZY_JOB_CORN")
    @Length(max = 32, message = "lazyJobCorn依赖任务运行时间不能超过32个字符")
    private String lazyJobCorn;
    /**
     * 任务ID
     */
    @TableField(value = "TAKS_ID")
    @Length(max = 32, message = "taksId任务ID不能超过32个字符")
    private String taksId;

    /**
     * 任务状态
     */
    @TableField(value = "STATUS")
    private Integer status;

    /**
     * 失败策略
     */
    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    @TableField(value = "LAZY_TASK_ENABLE")
    private Integer lazyTaskEnable;

    @TableField(value = "IS_HANDLE")
    private Integer isHandle;


    public TaskJobRelyOn() {
        super();
    }

    /**
     * 主键-数据库字段:JOB_ID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 主键-数据库字段:JOB_ID
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


    /**
     * 依赖JOB_ID-数据库字段:LAZY_JOB_ID
     */
    public String getLazyJobId() {
        return lazyJobId;
    }

    /**
     * 依赖JOB_ID-数据库字段:LAZY_JOB_ID
     */
    public void setLazyJobId(String lazyJobId) {
        this.lazyJobId = lazyJobId;
    }

    /**
     * 依赖任务ID-数据库字段:LAZY_TASK_ID
     */
    public String getLazyTaskId() {
        return lazyTaskId;
    }

    /**
     * 依赖任务ID-数据库字段:LAZY_TASK_ID
     */
    public void setLazyTaskId(String lazyTaskId) {
        this.lazyTaskId = lazyTaskId;
    }

    /**
     * 依赖任务运行时间-数据库字段:LAZY_JOB_CORN
     */
    public String getLazyJobCorn() {
        return lazyJobCorn;
    }

    /**
     * 依赖任务运行时间-数据库字段:LAZY_JOB_CORN
     */
    public void setLazyJobCorn(String lazyJobCorn) {
        this.lazyJobCorn = lazyJobCorn;
    }

    /**
     * 任务ID-数据库字段:TAKS_ID
     */
    public String getTaksId() {
        return taksId;
    }

    /**
     * 任务ID-数据库字段:TAKS_ID
     */
    public void setTaksId(String taksId) {
        this.taksId = taksId;
    }

    /**
     * 任务状态-数据库字段:STATUS
     */
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
