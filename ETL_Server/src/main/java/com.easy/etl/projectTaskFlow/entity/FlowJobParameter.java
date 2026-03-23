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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;


    @TableField(value = "FLOW_JOB_ID")
    private String flowJobId;

    @TableField(value = "TASK_ID")
    private String taskId;

    @TableField(value = "PARAM_KEY")
    private String paramKey;

    @TableField(value = "PARAM_VALUE")
    private String paramValue;

    @TableField(value = "PARAM_TYPE")
    private Integer paramType;


    public FlowJobParameter() {
        super();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }



    public String getFlowJobId() {
        return flowJobId;
    }


    public void setFlowJobId(String flowJobId) {
        this.flowJobId = flowJobId;
    }


    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public String getParamKey() {
        return paramKey;
    }


    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }


    public String getParamValue() {
        return paramValue;
    }


    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }


    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }


}
