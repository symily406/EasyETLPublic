package com.easy.etl.projectTaskFlow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 工作流JOB参数-实体类
 *
 * @author 邵勇
 * @table TF_FLOW_JOB_PARAMETER
 * @time 2024-01-17
 */
@TableName(value = "TF_FLOW_JOB_PARAMETER", autoResultMap = true)
public class FlowJobParameter extends Model<FlowJobParameter> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 归属工作流NODE
     */
    @TableField(value = "FLOW_JOB_ID")
    @NotEmpty(message = "flowJobId归属工作流NODE不能为空")
    @Length(max = 32, message = "flowJobId归属工作流NODE不能超过32个字符")
    private String flowJobId;
    /**
     * 归属任务
     */
    @TableField(value = "TASK_ID")
    @NotEmpty(message = "taskId归属任务不能为空")
    @Length(max = 32, message = "taskId归属任务不能超过32个字符")
    private String taskId;
    /**
     * 参数KEY
     */
    @TableField(value = "PARAM_KEY")
    @Length(max = 32, message = "paramKey参数KEY不能超过32个字符")
    private String paramKey;
    /**
     * 参数VALUE
     */
    @TableField(value = "PARAM_VALUE")
    @Length(max = 32, message = "paramValue参数VALUE不能超过32个字符")
    private String paramValue;
    /**
     * 参数类型
     */
    @TableField(value = "PARAM_TYPE")
    private Integer paramType;


    public FlowJobParameter() {
        super();
    }

    /**
     * ID-数据库字段:ID
     */
    public String getId() {
        return id;
    }

    /**
     * ID-数据库字段:ID
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 归属工作流NODE-数据库字段:FLOW_JOB_ID
     */
    public String getFlowJobId() {
        return flowJobId;
    }

    /**
     * 归属工作流NODE-数据库字段:FLOW_JOB_ID
     */
    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }

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
     * 参数KEY-数据库字段:PARAM_KEY
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 参数KEY-数据库字段:PARAM_KEY
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 参数VALUE-数据库字段:PARAM_VALUE
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 参数VALUE-数据库字段:PARAM_VALUE
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 参数类型-数据库字段:PARAM_TYPE
     */
    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }


}
