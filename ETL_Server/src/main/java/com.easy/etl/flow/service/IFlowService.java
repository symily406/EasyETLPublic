package com.easy.etl.flow.service;

import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.system.taskSchedule.entity.TaskSchedule;

import java.util.List;

public interface IFlowService {

    /**
     * 停止工作流任务
     *
     */
    List<String> stopTaskFlow(List<String> flowRunJoBIds);


    void run(TaskSchedule taskSchedule);

    /**
     * 添加工作流任务到工作流任务列表
     *
     * @return
     */
    void flowJobAddTask();

    /**
     * 添加工作流任务到运行任务表
     *
     * @return
     */
    void flowJobAddRunJob();

    /**
     * 更新工作流任务任务状态
     *
     * @param status
     * @return
     */
    void updateFlowTaskStatus(String flowRunJobId, String flowJobId, String executeNodeId, String jobRunNodeId, int status);

    /**
     * 处理工作流任务脚本
     *
     * @return
     */
    void dealWithFlowTaskStatus();
}
