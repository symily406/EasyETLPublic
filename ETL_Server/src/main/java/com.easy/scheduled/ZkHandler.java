package com.easy.scheduled;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Config;
import com.easy.etl.core.init.Zk;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * zookeeper 节点上报
 */
public class ZkHandler extends AbstractBaseCronTask {
    public ZkHandler(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, taskScheduleLogMapper);
    }

    @Override
    public void beforeJob(TaskSchedule taskSchedule) {

    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        //上报master节点
        Zk.initMaster();
        //设置是否master节点
        Config.isMaster=Zk.isMaster();
        //设置主master的IP
        Config.masterIp=Zk.getMaster();
        //设置定时任务是否master节点
        Config.scheduledMaster=Zk.isMaster();
    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
