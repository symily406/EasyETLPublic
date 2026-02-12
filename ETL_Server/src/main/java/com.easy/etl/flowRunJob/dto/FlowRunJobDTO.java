package com.easy.etl.flowRunJob.dto;


import com.easy.etl.flowRunJob.entity.FlowRunJob;

/**
*  工作流运行JOB-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class FlowRunJobDTO extends FlowRunJob {
    private String orderColumn;

    private String orderType;
    private String orderBy;
    private String planeDate;
    private Integer userLeval;

    private String projectMember;

    private String userId;
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

    public String getPlaneDate() {
        return planeDate;
    }

    public void setPlaneDate(String planeDate) {
        this.planeDate = planeDate;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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
}
