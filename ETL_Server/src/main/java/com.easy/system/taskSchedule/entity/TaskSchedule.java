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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "TASK_SCHEDULE_ID", type = IdType.ASSIGN_ID)
    private String taskScheduleId;

    /**
     * 任务名称
     */
    @TableField(value = "NAME")
    @NotEmpty(message = "name任务名称不能为空")
    @Length(max = 64, message = "name任务名称不能超过64个字符")
    private String name;
    /**
     * 执行周期配置
     */
    @TableField(value = "CORN")
    @NotEmpty(message = "corn执行周期配置不能为空")
    @Length(max = 32, message = "corn执行周期配置不能超过32个字符")
    private String corn;
    /**
     * 任务类的全路径
     */
    @TableField(value = "CLASS_PATH")
    @NotEmpty(message = "classPath任务类的全路径不能为空")
    @Length(max = 128, message = "classPath任务类的全路径不能超过128个字符")
    private String classPath;
    /**
     * 参数
     */
    @TableField(value = "PARAMETER")
    @Length(max = 128, message = "parameter参数不能超过128个字符")
    private String parameter;
    /**
     * 是否随系统启动立即执行
     */
    @TableField(value = "BOOT_UP")
    private Integer bootUp;
    /**
     * 任务状态
     */
    @TableField(value = "STATUS")
    private Integer status;

    /**
     * 执行状态
     */
    @TableField(value = "EXECUTION_STATUS")
    private Integer executionStatus;
    /**
     * 备注说明
     */
    @TableField(value = "REMARKS")
    @Length(max = 256, message = "remarks备注说明不能超过256个字符")
    private String remarks;
    /**
     * 任务等级
     */
    @TableField(value = "LEVEL")
    private Integer level;
    /**
     * 执行方式
     */
    @TableField(value = "RUN_MODE")
    private Integer runMode;
    /**
     * 执行节点
     */
    @TableField(value = "RUN_NODE")
    private Integer runNode;
    /**
     * 执行节点IP
     */
    @TableField(value = "RUN_NODE_IP")
    @Length(max = 32, message = "runNodeIp执行节点IP不能超过32个字符")
    private String runNodeIp;
    /**
     * 下次运行时间
     */
    @TableField(value = "RUN_NEXT_TIME")
    private Date runNextTime;



    public TaskSchedule() {
        super();
    }

    /**
     * 主键-数据库字段:TASK_SCHEDULE_ID
     */
    public String getTaskScheduleId() {
        return taskScheduleId;
    }

    /**
     * 主键-数据库字段:TASK_SCHEDULE_ID
     */
    public void setTaskScheduleId(String taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
    }


    /**
     * 任务名称-数据库字段:NAME
     */
    public String getName() {
        return name;
    }

    /**
     * 任务名称-数据库字段:NAME
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 执行周期配置-数据库字段:CORN
     */
    public String getCorn() {
        return corn;
    }

    /**
     * 执行周期配置-数据库字段:CORN
     */
    public void setCorn(String corn) {
        this.corn = corn;
    }

    /**
     * 任务类的全路径-数据库字段:CLASS_PATH
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * 任务类的全路径-数据库字段:CLASS_PATH
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 参数-数据库字段:PARAMETER
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * 参数-数据库字段:PARAMETER
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * 是否随系统启动立即执行-数据库字段:BOOT_UP
     */
    public Integer getBootUp() {
        return bootUp;
    }

    public void setBootUp(Integer bootUp) {
        this.bootUp = bootUp;
    }

    /**
     * 执行状态-数据库字段:STATUS
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 备注说明-数据库字段:REMARKS
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注说明-数据库字段:REMARKS
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 任务等级-数据库字段:LEVEL
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 执行方式-数据库字段:RUN_MODE
     */
    public Integer getRunMode() {
        return runMode;
    }

    public void setRunMode(Integer runMode) {
        this.runMode = runMode;
    }

    /**
     * 执行节点-数据库字段:RUN_NODE
     */
    public Integer getRunNode() {
        return runNode;
    }

    public void setRunNode(Integer runNode) {
        this.runNode = runNode;
    }

    /**
     * 执行节点IP-数据库字段:RUN_NODE_IP
     */
    public String getRunNodeIp() {
        return runNodeIp;
    }

    /**
     * 执行节点IP-数据库字段:RUN_NODE_IP
     */
    public void setRunNodeIp(String runNodeIp) {
        this.runNodeIp = runNodeIp;
    }

    /**
     * 下次运行时间-数据库字段:RUN_NEXT_TIME
     */
    public Date getRunNextTime() {
        return runNextTime;
    }

    /**
     * 下次运行时间-数据库字段:RUN_NEXT_TIME
     */
    public void setRunNextTime(Date runNextTime) {
        this.runNextTime = runNextTime;
    }

    public Integer getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
    }

    /**
     * 获取参数
     * @return
     */
    public DataMap getParam() {
        DataMap dataMap = new DataMap();
        if(StringUtils.isEmpty(parameter)){
            return dataMap;
        }
        dataMap.putAll(JsonKit.parseObject(parameter,Map.class));
        return dataMap;
    }
}
