package com.easy.etl.taskJob.dto;


import com.easy.etl.taskJob.entity.TaskJob;

import java.util.Date;
import java.util.List;

/**
 * 待运行任务-数据传输对象实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class TaskJobDTO extends TaskJob {

    private String planeDate;
    private List<String> jobIds;

    private String day;

    private Integer lazyTaskEnable;

    private String orderBy;

    private String orderColumn;

    private String orderType;

    private Integer userLeval;

    private String projectMember;

    private String userId;

    private String startDate;

    private String endDate;


    public List<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<String> jobIds) {
        this.jobIds = jobIds;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getLazyTaskEnable() {
        return lazyTaskEnable;
    }

    public void setLazyTaskEnable(Integer lazyTaskEnable) {
        this.lazyTaskEnable = lazyTaskEnable;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getUserLeval() {
        return userLeval;
    }

    public void setUserLeval(Integer userLeval) {
        this.userLeval = userLeval;
    }

    public String getProjectMember() {
        return projectMember;
    }

    public void setProjectMember(String projectMember) {
        this.projectMember = projectMember;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPlaneDate() {
        return planeDate;
    }

    public void setPlaneDate(String planeDate) {
        this.planeDate = planeDate;
    }
}
