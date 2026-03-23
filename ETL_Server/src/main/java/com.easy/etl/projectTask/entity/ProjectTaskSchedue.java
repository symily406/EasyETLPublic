package com.easy.etl.projectTask.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 项目任务定时-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_TASK_SCHEDUE
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_TASK_SCHEDUE", autoResultMap = true)
public class ProjectTaskSchedue extends Model<ProjectTaskSchedue> {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "TASK_ID", type = IdType.ASSIGN_ID)
    private String taskId;


    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    private String projectWorkSpaceLayerRelId;

    @TableField(value = "PROJECT_WORK_SPACE_ID")
    private String projectWorkSpaceId;

    @TableField(value = "WORK_SPACE_TYPE")
    private String workSpaceType;

    @TableField(value = "TASK_FOLDER_ID")
    private String taskFolderId;

    @TableField(value = "CORN_STATUS")
    private Integer cornStatus;

    @TableField(value = "START_DATE")
    private String startDate;

    @TableField(value = "END_DATE")
    private String endDate;

    @TableField(value = "CORN")
    private String corn;

    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;

    @TableField(value = "SCHEDULE_TYPE")
    private String scheduleType;

    @TableField(value = "SCHEDUE_HOUR")
    private String schedueHour;

    @TableField(value = "SCHEDUE_MINUTE")
    private String schedueMinute;

    @TableField(value = "SCHEDUE_WEEK")
    private String schedueWeek;

    @TableField(value = "SCHEDUE_DAY")
    private String schedueDay;

    @TableField(value = "SCHEDUE_MONTH")
    private String schedueMonth;

    @TableField(value = "TIME_INTERVAL")
    private String timeInterval;


    public ProjectTaskSchedue() {
        super();
    }


    public String getTaskId() {
        return taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
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


    public String getWorkSpaceType() {
        return workSpaceType;
    }


    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }


    public String getTaskFolderId() {
        return taskFolderId;
    }


    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }


    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getCorn() {
        return corn;
    }


    public void setCorn(String corn) {
        this.corn = corn;
    }


    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }


    public String getScheduleType() {
        return scheduleType;
    }


    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }


    public String getSchedueHour() {
        return schedueHour;
    }


    public void setSchedueHour(String schedueHour) {
        this.schedueHour = schedueHour;
    }


    public String getSchedueMinute() {
        return schedueMinute;
    }


    public void setSchedueMinute(String schedueMinute) {
        this.schedueMinute = schedueMinute;
    }


    public String getSchedueWeek() {
        return schedueWeek;
    }


    public void setSchedueWeek(String schedueWeek) {
        this.schedueWeek = schedueWeek;
    }


    public String getSchedueDay() {
        return schedueDay;
    }


    public void setSchedueDay(String schedueDay) {
        this.schedueDay = schedueDay;
    }


    public String getSchedueMonth() {
        return schedueMonth;
    }


    public void setSchedueMonth(String schedueMonth) {
        this.schedueMonth = schedueMonth;
    }


    public String getTimeInterval() {
        return timeInterval;
    }


    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }


}
