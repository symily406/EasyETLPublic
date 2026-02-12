package com.easy.etl.projectTask.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目任务依懒-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_LAZY
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_LAZY", autoResultMap = true)
public class ProjectTaskLazy extends Model<ProjectTaskLazy> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "TASK_LAZY_ID", type = IdType.ASSIGN_ID)
    private String taskLazyId;

    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 依懒任务ID
     */
    @TableField(value = "LAZY_TASK_ID")
    @Length(max = 32, message = "lazyTaskId依懒任务ID不能超过32个字符")
    private String lazyTaskId;


    public ProjectTaskLazy() {
        super();
    }

    /**
     * 主键-数据库字段:TASK_LAZY_ID
     */
    public String getTaskLazyId() {
        return taskLazyId;
    }

    /**
     * 主键-数据库字段:TASK_LAZY_ID
     */
    public void setTaskLazyId(String taskLazyId) {
        this.taskLazyId = taskLazyId;
    }

	/**
	 * 添加时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
	private LocalDateTime addTime;


    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 归属任务-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 依懒任务ID-数据库字段:LAZY_TASK_ID
     */
    public String getLazyTaskId() {
        return lazyTaskId;
    }

    /**
     * 依懒任务ID-数据库字段:LAZY_TASK_ID
     */
    public void setLazyTaskId(String lazyTaskId) {
        this.lazyTaskId = lazyTaskId;
    }

	public LocalDateTime getAddTime() {
		return addTime;
	}

	public void setAddTime(LocalDateTime addTime) {
		this.addTime = addTime;
	}
}
