package com.easy.etl.flowRunJob.vo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.easy.etl.em.CornStatusEnum;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 工作流运行JOB-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowRunJobTreeVo implements Serializable {


    private String id;

    private String jobRunNodeId;

    /**
     * 主键
     */
    private String flowRunJobId;

    private String flowJobId;

    /**
     * 归属任务
     */
    private String taskId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 执行时间
     */
    private Date executeTime;


    private Date planeTime;

    private Integer failurePolicy;

    private String workSpaceType;

    private String workSpaceTypeDesc;

    /**
     * 执行任务ID
     */
    private String executeTaskId;


    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "ADD_TIME", fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    private Integer executeCycle;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "COMPLETED_TIME")
    private Date completedTime;

    private Integer failedTaskNum;

    private Integer usageTime;

    private Integer addType;


    private String statusName;

    private String addTypeName;

    private String taskName;

    /**
     * 是否子节点
     */
    private Boolean isLeaf;

    private Integer nodeType;

    private String projectId;

    private String projectName;

    private String projectWorkSpaceLayerRelId;

    private String projectWorkSpaceId;

    private Integer cornStatus;


    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }

    private String cornStatusDesc;

    public String getCornStatusDesc() {
        return CornStatusEnum.getCornStatusEnum(getCornStatus()).getDesc();
    }

    public void setCornStatusDesc(String cornStatusDesc) {
        this.cornStatusDesc = cornStatusDesc;
    }

    private List<FlowRunJobTreeVo> children;


    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getPlaneTime() {
        return planeTime;
    }

    public void setPlaneTime(Date planeTime) {
        this.planeTime = planeTime;
    }

    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public Integer getExecuteCycle() {
        return executeCycle;
    }

    public void setExecuteCycle(Integer executeCycle) {
        this.executeCycle = executeCycle;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public Integer getFailedTaskNum() {
        return failedTaskNum;
    }

    public void setFailedTaskNum(Integer failedTaskNum) {
        this.failedTaskNum = failedTaskNum;
    }

    public Integer getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Integer usageTime) {
        this.usageTime = usageTime;
    }

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<FlowRunJobTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<FlowRunJobTreeVo> children) {
        this.children = children;
    }

    public String getJobRunNodeId() {
        return jobRunNodeId;
    }

    public void setJobRunNodeId(String jobRunNodeId) {
        this.jobRunNodeId = jobRunNodeId;
    }

    public String getWorkSpaceType() {
        return workSpaceType;
    }

    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    public String getWorkSpaceTypeDesc() {
        return workSpaceTypeDesc;
    }

    public void setWorkSpaceTypeDesc(String workSpaceTypeDesc) {
        this.workSpaceTypeDesc = workSpaceTypeDesc;
    }

    public String getExecuteTaskId() {
        return executeTaskId;
    }

    public void setExecuteTaskId(String executeTaskId) {
        this.executeTaskId = executeTaskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
