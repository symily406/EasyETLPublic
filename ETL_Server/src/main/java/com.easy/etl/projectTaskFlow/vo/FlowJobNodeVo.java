package com.easy.etl.projectTaskFlow.vo;


import com.easy.etl.projectTaskFlow.entity.FlowJobNode;

/**
*  工作流Node-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class FlowJobNodeVo extends FlowJobNode {
    private String dbSourceId;

    public String getDbSourceId() {
        return dbSourceId;
    }

    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }
}
