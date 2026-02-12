package com.easy.etl.projectTaskFlow.vo;


import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlow;

/**
*  项目工作流-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class ProjectTaskFlowVo extends ProjectTaskFlow {
    private  Integer cornStatus;
    private String startDate;
    private String endDate;
    private String corn;




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

    public Integer getCornStatus() {
        return cornStatus;
    }
}
