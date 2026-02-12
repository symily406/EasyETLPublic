package com.easy.system.taskSchedule.scheduled;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.init.Config;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.em.RunNodeEnum;
import com.easy.system.taskSchedule.em.TaskScheduleExecutionEnum;
import com.easy.system.taskSchedule.em.TaskScheduleLevalEnum;
import com.easy.system.taskSchedule.em.TaskScheduleStatusEnum;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.easy.system.taskSchedule.service.ITaskScheduleService;
import com.easy.system.taskSchedule.service.impl.TaskScheduleServiceImpl;
import com.spring.boot.ext.plugin.context.Application;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统级定时任务
 */
public class SystemTask extends AbstractBaseCronTask {
    private ITaskScheduleService taskScheduleService;

    public SystemTask(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, taskScheduleLogMapper);
    }

    @Override
    public void beforeJob(TaskSchedule taskSchedule) {

    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        if(!Config.scheduledMaster){
            return;
        }
        if (ObjectUtils.isEmpty(taskScheduleService)) {
            taskScheduleService = (TaskScheduleServiceImpl) Application.getBean("taskScheduleService");
        }
        //起动其它定时任务
        List<TaskSchedule> tasks = taskScheduleService.list(new LambdaUpdateWrapper<TaskSchedule>()
                .eq(TaskSchedule::getEnable, 1)
                .eq(TaskSchedule::getBootUp, 1)
                .in(TaskSchedule::getStatus, TaskScheduleStatusEnum.TO_BE_EXECUTED.getStatus(), TaskScheduleStatusEnum.BEING_EXECUTED.getStatus(), TaskScheduleStatusEnum.STOPPING.getStatus())
                .in(TaskSchedule::getLevel, TaskScheduleLevalEnum.NORMAL.getLeval(), TaskScheduleLevalEnum.INTERNAL.getLeval())
        );
        if (!ObjectUtils.isEmpty(tasks)) {
            //待运行或运行中的定时任务
            List<TaskSchedule> runTasks = tasks.stream().filter(v -> {
                return v.getStatus() == TaskScheduleStatusEnum.TO_BE_EXECUTED.getStatus()||v.getStatus() == TaskScheduleStatusEnum.BEING_EXECUTED.getStatus()
                        ||v.getIsDel()==1;
            }).collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(runTasks)) {
                startTask(runTasks);
            }
            //待停止定时任务,把状态为待停止和已删除的任务都停止
            List<TaskSchedule> stopTasks = tasks.stream().filter(v -> {
                return  v.getStatus() == TaskScheduleStatusEnum.STOPPING.getStatus()||v.getIsDel()==0;
            }).collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(stopTasks)) {
                stopTask(stopTasks);
            }
        }

    }

    /**
     * 开启任务
     * @param runTasks
     */
    private void startTask(List<TaskSchedule> runTasks){
        for (TaskSchedule task:runTasks) {
            switch (RunNodeEnum.getEnum(task.getRunNode())){
                case CURRENT_NODE:
                    if(taskScheduleService.startTaskSchedule(task)){
                        taskScheduleService.beginTaskSchedule(task.getTaskScheduleId());
                    }
                    break;
                case APPOINT_NODE:
                    if(task.getRunNodeIp().equals(Config.computerIp)){
                        if(taskScheduleService.startTaskSchedule(task)){
                            taskScheduleService.beginTaskSchedule(task.getTaskScheduleId());
                        }
                    }
                    break;
            }
        }
    }

    private void stopTask(List<TaskSchedule> stopTasks){
        for (TaskSchedule task:stopTasks) {
            switch (RunNodeEnum.getEnum(task.getRunNode())){
                case CURRENT_NODE:
                    if(taskScheduleService.stopTaskSchedule(task.getTaskScheduleId())){
                        taskScheduleService.stopedTaskSchedule(task.getTaskScheduleId());
                    }
                    break;
                case APPOINT_NODE:
                    if(task.getRunNodeIp().equals(Config.computerIp)){
                        if(taskScheduleService.stopTaskSchedule(task.getTaskScheduleId())){
                            taskScheduleService.stopedTaskSchedule(task.getTaskScheduleId());
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
