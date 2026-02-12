package com.easy.scheduled;

import com.easy.etl.core.init.Config;
import com.easy.etl.flow.service.IFlowService;
import com.easy.etl.flow.service.impl.FlowServiceImpl;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.spring.boot.ext.plugin.context.Application;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FlowJobHandler extends AbstractBaseCronTask {

    private IFlowService flowService;

    public FlowJobHandler(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, null);
    }


    @Override
    public void beforeJob(TaskSchedule taskSchedule) {

    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        if (ObjectUtils.isEmpty(flowService)){
            flowService= Application.getBean(FlowServiceImpl.class);
        }
        if (Config.isMaster) {
            flowService.run(taskSchedule);
        }
    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
