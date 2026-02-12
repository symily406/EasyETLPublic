package com.easy.scheduled;

import com.easy.etl.core.init.Config;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.easy.etl.runTaskJob.service.impl.RunTaskJobServiceImpl;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.spring.boot.ext.plugin.context.Application;
import org.springframework.util.ObjectUtils;

public class JobHandler extends AbstractBaseCronTask {


    private IRunTaskJobService runTaskJobService;

    public JobHandler(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, null);
    }


    @Override
    public void beforeJob(TaskSchedule taskSchedule) {
        if (ObjectUtils.isEmpty(runTaskJobService)) {
            runTaskJobService = Application.getBean(RunTaskJobServiceImpl.class);
        }
    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        if (Config.isMaster) {
            runTaskJobService.taskPush();
        }
    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
