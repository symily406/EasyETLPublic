package com.easy.system.taskSchedule.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.init.Config;
import com.easy.system.operationLog.entity.OperationLog;
import com.easy.system.operationLog.service.IOperationLogService;
import com.easy.system.operationLog.service.impl.OperationLogServiceImpl;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.easy.system.taskSchedule.service.ITaskScheduleLogService;
import com.easy.system.taskSchedule.service.impl.TaskScheduleLogServiceImpl;
import com.spring.boot.ext.plugin.context.Application;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 清空日志定时任务
 */
public class ClearLogTask extends AbstractBaseCronTask {

    private ITaskScheduleLogService taskScheduleLogService;

    private IOperationLogService operationLogService;

    public ClearLogTask(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, taskScheduleLogMapper);
    }

    @Override
    public void beforeJob(TaskSchedule taskSchedule) {

    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        if(ObjectUtils.isEmpty(taskScheduleLogService)){
           taskScheduleLogService=  Application.getBean(TaskScheduleLogServiceImpl.class);
        }
        if(ObjectUtils.isEmpty(operationLogService)){
            operationLogService= Application.getBean(OperationLogServiceImpl.class);
        }
        taskScheduleLogService.remove(
                new QueryWrapper<TaskScheduleLog>()
                .le("DATE_FORMAT(ADD_TIME,'%Y-%m-%d')", DateFormatUtils.format( DateUtils.addDays(new Date(),-Config.clearLogDays),"yyyy-MM-dd"))
        );
        List<OperationLog> list = operationLogService.list(new QueryWrapper<OperationLog>()
                .le("DATE_FORMAT(ADD_TIME,'%Y-%m-%d')", DateFormatUtils.format(DateUtils.addDays(new Date(), -Config.clearLogDays), "yyyy-MM-dd"))
        );
        if (!ObjectUtils.isEmpty(list)){
            List<String> ids = list.stream().map(v-> v.getLogId()).collect(Collectors.toList());
            operationLogService.batchDelete(ids);
        }

    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
