package com.easy.scheduled;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Monitor;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.spring.boot.ext.plugin.context.Application;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

public class MonitorHandler extends AbstractBaseCronTask {
    private StringRedisTemplate stringRedisTemplate;

    public MonitorHandler(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, taskScheduleLogMapper);
    }

    @Override
    public void beforeJob(TaskSchedule taskSchedule) {
        if (ObjectUtils.isEmpty(stringRedisTemplate)) {
            stringRedisTemplate = (StringRedisTemplate) Application.getBean("stringRedisTemplate");
        }
    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        Monitor.monitor(stringRedisTemplate);
    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
