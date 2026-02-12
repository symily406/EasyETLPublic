package com.easy.etl.scheduled;

import com.easy.etl.cache.CacheCore;
import com.easy.etl.core.callback.ICallBack;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Config;
import com.easy.etl.core.thread.ThreadCore;
import com.easy.etl.em.ExecuteTaskTypeEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.execute.entity.ExecuteJob;
import com.easy.etl.execute.service.IExecuteJobService;
import com.easy.etl.kill.entity.KillJob;
import com.easy.etl.kill.service.IKillJobService;
import com.easy.etl.execute.DataxExecute;
import com.easy.etl.execute.SqlExecute;
import com.easy.etl.rabbitmq.task.TaskJobInfo;
import com.easy.etl.rabbitmq.task.TaskJobResult;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class TaskHandler implements ICallBack {
    private static final Logger LOG = LoggerFactory.getLogger(TaskHandler.class);
    @Resource
    private RedisStreamService redisStreamService;
    @Resource
    private IExecuteJobService executeJobService;
    @Resource
    private IKillJobService killJobService;
    @Resource
    @Qualifier(GlobalConstant.executorThreadPool)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Scheduled(cron = GlobalConstant.jobHandlerCorn)
    public void run() {
        if (StringUtils.isEmpty(Config.computerIp)) {
            return;
        }
        List<ExecuteJob> list = executeJobService.queryExecuteJob(Config.computerIp, Arrays.asList(WorkSpaceTypeEnum.SYNC.getType(), WorkSpaceTypeEnum.SPARK_SQL.getType(), WorkSpaceTypeEnum.SQL.getType()), TaskStatusEnum.TO_BE_EXECUTED);
        if (!ObjectUtils.isEmpty(list)) {
            for (ExecuteJob job : list) {
                TaskJobInfo taskJobInfo = JsonKit.parseObject(CacheCore.getTaskRunner(job.getJobId()), TaskJobInfo.class);
                switch (ExecuteTaskTypeEnum.getExecuteTaskType(taskJobInfo.getTaskType())) {
                    case DATAX_TASK://数据集成
                        ThreadCore.addThread(job.getJobId(), addSchedule(new DataxExecute(taskJobInfo, this)));
                        break;
                    case SQL_TASK://脚本
                        ThreadCore.addThread(job.getJobId(), addSchedule(new SqlExecute(taskJobInfo, this)));
                        break;
                    default:
                }
            }
        }
        List<KillJob> killJobs = killJobService.queryKillJobAll(Config.computerIp);
        if (!ObjectUtils.isEmpty(killJobs)) {
            for (KillJob job : killJobs) {
                try {
                    Future<?> future = ThreadCore.getThread(job.getJobId());
                    if (future != null && !future.isDone()) {
                        boolean cancelled = future.cancel(true);
                        if (cancelled) {
                            LOG.info("任务编号:" + job.getJobId() + ",停止成功");
                            ThreadCore.removeThread(job.getJobId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    killJobService.updateStatus(job.getJobId(), TaskStatusEnum.TASK_STOP.getStatus());
                    executeJobService.updateTaskStatus(job.getJobId(), job.getApplicationId(), TaskStatusEnum.TASK_STOP.getStatus());
                    TaskJobResult taskJobInfo = new TaskJobResult(job.getJobId(), job.getJobId(), TaskStatusEnum.TASK_STOP.getDesc(), TaskStatusEnum.TASK_STOP.getStatus(), new Date(), 0l, Config.computerIp);
                    LOG.info("任务编号:" + job.getJobId() + ",发送状态:"+JsonKit.toJsonString(taskJobInfo));
                    redisStreamService.send(GlobalConstant.streamResultkey, taskJobInfo);
                }
            }
        }
        List<ExecuteJob> runs = executeJobService.queryExecuteJob(Config.computerIp, Arrays.asList(WorkSpaceTypeEnum.SPARK_SQL.getType(), WorkSpaceTypeEnum.SQL.getType(), WorkSpaceTypeEnum.WORKFLOW.getType()), TaskStatusEnum.RUN);
        if (!ObjectUtils.isEmpty(runs)) {
            for (ExecuteJob job : runs) {
                TaskJobResult taskJobResult = new TaskJobResult(job.getJobId(), job.getJobId(), TaskStatusEnum.RUN.getDesc(), null, TaskStatusEnum.RUN.getStatus(), new Date(), 0l, Config.computerIp);
                redisStreamService.send(GlobalConstant.streamResultkey, taskJobResult);
            }
        }
    }

    private Future<?> addSchedule(Runnable runnable) {
        return threadPoolTaskExecutor.submit(runnable);
    }

    @Override
    public void start(String jobId, String applicationId, String message, int status, Date time) {
        try {
            TaskJobResult taskJobInfo = new TaskJobResult(jobId, applicationId, message, status, time, Config.computerIp);
            executeJobService.updateTaskStatus(jobId, applicationId, status);
            redisStreamService.send(GlobalConstant.streamResultkey, taskJobInfo);
            LOG.info("任务开始执行：{}", JsonKit.toJsonString(taskJobInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(String jobId, String applicationId, String message, int status, Date time, long timer) {

    }

    @Override
    public void completed(String jobId, String applicationId, String message, String result, int status, Date time, long timer) {
        try {
            executeJobService.updateTaskStatus(jobId, applicationId, status);
            TaskJobResult taskJobResult = new TaskJobResult(jobId, applicationId, message, result, status, time, timer, Config.computerIp);
            redisStreamService.send(GlobalConstant.streamResultkey, taskJobResult);
            LOG.info("任务执行完成：{}", JsonKit.toJsonString(taskJobResult));
        } catch (Exception e) {
            LOG.error("发送任务结果时发生异常，任务ID: {}", jobId, e);
        }
    }
}
