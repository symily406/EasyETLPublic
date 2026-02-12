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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "TASK_ID", type = IdType.ASSIGN_ID)
    private String taskId;

    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 工作空间分层
     */
    @TableField(value = "PROJECT_WORK_SPACE_LAYER_REL_ID")
    @NotEmpty(message = "projectWorkSpaceLayerRelId工作空间分层不能为空")
    @Length(max = 32, message = "projectWorkSpaceLayerRelId工作空间分层不能超过32个字符")
    private String projectWorkSpaceLayerRelId;
    /**
     * 项目空间ID
     */
    @TableField(value = "PROJECT_WORK_SPACE_ID")
    @Length(max = 32, message = "projectWorkSpaceId项目空间ID不能超过32个字符")
    private String projectWorkSpaceId;
    /**
     * 工作空间任务类型
     */
    @TableField(value = "WORK_SPACE_TYPE")
    @Length(max = 8, message = "workSpaceType工作空间任务类型不能超过8个字符")
    private String workSpaceType;
    /**
     * 目录ID
     */
    @TableField(value = "TASK_FOLDER_ID")
    @NotEmpty(message = "taskFolderId目录ID不能为空")
    @Length(max = 32, message = "taskFolderId目录ID不能超过32个字符")
    private String taskFolderId;
    /**
     * 调度状态
     */
    @TableField(value = "CORN_STATUS")
    private Integer cornStatus;
    /**
     * 调度开始时间
     */
    @TableField(value = "START_DATE")
    @Length(max = 16, message = "startDate调度开始时间不能超过16个字符")
    private String startDate;
    /**
     * 调度结束时间
     */
    @TableField(value = "END_DATE")
    @Length(max = 16, message = "endDate调度结束时间不能超过16个字符")
    private String endDate;
    /**
     * 调度时间
     */
    @TableField(value = "CORN")
    @Length(max = 64, message = "corn调度时间不能超过64个字符")
    private String corn;
    /**
     * 失败策略
     */
    @TableField(value = "FAILURE_POLICY")
    private Integer failurePolicy;
    /**
     * 调度方式
     */
    @TableField(value = "SCHEDULE_TYPE")
    @Length(max = 8, message = "scheduleType调度方式不能超过8个字符")
    private String scheduleType;
    /**
     * 小时
     */
    @TableField(value = "SCHEDUE_HOUR")
    @Length(max = 8, message = "schedueHour小时不能超过8个字符")
    private String schedueHour;
    /**
     * 分钟
     */
    @TableField(value = "SCHEDUE_MINUTE")
    @Length(max = 8, message = "schedueMinute分钟不能超过8个字符")
    private String schedueMinute;
    /**
     * 周
     */
    @TableField(value = "SCHEDUE_WEEK")
    @Length(max = 8, message = "schedueWeek周不能超过8个字符")
    private String schedueWeek;
    /**
     * 天
     */
    @TableField(value = "SCHEDUE_DAY")
    @Length(max = 8, message = "schedueDay天不能超过8个字符")
    private String schedueDay;
    /**
     * 月
     */
    @TableField(value = "SCHEDUE_MONTH")
    @Length(max = 8, message = "schedueMonth月不能超过8个字符")
    private String schedueMonth;
    /**
     * 时间间隔
     */
    @TableField(value = "TIME_INTERVAL")
    @Length(max = 8, message = "timeInterval时间间隔不能超过8个字符")
    private String timeInterval;


    public ProjectTaskSchedue() {
        super();
    }

    /**
     * 主键-数据库字段:TASK_ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 主键-数据库字段:TASK_ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public String getProjectWorkSpaceLayerRelId() {
        return projectWorkSpaceLayerRelId;
    }

    /**
     * 工作空间分层-数据库字段:PROJECT_WORK_SPACE_LAYER_REL_ID
     */
    public void setProjectWorkSpaceLayerRelId(String projectWorkSpaceLayerRelId) {
        this.projectWorkSpaceLayerRelId = projectWorkSpaceLayerRelId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public String getProjectWorkSpaceId() {
        return projectWorkSpaceId;
    }

    /**
     * 项目空间ID-数据库字段:PROJECT_WORK_SPACE_ID
     */
    public void setProjectWorkSpaceId(String projectWorkSpaceId) {
        this.projectWorkSpaceId = projectWorkSpaceId;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public String getWorkSpaceType() {
        return workSpaceType;
    }

    /**
     * 工作空间任务类型-数据库字段:WORK_SPACE_TYPE
     */
    public void setWorkSpaceType(String workSpaceType) {
        this.workSpaceType = workSpaceType;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public String getTaskFolderId() {
        return taskFolderId;
    }

    /**
     * 目录ID-数据库字段:TASK_FOLDER_ID
     */
    public void setTaskFolderId(String taskFolderId) {
        this.taskFolderId = taskFolderId;
    }

    /**
     * 调度状态-数据库字段:CORN_STATUS
     */
    public Integer getCornStatus() {
        return cornStatus;
    }

    public void setCornStatus(Integer cornStatus) {
        this.cornStatus = cornStatus;
    }

    /**
     * 调度开始时间-数据库字段:START_DATE
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 调度开始时间-数据库字段:START_DATE
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 调度结束时间-数据库字段:END_DATE
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 调度结束时间-数据库字段:END_DATE
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 调度时间-数据库字段:CORN
     */
    public String getCorn() {
        return corn;
    }

    /**
     * 调度时间-数据库字段:CORN
     */
    public void setCorn(String corn) {
        this.corn = corn;
    }

    /**
     * 失败策略-数据库字段:FAILURE_POLICY
     */
    public Integer getFailurePolicy() {
        return failurePolicy;
    }

    public void setFailurePolicy(Integer failurePolicy) {
        this.failurePolicy = failurePolicy;
    }

    /**
     * 调度方式-数据库字段:SCHEDULE_TYPE
     */
    public String getScheduleType() {
        return scheduleType;
    }

    /**
     * 调度方式-数据库字段:SCHEDULE_TYPE
     */
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * 小时-数据库字段:SCHEDUE_HOUR
     */
    public String getSchedueHour() {
        return schedueHour;
    }

    /**
     * 小时-数据库字段:SCHEDUE_HOUR
     */
    public void setSchedueHour(String schedueHour) {
        this.schedueHour = schedueHour;
    }

    /**
     * 分钟-数据库字段:SCHEDUE_MINUTE
     */
    public String getSchedueMinute() {
        return schedueMinute;
    }

    /**
     * 分钟-数据库字段:SCHEDUE_MINUTE
     */
    public void setSchedueMinute(String schedueMinute) {
        this.schedueMinute = schedueMinute;
    }

    /**
     * 周-数据库字段:SCHEDUE_WEEK
     */
    public String getSchedueWeek() {
        return schedueWeek;
    }

    /**
     * 周-数据库字段:SCHEDUE_WEEK
     */
    public void setSchedueWeek(String schedueWeek) {
        this.schedueWeek = schedueWeek;
    }

    /**
     * 天-数据库字段:SCHEDUE_DAY
     */
    public String getSchedueDay() {
        return schedueDay;
    }

    /**
     * 天-数据库字段:SCHEDUE_DAY
     */
    public void setSchedueDay(String schedueDay) {
        this.schedueDay = schedueDay;
    }

    /**
     * 月-数据库字段:SCHEDUE_MONTH
     */
    public String getSchedueMonth() {
        return schedueMonth;
    }

    /**
     * 月-数据库字段:SCHEDUE_MONTH
     */
    public void setSchedueMonth(String schedueMonth) {
        this.schedueMonth = schedueMonth;
    }

    /**
     * 时间间隔-数据库字段:TIME_INTERVAL
     */
    public String getTimeInterval() {
        return timeInterval;
    }

    /**
     * 时间间隔-数据库字段:TIME_INTERVAL
     */
    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }


}
