package com.easy.system.taskSchedule.entity;

import java.util.Date;
import java.util.Map;
import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import com.spring.boot.ext.kit.DataMap;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 任务调度-实体类
 *
 * @author 邵勇
 * @table TD_TASK_SCHEDULE
 * @time 2024-01-17
 */
@TableName(value = "TD_TASK_SCHEDULE", autoResultMap = true)
public class TaskSchedule extends BaseEntity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "TASK_SCHEDULE_ID", type = IdType.ASSIGN_ID)
    private String taskScheduleId;


    @TableField(value = "NAME")
    private String name;

    @TableField(value = "CORN")
    private String corn;

    @TableField(value = "CLASS_PATH")
    private String classPath;

    @TableField(value = "PARAMETER")
    private String parameter;

    @TableField(value = "BOOT_UP")
    private Integer bootUp;

    @TableField(value = "STATUS")
    private Integer status;


    @TableField(value = "EXECUTION_STATUS")
    private Integer executionStatus;

    @TableField(value = "REMARKS")
    private String remarks;

    @TableField(value = "LEVEL")
    private Integer level;

    @TableField(value = "RUN_MODE")
    private Integer runMode;

    @TableField(value = "RUN_NODE")
    private Integer runNode;

    @TableField(value = "RUN_NODE_IP")
    private String runNodeIp;

    @TableField(value = "RUN_NEXT_TIME")
    private Date runNextTime;



    public TaskSchedule() {
        super();
    }


    public String getTaskScheduleId() {
        return taskScheduleId;
    }


    public void setTaskScheduleId(String taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
    }



    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCorn() {
        return corn;
    }


    public void setCorn(String corn) {
        this.corn = corn;
    }


    public String getClassPath() {
        return classPath;
    }


    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }


    public String getParameter() {
        return parameter;
    }


    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public Integer getBootUp() {
        return bootUp;
    }

    public void setBootUp(Integer bootUp) {
        this.bootUp = bootUp;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }


    public Integer getRunMode() {
        return runMode;
    }

    public void setRunMode(Integer runMode) {
        this.runMode = runMode;
    }


    public Integer getRunNode() {
        return runNode;
    }

    public void setRunNode(Integer runNode) {
        this.runNode = runNode;
    }


    public String getRunNodeIp() {
        return runNodeIp;
    }


    public void setRunNodeIp(String runNodeIp) {
        this.runNodeIp = runNodeIp;
    }


    public Date getRunNextTime() {
        return runNextTime;
    }


    public void setRunNextTime(Date runNextTime) {
        this.runNextTime = runNextTime;
    }

    public Integer getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
    }


    public DataMap getParam() {
        DataMap dataMap = new DataMap();
        if(StringUtils.isEmpty(parameter)){
            return dataMap;
        }
        dataMap.putAll(JsonKit.parseObject(parameter,Map.class));
        return dataMap;
    }
}
