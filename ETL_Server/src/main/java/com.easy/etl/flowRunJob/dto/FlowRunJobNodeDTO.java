package com.easy.etl.flowRunJob.dto;


import com.easy.etl.flowRunJob.entity.FlowRunJobNode;

import java.util.List;

/**
*  工作流运行Node-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class FlowRunJobNodeDTO extends FlowRunJobNode {

    List<Integer> notInNodeTypes;
    List<String> flowRunJobIds;

    public List<String> getFlowRunJobIds() {
        return flowRunJobIds;
    }

    public void setFlowRunJobIds(List<String> flowRunJobIds) {
        this.flowRunJobIds = flowRunJobIds;
    }

    public List<Integer> getNotInNodeTypes() {
        return notInNodeTypes;
    }

    public void setNotInNodeTypes(List<Integer> notInNodeTypes) {
        this.notInNodeTypes = notInNodeTypes;
    }
}
