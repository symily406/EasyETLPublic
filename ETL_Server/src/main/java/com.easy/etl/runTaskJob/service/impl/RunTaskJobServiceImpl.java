package com.easy.etl.runTaskJob.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.cache.CacheCore;
import com.easy.etl.core.constant.GlobalCache;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Config;
import com.easy.etl.core.init.Zk;
import com.easy.etl.datax.kit.DataXKit;
import com.easy.etl.datax.model.core.DataXServer;
import com.easy.etl.em.HandleEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.em.TaskTypeEnum;
import com.easy.etl.conf.entity.Conf;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.utils.ConfConvert;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.etl.execute.entity.ExecuteJob;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.mapper.FlowRunJobMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobNodeMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobPnodeMapper;
import com.easy.etl.kill.entity.KillJob;
import com.easy.etl.projectTaskFlow.mapper.FlowJobMapper;
import com.easy.etl.projectTaskFlow.mapper.FlowJobNodeMapper;
import com.easy.etl.projectTaskFlow.mapper.FlowJobPnodeMapper;
import com.easy.etl.runTaskJob.dto.RunTaskJobDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.entity.RunTaskJobTime;
import com.easy.etl.runTaskJob.mapper.RunTaskJobLogMapper;
import com.easy.etl.runTaskJob.mapper.RunTaskJobMapper;
import com.easy.etl.runTaskJob.mapper.RunTaskJobTimeMapper;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.easy.etl.runTaskJob.vo.RunTaskJobVo;
import com.easy.etl.rabbitmq.task.TaskJobInfo;
import com.easy.etl.rabbitmq.task.TaskJobResult;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.easy.run.cache.CacheManagement;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * 任务运行-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class RunTaskJobServiceImpl extends BaseServiceImpl<RunTaskJobMapper, RunTaskJob> implements IRunTaskJobService {
    private static Logger log = LoggerFactory.getLogger("TASK_PUSH_INFO");
    @Autowired
    private RunTaskJobTimeMapper runTaskJobTimeMapper;

    @Autowired
    private RunTaskJobLogMapper runTaskJobLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private FlowRunJobNodeMapper flowRunJobNodeMapper;
    @Autowired
    private FlowRunJobPnodeMapper flowRunJobPnodeMapper;
    @Autowired
    private FlowRunJobMapper flowRunJobMapper;

    @Autowired
    private FlowJobNodeMapper flowJobNodeMapper;
    @Autowired
    private FlowJobPnodeMapper flowJobPnodeMapper;
    @Autowired
    private FlowJobMapper flowJobMapper;

    @Autowired
    private IConfService confService;

    @Autowired
    private RedisStreamService redisStreamService;


    @Override
    public RunTaskJob queryRunnerJob(String taskId) {
        LambdaQueryWrapper<RunTaskJob> queryJob = new LambdaQueryWrapper<>();
        queryJob.select(RunTaskJob::getTaskId, RunTaskJob::getJobId, RunTaskJob::getStatus, RunTaskJob::getHasResult, RunTaskJob::getEndTime);
        queryJob.eq(RunTaskJob::getTaskId, taskId);
        queryJob.in(RunTaskJob::getStatus,
                TaskStatusEnum.TO_BE_ALLOCATED.getStatus(),
                TaskStatusEnum.ACCEPTED.getStatus(),
                TaskStatusEnum.TO_BE_EXECUTED.getStatus(),
                TaskStatusEnum.RUN.getStatus(),
                TaskStatusEnum.WATE_TASK_STOP.getStatus()
        );
        queryJob.orderByDesc(RunTaskJob::getAddTime);
        List<RunTaskJob> jobs = baseMapper.selectList(queryJob);
        if (ObjectUtils.isEmpty(jobs)) {
            return null;
        }
        return jobs.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean jobStop(List<String> jobIds) {
        if (ObjectUtils.isEmpty(jobIds)) {
            return false;
        }
        List<RunTaskJob> runTaskJobs = baseMapper.selectList(
                new LambdaQueryWrapper<RunTaskJob>()
                        .in(RunTaskJob::getJobId, jobIds)
                        .notIn(RunTaskJob::getStatus, TaskStatusEnum.SUCCED, TaskStatusEnum.FAIL, TaskStatusEnum.WATE_TASK_STOP, TaskStatusEnum.TASK_STOP)
        );
        if (ObjectUtils.isEmpty(runTaskJobs)) {
            return false;
        }
        for (RunTaskJob runTaskJob : runTaskJobs) {
            //待分配任务直接停止
            if (runTaskJob.getStatus() == TaskStatusEnum.TO_BE_ALLOCATED.getStatus()) {
                baseMapper.update(null,
                        new LambdaUpdateWrapper<RunTaskJob>()
                                .set(RunTaskJob::getStatus, TaskStatusEnum.TASK_STOP.getStatus())
                                .eq(RunTaskJob::getJobId, runTaskJob.getJobId())

                );
                RunTaskJobLog log = new RunTaskJobLog();
                log.setJobId(runTaskJob.getJobId());
                log.setTaskId(runTaskJob.getTaskId());
                log.setStatus(TaskStatusEnum.TASK_STOP.getStatus());
                log.setLogContent(TaskStatusEnum.TASK_STOP.getDesc());
                runTaskJobLogMapper.insert(log);
                continue;
            }
            baseMapper.update(null,
                    new LambdaUpdateWrapper<RunTaskJob>()
                            .set(RunTaskJob::getStatus, TaskStatusEnum.WATE_TASK_STOP.getStatus())
                            .eq(RunTaskJob::getJobId, runTaskJob.getJobId())

            );
            RunTaskJobLog log = new RunTaskJobLog();
            log.setJobId(runTaskJob.getJobId());
            log.setTaskId(runTaskJob.getTaskId());
            log.setStatus(TaskStatusEnum.WATE_TASK_STOP.getStatus());
            log.setLogContent(TaskStatusEnum.WATE_TASK_STOP.getDesc());
            runTaskJobLogMapper.insert(log);
            KillJob killJob = new KillJob();
            BeanUtils.copyProperties(runTaskJob, killJob);
            redisStreamService.send(GlobalConstant.streamKillkey, killJob);
        }
        return true;
    }

    /**
     * 保存或修改Entity
     *
     * @param runTaskJob
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateRunTaskJob(RunTaskJob runTaskJob) {
        runTaskJob.setTaskType(TaskTypeEnum.ROUTINE_TASK.getType());
        baseMapper.insert(runTaskJob);
        if (Integer.parseInt(runTaskJob.getWorkSpaceType()) == 2) {
            RunTaskJobLog log = new RunTaskJobLog();
            log.setJobId(runTaskJob.getJobId());
            log.setTaskId(runTaskJob.getTaskId());
            log.setStatus(runTaskJob.getStatus());
            log.setLogContent("执行的sql:" + runTaskJob.getScriptContent());
            runTaskJobLogMapper.insert(log);
        }
        RunTaskJobLog log = new RunTaskJobLog();
        log.setJobId(runTaskJob.getJobId());
        log.setTaskId(runTaskJob.getTaskId());
        log.setStatus(runTaskJob.getStatus());
        log.setLogContent("等待任务分配...");
        runTaskJobLogMapper.insert(log);
        return true;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RunTaskJobVo> queryRunTaskJobPage(IPage<RunTaskJobVo> page, RunTaskJobDTO param) {
        return page.setRecords(baseMapper.queryRunTaskJobPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<RunTaskJobVo> queryRunTaskJobAll(RunTaskJobDTO param) {
        return baseMapper.queryRunTaskJobAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public RunTaskJobVo queryRunTaskJobByCondition(RunTaskJobDTO param) {
        return baseMapper.queryRunTaskJobByCondition(param);
    }

    @Override
    public List<RunTaskJobVo> queryRunTaskJob(RunTaskJobDTO param) {
        return baseMapper.queryRunTaskJob(param);
    }

    /**
     * 返回任务的运行结果
     *
     * @param jobId
     * @return
     */

    @Override
    public String queryJobResult(String jobId) {
        return CacheManagement.getSqlResult(jobId);
    }

    /**
     * 根据taskID返回有运行结果的任务
     *
     * @param taskId
     * @return
     */
    @Override
    public List<RunTaskJobVo> queryTaskJobByTaskId(String taskId) {
        return baseMapper.queryTaskJobByTaskId(taskId, DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
    }

    /**
     * 移除运行结果
     *
     * @param jobId
     * @param taskId
     * @return
     */
    @Override
    public List<RunTaskJobVo> removeTaskJobResult(String jobId, String taskId) {
        baseMapper.update(null, new LambdaUpdateWrapper<RunTaskJob>()
                .eq(RunTaskJob::getJobId, jobId)
                .set(RunTaskJob::getCacheResult, 0)
        );
        CacheManagement.removeSqlResultById(jobId);
        return queryTaskJobByTaskId(taskId);
    }

    /**
     * 保存运行日志
     *
     * @param result
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveJobResult(TaskJobResult result) {
        RunTaskJob job = baseMapper.selectById(result.getJobId());
        if (ObjectUtils.isEmpty(job)) {
            return;
        }

        if (TaskStatusEnum.getTaskStatusEnum(job.getStatus()) == TaskStatusEnum.FAIL || TaskStatusEnum.getTaskStatusEnum(job.getStatus()) == TaskStatusEnum.TASK_STOP) {
            return;
        }

        if (TaskStatusEnum.getTaskStatusEnum(result.getStatus()) == TaskStatusEnum.SUCCED && StringUtils.isNotEmpty(result.getResult())) {
            job.setCacheResult(1);
            job.setCacheExpirationTime(DateUtils.addMinutes(new Date(), GlobalCache.sqlResultExpirationTime));
        }
        job.setRunNum(job.getRunNum() + 1);
        job.setJobId(result.getJobId());
        job.setStatus(result.getStatus());
        job.setApplicationId(result.getApplicationId());
        if (ObjectUtil.isNotEmpty(result.getStartDate())) {
            job.setStartTime(result.getStartDate());
        }
        if (ObjectUtil.isNotEmpty(result.getEndDate())) {
            job.setEndTime(result.getEndDate());
        }
        if (!ObjectUtils.isEmpty(job.getStartTime()) && !ObjectUtils.isEmpty(job.getEndTime())) {
            job.setUsageTime(job.getEndTime().getTime() - job.getStartTime().getTime());
        }
        job.setIp(result.getExecuteIp());
        job.setIsHandle(HandleEnum.UNTREATED.getStatus());
        baseMapper.updateById(job);

        boolean update = true;
        RunTaskJobTime jobTime = runTaskJobTimeMapper.selectById(job.getJobId());
        if (jobTime == null) {
            update = false;
            jobTime = new RunTaskJobTime();
            jobTime.setJobId(job.getJobId());
        }
        if (ObjectUtil.isNotEmpty(result.getStartDate())) {
            jobTime.setStartTime(result.getStartDate());
        }
        if (ObjectUtil.isNotEmpty(result.getEndDate())) {
            jobTime.setEndTime(result.getEndDate());
        }
        if (ObjectUtil.isNotEmpty(result.getTimer())) {
            jobTime.setUsageTime(result.getTimer());
        }
        jobTime.setTaskId(job.getTaskId());
        if (update) {
            runTaskJobTimeMapper.updateById(jobTime);
        } else {
            runTaskJobTimeMapper.insert(jobTime);
        }

        RunTaskJobLog jobLog = new RunTaskJobLog();
        jobLog.setJobId(job.getJobId());
        jobLog.setTaskId(job.getTaskId());
        jobLog.setLogContent(result.getMessage());
        jobLog.setStatus(result.getStatus());
        runTaskJobLogMapper.insert(jobLog);

        if (TaskTypeEnum.getTaskTypeEnum(job.getAddType()) == TaskTypeEnum.WORKFLOW_TASK) {
            String executeNodeId = job.getExecuteNodeId();
            String flowRunJobId = job.getFlowRunJobId();
            //TF_FLOW_RUN_JOB_NODE--工作流运行Node
            LambdaUpdateWrapper<FlowRunJobNode> flowRunJobNodeLambdaUpdateWrapper = new LambdaUpdateWrapper<FlowRunJobNode>();
            if (ObjectUtil.isNotEmpty(result.getStartDate())) {
                flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getExecuteTime, result.getStartDate());
            }
            if (ObjectUtil.isNotEmpty(result.getEndDate())) {
                flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getCompletedTime, result.getEndDate());
            }
            if (ObjectUtil.isNotEmpty(result.getTimer())) {
                flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getUsageTime, result.getTimer());
            }
            flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getStatus, result.getStatus());
            flowRunJobNodeLambdaUpdateWrapper.eq(FlowRunJobNode::getExecuteNodeId, executeNodeId);
            flowRunJobNodeLambdaUpdateWrapper.eq(FlowRunJobNode::getFlowRunJobId, flowRunJobId);
            flowRunJobNodeMapper.update(null, flowRunJobNodeLambdaUpdateWrapper);

            //TF_FLOW_RUN_JOB_PNODE--工作流运行依懒node
            flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>()
                    .eq(FlowRunJobPnode::getExecuteNodeId, executeNodeId)
                    .eq(FlowRunJobPnode::getFlowRunJobId, flowRunJobId)
                    .set(FlowRunJobPnode::getStatus, result.getStatus())
            );
        }
    }

    @Override
    public void taskPush() {
        List<String> executeNodes = Zk.getExecuteNodes();
        log.info("执行器节点:" + executeNodes);
        if (executeNodes.size() == 0) {
            log.info("执行器未启动");
            return;
        }
        int totalRun = 0;
        Map<String, Integer> executeNodeMap = new HashMap<>();
        for (String executeNode : executeNodes) {
            int runNum = CacheCore.getTaskRunnerNum(executeNode);
            totalRun += runNum;
            int freeRunNum = GlobalConstant.concurrentConsumers - runNum;
            if (freeRunNum <= 0) {
                continue;
            }
            executeNodeMap.put(executeNode, freeRunNum);
        }
        ConfVo conf = confService.getConf();
        if (ObjectUtils.isEmpty(conf)) {
            log.info("请先进行系统配置才能运行任务,路径:项目管理->系统配置");
            return;
        }
        int runBasicQos = GlobalConstant.concurrentConsumers * executeNodes.size();
        int limitSize = runBasicQos - totalRun;
        log.info("任务推送队列开始,执行器数量:" + executeNodes.size() + ",当前队列大小:" + runBasicQos + ",正在运行:" + totalRun + ",推送数量:" + limitSize);
        if (limitSize > 0) {
            List<TaskJobInfo> list = baseMapper.queryTaskJobInfo(limitSize);
            for (Map.Entry<String, Integer> entry : executeNodeMap.entrySet()) {
                List<TaskJobInfo> sendList = list.stream().limit(entry.getValue()).collect(Collectors.toList());
                for (TaskJobInfo taskJobInfo : sendList) {
                    try {
                        taskJobInfo.setScriptContent(StringUtils.replace(taskJobInfo.getScriptContent(),
                                GlobalConstant.dataxServerExpression, DataXKit.createDataXServer(taskJobInfo.getJobId(), conf.getConfDatax().getDataxService())));
                        ConfConvert.getConfVo(conf, taskJobInfo.getEvnContent(), taskJobInfo.getWorkSpaceType());
                        taskJobInfo.setEvnContent(null);
                        taskJobInfo.setWorkSpaceType(null);
                        taskJobInfo.setBase(conf.getConfBase());
                        taskJobInfo.setYarn(conf.getConfYarn());
                        taskJobInfo.setDatax(conf.getConfDatax());
                        taskJobInfo.setSql(conf.getConfSql());
                        ExecuteJob executeJob = new ExecuteJob();
                        BeanUtils.copyProperties(taskJobInfo, executeJob);
                        CacheCore.setTaskRunner(executeJob.getJobId(), JsonKit.toJsonString(taskJobInfo));
                        redisStreamService.send(GlobalConstant.streamRunnerkey, executeJob);
                        RunTaskJob job = new RunTaskJob();
                        job.setJobId(taskJobInfo.getJobId());
                        job.setTaskId(taskJobInfo.getTaskId());
                        job.setStatus(TaskStatusEnum.TO_BE_EXECUTED.getStatus());
                        baseMapper.updateById(job);
                        log.info("任务推送队列成功,taskId" + job.getTaskId() + ",JobId:" + job.getJobId());
                    } catch (Exception e) {
                        log.error("任务推送队列错误," + e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void taskAllot(String ip, int limitSize) {
        LambdaQueryWrapper<RunTaskJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(RunTaskJob::getJobId, RunTaskJob::getTaskId, RunTaskJob::getScriptContent, RunTaskJob::getHasResult);
        queryWrapper.eq(RunTaskJob::getStatus, 1);
        queryWrapper.last("limit " + limitSize);

        List<RunTaskJob> list = baseMapper.selectList(queryWrapper);
        for (RunTaskJob runTaskJob : list) {
            RunTaskJob job = new RunTaskJob();
            job.setJobId(runTaskJob.getJobId());
            job.setTaskId(runTaskJob.getTaskId());
            job.setStatus(TaskStatusEnum.TO_BE_EXECUTED.getStatus());
            job.setHasResult(runTaskJob.getHasResult());
            job.setIp(ip);
            baseMapper.updateById(job);

            String logInfo = "任务执行服务器IP:" + ip + "\n";
            logInfo += "====================================================\n";
            logInfo += "任务执行脚本\n";
            logInfo += runTaskJob.getScriptContent() + "\n";
            logInfo += "====================================================";

            RunTaskJobLog log = new RunTaskJobLog();
            log.setJobId(job.getJobId());
            log.setTaskId(job.getTaskId());
            log.setStatus(job.getStatus());
            log.setLogContent(logInfo);
            runTaskJobLogMapper.insert(log);
        }
    }
}
