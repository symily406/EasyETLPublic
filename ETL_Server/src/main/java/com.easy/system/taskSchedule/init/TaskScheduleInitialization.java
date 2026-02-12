package com.easy.system.taskSchedule.init;

import com.easy.etl.core.init.Config;
import com.easy.system.taskSchedule.service.ITaskScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Configuration
@Order(value = 2)
public class TaskScheduleInitialization implements ApplicationRunner {
    private static Logger log= LoggerFactory.getLogger(TaskScheduleInitialization.class);
    @Resource
    private ITaskScheduleService taskScheduleService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(Config.scheduledCluster){
            //集群部署时起动系统级定时任务
            taskScheduleService.systemTaskScheduleStart();
            return;
        }
        //单机部署时起动定时任务
        taskScheduleService.bootUpTaskSchedule();
    }
}
