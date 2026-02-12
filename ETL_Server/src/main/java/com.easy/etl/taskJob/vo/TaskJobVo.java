package com.easy.etl.taskJob.vo;


import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.easy.etl.em.CornStatusEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.taskJob.entity.TaskJob;
import org.springframework.util.ObjectUtils;

/**
*  待运行任务-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class TaskJobVo extends TaskJob {

    private String projectId;

    private String projectWorkSpaceLayerRelId;

    private String projectWorkSpaceId;

    private String cornStatusDesc;

    public String getCornStatusDesc() {
        return CornStatusEnum.getCornStatusEnum(getCornStatus()).getDesc();
    }

    public void setCornStatusDesc(String cornStatusDesc) {
        this.cornStatusDesc = cornStatusDesc;
    }

    private String workSpaceTypeDesc;

    public String getWorkSpaceTypeDesc() {
        return workSpaceTypeDesc;
    }

    public void setWorkSpaceTypeDesc(String workSpaceTypeDesc) {
        this.workSpaceTypeDesc = workSpaceTypeDesc;
    }

    private String statusDesc;

    public String getStatusDesc() {
        return TaskStatusEnum.getTaskStatusEnum(getStatus()).getDesc();
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String taskName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    private Long disableNum;

    public Long getDisableNum() {
        return disableNum;
    }

    public void setDisableNum(Long disableNum) {
        this.disableNum = disableNum;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    @Override
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    @Override
    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    @Override
    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }
}
