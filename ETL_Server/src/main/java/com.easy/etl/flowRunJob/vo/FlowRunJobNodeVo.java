package com.easy.etl.flowRunJob.vo;


import com.easy.etl.em.NodeTypeEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * 工作流运行Node-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class FlowRunJobNodeVo extends FlowRunJobNode {
    private String projectId;

    private String projectName;

    private String taskFolderId;

    private String projectWorkSpaceLayerRelId;

    private String projectWorkSpaceId;

    private String taskName;

    private String desc;

    private String workSpaceTypeDesc;


    private String statusDesc;

    private Integer cornStatus;

    private String jobId;


    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskFolderId() {
        return taskFolderId;
    }

    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDesc() {
        String taskName = getTaskName();
        if (StringUtils.isEmpty(taskName)) {
            taskName = NodeTypeEnum.getNodeTypeEnum(getNodeType()).getDesc();
        }
        String message = taskName + "[" + TaskStatusEnum.getTaskStatusEnum(getStatus()).getDesc() + "]";
        message += ",计划执行时间:" + DateFormatUtils.format(getPlaneTime(), "yyyy-MM-dd HH:mm:ss");
        if (!ObjectUtils.isEmpty(getExecuteTime())) {
            message += ",开始执行时间:" + DateFormatUtils.format(getExecuteTime(), "yyyy-MM-dd HH:mm:ss");
        }
        if (!ObjectUtils.isEmpty(getCompletedTime())) {
            message += ",结束执行时间" + DateFormatUtils.format(getCompletedTime(), "yyyy-MM-dd HH:mm:ss") + ",耗时:" + getUsageTime() + "毫秒";
        }
        return message;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWorkSpaceTypeDesc() {
        return workSpaceTypeDesc;
    }

    public void setWorkSpaceTypeDesc(String workSpaceTypeDesc) {
        this.workSpaceTypeDesc = workSpaceTypeDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


}
