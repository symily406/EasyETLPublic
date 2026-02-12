package com.easy.system.taskSchedule.compont;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.system.taskSchedule.em.TaskScheduleExecutionEnum;
import com.easy.system.taskSchedule.em.TaskScheduleStatusEnum;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.easy.system.taskSchedule.service.ITaskScheduleLogService;
import com.easy.system.taskSchedule.service.ITaskScheduleService;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.plugin.context.Application;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

public abstract class AbstractBaseCronTask implements Runnable {
    private static Logger log = LoggerFactory.getLogger("TASK_PUSH_INFO");
    private TaskSchedule taskSchedule;
    private TaskScheduleResult taskScheduleResult;

    private TaskScheduleMapper taskScheduleMapper;

    private TaskScheduleLogMapper taskScheduleLogMapper;

    public AbstractBaseCronTask(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        this.taskSchedule = taskSchedule;
        this.taskScheduleMapper = taskScheduleMapper;
        this.taskScheduleLogMapper = taskScheduleLogMapper;
    }

    //前置操作
    public abstract void beforeJob(TaskSchedule taskSchedule);


    //任务操作
    public abstract void startJob(TaskSchedule taskSchedule);


    //后置操作
    public abstract void afterJob(TaskScheduleResult taskScheduleResult);

    //错误操作
    @Override
    public void run() {
        taskScheduleResult = new TaskScheduleResult();
        taskScheduleResult.setTaskScheduleId(taskSchedule.getTaskScheduleId());
        StringBuilder message = new StringBuilder();
        message.append(String.format("任务%s开始执行,时间:%s", taskSchedule.getName(), DateUtil.now())).append("<br>");
        log.info("---------------------任务 {} 开始执行-----------------------", taskSchedule.getName());
        if (StringUtils.isNotBlank(taskSchedule.getParameter())){
            log.info("任务参数：" + taskSchedule.getParameter());
            message.append(String.format("任务参数：%s", taskSchedule.getParameter())).append("<br>");
        }
        if (StringUtils.isNotBlank(taskSchedule.getRemarks())){
            log.info("任务描述：" + taskSchedule.getRemarks());
            message.append(String.format("任务描述：%s", taskSchedule.getRemarks())).append("<br>");
        }
        TimeInterval timer = DateUtil.timer();
        try {
            taskScheduleMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                    .set(TaskSchedule::getExecutionStatus, TaskScheduleExecutionEnum.TO_BE_EXECUTED.getStatus())
                    .eq(TaskSchedule::getTaskScheduleId, taskSchedule.getTaskScheduleId())
            );
            beforeJob(this.taskSchedule);
            startJob(this.taskSchedule);
            taskScheduleMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                    .set(TaskSchedule::getExecutionStatus, TaskScheduleExecutionEnum.BEING_EXECUTED.getStatus())
                    .set(TaskSchedule::getRunNextTime, CronCalculator.calculator(taskSchedule.getCorn()))
                    .eq(TaskSchedule::getTaskScheduleId, taskSchedule.getTaskScheduleId())
            );
            log.info("任务耗时：约 {} 毫秒", timer.interval());
            message.append(String.format("任务耗时：约%s毫秒", timer.interval())).append("<br>");
            taskScheduleResult.setUseTimer(timer.interval());
            taskScheduleResult.setTaskScheduleExecutionEnum(TaskScheduleExecutionEnum.COMPLETE);
        } catch (Exception e) {
            log.error("任务{}计算出错！请及时排查问题！", taskSchedule.getName(), e);
            message.append(String.format("任务%s计算出错！请及时排查问题！", taskSchedule.getName())).append("<br>");
            message.append(e).append("<br>");
            e.printStackTrace();
            taskScheduleMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                    .set(TaskSchedule::getExecutionStatus, TaskScheduleExecutionEnum.EXECUTION_FAILED.getStatus())
                    .set(TaskSchedule::getRunNextTime, CronCalculator.calculator(taskSchedule.getCorn()))
                    .eq(TaskSchedule::getTaskScheduleId, taskSchedule.getTaskScheduleId())
            );
            taskScheduleResult.setTaskScheduleExecutionEnum(TaskScheduleExecutionEnum.EXECUTION_FAILED);
        } finally {
            log.info("---------------------任务 {} 结束执行,用时{}毫秒-----------------------\n", taskSchedule.getName(), timer.interval());
            message.append(String.format("任务%s结束执行,用时%s毫秒", taskSchedule.getName(), timer.interval()));
            taskScheduleResult.setMessage(message.toString());
            afterJob(taskScheduleResult);
            insertTaskScheduleLog(taskSchedule.getTaskScheduleId(), taskScheduleResult);
        }
    }

    private void insertTaskScheduleLog(String taskScheduleId, TaskScheduleResult taskScheduleResult) {
        TaskScheduleLog taskScheduleLog = new TaskScheduleLog();
        taskScheduleLog.setTaskScheduleId(taskScheduleId);
        taskScheduleLog.setExecuteTime(DateUtil.date());
        taskScheduleLog.setContent(taskScheduleResult.getMessage());
        taskScheduleLog.setExecutionStatus(taskScheduleResult.getTaskScheduleExecutionEnum().getStatus());
        taskScheduleLog.setUseTime(taskScheduleResult.getUseTimer());
        if (!ObjectUtils.isEmpty(taskScheduleLogMapper)){
            taskScheduleLogMapper.insert(taskScheduleLog);
        }
    }
}
