package com.easy.etl.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.common.db.model.SqlTable;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.em.*;
import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.mapper.DbSourceMapper;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.easy.etl.flow.service.IFlowService;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.mapper.FlowRunJobMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobNodeMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobPnodeMapper;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeStatusVo;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeVo;
import com.easy.etl.flowRunJob.vo.FlowRunJobPnodeVo;
import com.easy.etl.projectTaskFlow.entity.*;
import com.easy.etl.projectTaskFlow.mapper.*;
import com.easy.etl.projectTaskFlow.utils.ParameterUtils;
import com.easy.etl.projectTaskFlow.vo.FlowJobNodeVo;
import com.easy.etl.projectTaskFlow.vo.FlowJobVo;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.mapper.RunTaskJobLogMapper;
import com.easy.etl.runTaskJob.mapper.RunTaskJobMapper;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.kit.DataMap;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlowServiceImpl implements IFlowService {
    @Autowired
    private FlowJobMapper flowJobMapper;
    @Autowired
    private FlowJobPnodeMapper flowJobPnodeMapper;
    @Autowired
    private FlowJobNodeMapper flowJobNodeMapper;
    @Autowired
    private FlowJobContentMapper flowJobContentMapper;
    @Autowired
    private FlowJobParameterMapper flowJobParameterMapper;
    @Autowired
    private FlowRunJobMapper flowRunJobMapper;
    @Autowired
    private FlowRunJobPnodeMapper flowRunJobPnodeMapper;
    @Autowired
    private FlowRunJobNodeMapper flowRunJobNodeMapper;
    @Autowired
    private FlowRunJobContentMapper flowRunJobContentMapper;

    @Autowired
    private DbSourceMapper dbSourceMapper;
    @Autowired
    private RunTaskJobMapper runTaskJobMapper;
    @Autowired
    private RunTaskJobLogMapper runTaskJobLogMapper;

    @Override
    public List<String> stopTaskFlow(List<String> flowRunJoBIds) {
        List<String> jobIds = new ArrayList<>();
        List<FlowRunJobNode> flowRunJobNodes = flowRunJobNodeMapper.selectList(
                new LambdaQueryWrapper<FlowRunJobNode>()
                        .in(FlowRunJobNode::getFlowRunJobId, flowRunJoBIds)
                        .in(FlowRunJobNode::getStatus, Arrays.asList(
                                TaskStatusEnum.TO_BE_ALLOCATED.getStatus(),
                                TaskStatusEnum.ACCEPTED.getStatus(),
                                TaskStatusEnum.RUN.getStatus(),
                                TaskStatusEnum.PARENT_RUN.getStatus()
                        ))
        );
        if (!ObjectUtils.isEmpty(flowRunJobNodes)) {
            flowRunJobNodeMapper.update(null,
                    new LambdaUpdateWrapper<FlowRunJobNode>()
                            .in(FlowRunJobNode::getJobRunNodeId, flowRunJobNodes.stream().map(v -> v.getJobRunNodeId()).collect(Collectors.toList()))
                            .set(FlowRunJobNode::getStatus, TaskStatusEnum.TASK_STOP.getStatus())
            );
            List<RunTaskJob> runTaskJob = runTaskJobMapper.selectList(new LambdaQueryWrapper<RunTaskJob>()
                    .in(RunTaskJob::getFlowRunJobId,
                            flowRunJobNodes.stream().map(v -> v.getFlowRunJobId()).collect(Collectors.toList())
                    )
            );
            if (!ObjectUtils.isEmpty(runTaskJob)) {
                jobIds.addAll(runTaskJob.stream().map(v -> v.getJobId()).collect(Collectors.toList()));
            }

            flowRunJobMapper.update(null,
                    new LambdaUpdateWrapper<FlowRunJob>()
                            .in(FlowRunJob::getFlowRunJobId,
                                    flowRunJobNodes.stream().map(v -> v.getFlowRunJobId()).collect(Collectors.toList())
                            )
                            .set(FlowRunJob::getStatus, TaskStatusEnum.TASK_STOP.getStatus())
            );
        }
        List<FlowRunJobPnode> flowRunJobPnodes = flowRunJobPnodeMapper.selectList(new LambdaQueryWrapper<FlowRunJobPnode>()
                .in(FlowRunJobPnode::getExecuteNodeId,
                        flowRunJobNodes.stream().map(v -> v.getExecuteNodeId()).collect(Collectors.toList())
                )
        );
        if (!ObjectUtils.isEmpty(flowRunJobPnodes)) {
            flowRunJobPnodeMapper.update(null,
                    new LambdaUpdateWrapper<FlowRunJobPnode>()
                            .in(FlowRunJobPnode::getId, flowRunJobPnodes.stream().map(v -> v.getId()).collect(Collectors.toList()))
                            .set(FlowRunJobPnode::getStatus, TaskStatusEnum.TASK_STOP.getStatus())
            );
        }

        return jobIds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(TaskSchedule taskSchedule) {
        DataMap param = taskSchedule.getParam();
        if (param.getInt("dealWithFlowTaskStatus") == 1) {
            this.dealWithFlowTaskStatus();
        }
        if (param.getInt("flowJobAddRunJob") == 1) {
            this.flowJobAddRunJob();
        }
        if (param.getInt("flowJobAddTask") == 1) {
            this.flowJobAddTask();
        }
    }

    /**
     * 添加工作流任务到工作流任务列表
     *
     * @return
     */
    @Override
    public void flowJobAddTask() {
        List<FlowJobVo> flowJobVos = flowJobMapper.queryFlowJob(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), TaskStatusEnum.TO_BE_ALLOCATED.getStatus(), GlobalConstant.flowJobAddTaskNum);
        if (!ObjectUtils.isEmpty(flowJobVos)) {
            List<String> flowJobIds = flowJobVos.stream().map(v -> v.getFlowJobId()).collect(Collectors.toList());
            //TF_FLOW_JOB_PNODE--工作流依懒Node
            List<FlowJobPnode> flowJobPnodes = flowJobPnodeMapper.selectList(new LambdaQueryWrapper<FlowJobPnode>().in(FlowJobPnode::getFlowJobId, flowJobIds));
            //TF_FLOW_JOB_NODE--工作流Node
            List<FlowJobNodeVo> flowJobNodes = flowJobNodeMapper.queryflowNodes(flowJobIds);
            //--工作流参数
            List<FlowJobParameter> flowJobParameters = flowJobParameterMapper.selectList(new LambdaQueryWrapper<FlowJobParameter>().in(FlowJobParameter::getFlowJobId, flowJobIds));
            Map<String, String> parameters = ParameterUtils.parse(flowJobParameters.stream().collect(Collectors.toMap(FlowJobParameter::getParamKey, FlowJobParameter::getParamValue)));
            //工作流主任务添加
            for (FlowJobVo flowJobVo : flowJobVos) {
                String flowRunJobId = IdBulider.id();
                int executeCycle = flowJobVo.getExecuteCycle();
                FlowRunJob flowRunJob = new FlowRunJob();
                flowRunJob.setFlowRunJobId(flowRunJobId);
                flowRunJob.setFlowJobId(flowJobVo.getFlowJobId());
                flowRunJob.setTaskId(flowJobVo.getTaskId());
                flowRunJob.setPlaneTime(flowJobVo.getNextExecuteTime());
                flowRunJob.setStatus(TaskStatusEnum.TO_BE_ALLOCATED.getStatus());
                flowRunJob.setExecuteCycle(executeCycle);
                flowRunJob.setFailurePolicy(flowJobVo.getFailurePolicy());
                flowRunJobMapper.insert(flowRunJob);
                //处理上级任务
                List<FlowJobPnode> jobPnodes = flowJobPnodes.stream().filter(v -> flowJobVo.getFlowJobId().equals(v.getFlowJobId())).collect(Collectors.toList());
                if (jobPnodes != null && !jobPnodes.isEmpty()) {
                    for (FlowJobPnode jobPnode : jobPnodes) {
                        FlowRunJobPnode flowRunJobPnode = new FlowRunJobPnode();
                        flowRunJobPnode.setFlowRunJobId(flowRunJobId);
                        flowRunJobPnode.setFlowJobId(jobPnode.getFlowJobId());
                        flowRunJobPnode.setTaskId(jobPnode.getTaskId());
                        flowRunJobPnode.setSourceNodeId(jobPnode.getSourceNodeId());
                        flowRunJobPnode.setSourceTaskId(jobPnode.getSourceTaskId());
                        flowRunJobPnode.setExecuteNodeId(jobPnode.getExecuteNodeId());
                        flowRunJobPnode.setExecuteTaskId(jobPnode.getExecuteTaskId());
                        flowRunJobPnode.setStatus(jobPnode.getStatus());
                        flowRunJobPnode.setFailurePolicy(flowJobVo.getFailurePolicy());
                        flowRunJobPnode.setNextNodeStatus(TaskNextNodeStatusEnum.NEXT_NODE_STATUS_0.getStatus());
                        flowRunJobPnodeMapper.insert(flowRunJobPnode);
                    }
                }
                //处理运行节点及脚本
                List<FlowJobNodeVo> jobNodes = flowJobNodes.stream().filter(v -> v.getFlowJobId().equals(flowJobVo.getFlowJobId())).collect(Collectors.toList());
                if (jobNodes != null && !jobNodes.isEmpty()) {
                    for (FlowJobNodeVo flowJobNode : jobNodes) {
                        FlowRunJobNode flowRunJobNode = new FlowRunJobNode();
                        flowRunJobNode.setFlowRunJobId(flowRunJobId);
                        flowRunJobNode.setFlowJobId(flowJobNode.getFlowJobId());
                        flowRunJobNode.setTaskId(flowJobNode.getTaskId());
                        flowRunJobNode.setWorkSpaceType(flowJobNode.getWorkSpaceType());
                        flowRunJobNode.setExecuteNodeId(flowJobNode.getExecuteNodeId());
                        flowRunJobNode.setExecuteTaskId(flowJobNode.getExecuteTaskId());
                        flowRunJobNode.setNodeType(flowJobNode.getNodeType());
                        flowRunJobNode.setStatus(flowJobNode.getStatus());
                        flowRunJobNode.setPlaneTime(flowRunJob.getPlaneTime());
                        flowRunJobNode.setSeqIndex(flowJobNode.getSeqIndex());
                        if (StringUtils.isNotBlank(flowJobNode.getScriptContent())) {
                            String scriptContent = Expression.parse(flowJobNode.getScriptContent(), parameters);
                            flowRunJobNode.setScriptContent(scriptContent);
                        }
                        flowRunJobNode.setDbSourceId(flowJobNode.getDbSourceId());
                        flowRunJobNodeMapper.insert(flowRunJobNode);
                    }
                }
                FlowJobContent flowJobContent = flowJobContentMapper.selectById(flowJobVo.getFlowJobId());
                FlowRunJobContent flowRunJobContent = new FlowRunJobContent();
                flowRunJobContent.setFlowRunJobId(flowRunJobId);
                flowRunJobContent.setFlowJobId(flowJobVo.getFlowJobId());
                flowRunJobContent.setFlowContent(flowJobContent.getFlowContent());
                flowRunJobContentMapper.insert(flowRunJobContent);

                FlowJob flowJob = new FlowJob();
                flowJob.setFlowJobId(flowJobVo.getFlowJobId());
                flowJob.setNextExecuteTime(CronCalculator.calculator(flowJobVo.getCorn()));
                flowJob.setStatus(TaskStatusEnum.TO_BE_EXECUTED.getStatus());
                flowJob.setFlowRunJobId(flowRunJobId);
                flowJobMapper.updateById(flowJob);
            }
        }
    }

    /**
     * 添加工作流任务到运行任务表
     *
     * @return
     */
    @Override
    public void flowJobAddRunJob() {
        List<FlowRunJobNodeVo> list = flowRunJobNodeMapper.queryFlowRunTasks(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), TaskStatusEnum.TO_BE_ALLOCATED.getStatus(), GlobalConstant.flowJobAddRunTaskNum);
        if (!ObjectUtils.isEmpty(list)) {
            DbSourceDTO dbSourceDTO = new DbSourceDTO();
            dbSourceDTO.setUserLeval(100);
            dbSourceDTO.setUserDutyLeval(10);
            List<DbSourceVo> dbSourceVos = dbSourceMapper.queryDbSourceAll(dbSourceDTO);
            Map<String, DbSourceVo> dbMap = dbSourceVos.stream().collect(HashMap::new, (k, v) -> k.put(v.getDbSourceId(), v), HashMap::putAll);
            for (FlowRunJobNodeVo flowRunJobNodeVo : list) {
                if (flowRunJobNodeVo.getNodeType() == NodeTypeEnum.END_NODE.getNodeType()) {
                    this.updateFlowTaskStatus(flowRunJobNodeVo.getFlowRunJobId(), flowRunJobNodeVo.getFlowJobId(), flowRunJobNodeVo.getExecuteNodeId(), flowRunJobNodeVo.getJobRunNodeId(), TaskStatusEnum.SUCCED.getStatus());
                    continue;
                }
                RunTaskJob runTaskJob = new RunTaskJob();
                runTaskJob.setTaskId(flowRunJobNodeVo.getExecuteTaskId());
                runTaskJob.setTaskType(TaskTypeEnum.WORKFLOW_TASK.getType());
                runTaskJob.setStatus(TaskStatusEnum.TO_BE_ALLOCATED.getStatus());
                runTaskJob.setScriptContent(DbKit.removeComments(flowRunJobNodeVo.getScriptContent()));
                runTaskJob.setWorkSpaceType(flowRunJobNodeVo.getWorkSpaceType());
                runTaskJob.setTaskFolderId(flowRunJobNodeVo.getTaskFolderId());
                runTaskJob.setProjectId(flowRunJobNodeVo.getProjectId());
                runTaskJob.setDbSourceId(flowRunJobNodeVo.getDbSourceId());
                runTaskJob.setNextRunTime(flowRunJobNodeVo.getPlaneTime());
                runTaskJob.setProjectWorkSpaceLayerRelId(flowRunJobNodeVo.getProjectWorkSpaceLayerRelId());
                runTaskJob.setProjectWorkSpaceId(flowRunJobNodeVo.getProjectWorkSpaceId());
                runTaskJob.setAddType(TaskAddTypeEnum.ADD_TYPE_2.getType());
                runTaskJob.setIsRetry(0);
                runTaskJob.setRunNum(0);
                runTaskJob.setFlowRunJobId(flowRunJobNodeVo.getFlowRunJobId());
                runTaskJob.setFlowJobId(flowRunJobNodeVo.getFlowJobId());
                runTaskJob.setExecuteNodeId(flowRunJobNodeVo.getExecuteNodeId());
                runTaskJob.setJobRunNodeId(flowRunJobNodeVo.getJobRunNodeId());
                if (StringUtils.isNotBlank(flowRunJobNodeVo.getDbSourceId())) {
                    DbSourceVo dbSourceVo = dbMap.get(flowRunJobNodeVo.getDbSourceId());
                    runTaskJob.setDbType(dbSourceVo.getSourceTypeName());
                    switch (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(flowRunJobNodeVo.getWorkSpaceType()))) {
                        case SQL:
                        case SPARK_SQL:
                            SqlTable sqlTable = DbKit.parseSqlTables(runTaskJob.getScriptContent(), dbSourceVo.getJdbcDrive());
                            boolean hasResult = DbKit.sqlHasResult(sqlTable.getTables());
                            runTaskJob.setHasResult(hasResult == true ? 1 : 0);
                            break;
                    }
                }

                runTaskJobMapper.insert(runTaskJob);
                switch (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(flowRunJobNodeVo.getWorkSpaceType()))) {
                    case SQL:
                    case SPARK_SQL:
                        RunTaskJobLog log = new RunTaskJobLog();
                        log.setJobId(runTaskJob.getJobId());
                        log.setTaskId(runTaskJob.getTaskId());
                        log.setStatus(runTaskJob.getStatus());
                        log.setLogContent("执行的sql:" + runTaskJob.getScriptContent());
                        runTaskJobLogMapper.insert(log);
                        break;
                }
                RunTaskJobLog log = new RunTaskJobLog();
                log.setJobId(runTaskJob.getJobId());
                log.setTaskId(runTaskJob.getTaskId());
                log.setStatus(runTaskJob.getStatus());
                log.setLogContent("等待任务分配...");
                runTaskJobLogMapper.insert(log);
                //各任务节点状态修改
                this.updateFlowTaskStatus(flowRunJobNodeVo.getFlowRunJobId(), flowRunJobNodeVo.getFlowJobId(), flowRunJobNodeVo.getExecuteNodeId(), flowRunJobNodeVo.getJobRunNodeId(), TaskStatusEnum.TO_BE_EXECUTED.getStatus());
            }
        }
    }

    @Override
    public void updateFlowTaskStatus(String flowRunJobId, String flowJobId, String executeNodeId, String jobRunNodeId, int status) {
        //TF_FLOW_RUN_JOB_NODE-工作流运行Node
        LambdaUpdateWrapper<FlowRunJobNode> flowRunJobNodeLambdaUpdateWrapper = new LambdaUpdateWrapper<FlowRunJobNode>();
        flowRunJobNodeLambdaUpdateWrapper.eq(FlowRunJobNode::getJobRunNodeId, jobRunNodeId);
        flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getStatus, status);
        if (status == TaskStatusEnum.SUCCED.getStatus()) {
            flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getExecuteTime, new Date());
            flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getCompletedTime, new Date());
            flowRunJobNodeLambdaUpdateWrapper.set(FlowRunJobNode::getUsageTime, 0);
        }
        flowRunJobNodeMapper.update(null, flowRunJobNodeLambdaUpdateWrapper);
        //TF_FLOW_RUN_JOB_PNODE--工作流运行依懒node
        flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getExecuteNodeId, executeNodeId).eq(FlowRunJobPnode::getFlowRunJobId, flowRunJobId).set(FlowRunJobPnode::getStatus, status));
        //TF_FLOW_RUN_JOB--工作流运行JOB
        if (status == TaskStatusEnum.SUCCED.getStatus()) {
            flowRunJobMapper.update(null, new LambdaUpdateWrapper<FlowRunJob>().eq(FlowRunJob::getFlowRunJobId, flowRunJobId).set(FlowRunJob::getStatus, status).set(FlowRunJob::getCompletedTime, new Date()));
        } else {
            flowRunJobMapper.update(null, new LambdaUpdateWrapper<FlowRunJob>().eq(FlowRunJob::getFlowRunJobId, flowRunJobId).set(FlowRunJob::getExecuteTime, new Date()).set(FlowRunJob::getStatus, status));
        }
    }

    @Override
    public void dealWithFlowTaskStatus() {
        List<Integer> taskStatus = new ArrayList<>();
        taskStatus.add(TaskStatusEnum.SUCCED.getStatus());
        taskStatus.add(TaskStatusEnum.FAIL.getStatus());
        //节点关联关系
        List<FlowRunJobPnodeVo> flowRunJobPnodes = flowRunJobPnodeMapper.queryFaileAndSuccedFlowRunJobNode(taskStatus, TaskNextNodeStatusEnum.NEXT_NODE_STATUS_0.getStatus());
        if (!ObjectUtils.isEmpty(flowRunJobPnodes)) {
            for (FlowRunJobPnodeVo flowRunJobPnode : flowRunJobPnodes) {
                switch (TaskStatusEnum.getTaskStatusEnum(flowRunJobPnode.getStatus())) {
                    case SUCCED://成功
                        flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getFlowRunJobId, flowRunJobPnode.getFlowRunJobId()).eq(FlowRunJobPnode::getSourceNodeId, flowRunJobPnode.getExecuteNodeId()).set(FlowRunJobPnode::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus()));
                        break;
                    case FAIL://失败
                        switch (FailurePolicyEnum.getFailurePolicyEnum(flowRunJobPnode.getFailurePolicy())) {
                            case FAIL_CONTINUE://失败继续
                                flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getFlowRunJobId, flowRunJobPnode.getFlowRunJobId()).eq(FlowRunJobPnode::getSourceNodeId, flowRunJobPnode.getExecuteNodeId()).eq(FlowRunJobPnode::getStatus, TaskStatusEnum.PARENT_RUN.getStatus()).eq(FlowRunJobPnode::getNextNodeStatus, TaskNextNodeStatusEnum.NEXT_NODE_STATUS_0.getStatus()).set(FlowRunJobPnode::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus()));
                                break;
                            case FAIL_END://失败停止
                                List<FlowRunJobPnode> pnodes = flowRunJobPnodeMapper.selectList(new LambdaQueryWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getFlowRunJobId, flowRunJobPnode.getFlowRunJobId()).eq(FlowRunJobPnode::getSourceNodeId, flowRunJobPnode.getExecuteNodeId()));
                                List<String> pnodeIds = pnodes.stream().map(v -> v.getId()).collect(Collectors.toList());
                                flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().in(FlowRunJobPnode::getId, pnodeIds).set(FlowRunJobPnode::getStatus, TaskStatusEnum.PARENT_FAIL.getStatus()));

                                List<String> executeNodeIds = pnodes.stream().map(v -> v.getExecuteNodeId()).collect(Collectors.toList());

                                flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getFlowRunJobId, flowRunJobPnode.getFlowRunJobId()).in(FlowRunJobPnode::getExecuteNodeId, executeNodeIds).set(FlowRunJobPnode::getNextNodeStatus, TaskNextNodeStatusEnum.NEXT_NODE_STATUS_1.getStatus()));

                                flowRunJobNodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobNode>().eq(FlowRunJobNode::getFlowRunJobId, flowRunJobPnode.getFlowRunJobId()).in(FlowRunJobNode::getExecuteNodeId, executeNodeIds).set(FlowRunJobNode::getStatus, TaskStatusEnum.PARENT_FAIL.getStatus()));
                                break;
                        }
                        break;
                }
                flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getId, flowRunJobPnode.getId()).set(FlowRunJobPnode::getNextNodeStatus, TaskNextNodeStatusEnum.NEXT_NODE_STATUS_1.getStatus()));
            }
        }
        //查找节点中可以执行的节点
        List<FlowRunJobPnodeVo> enableRunNodes = flowRunJobPnodeMapper.queryEnableFlowRunJobNode(TaskStatusEnum.TO_BE_ALLOCATED.getStatus(), TaskNextNodeStatusEnum.NEXT_NODE_STATUS_0.getStatus(), TaskStatusEnum.SUCCED.getStatus());
        if (!ObjectUtils.isEmpty(enableRunNodes)) {
            for (FlowRunJobPnode runNode : enableRunNodes) {
                flowRunJobNodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobNode>().eq(FlowRunJobNode::getFlowRunJobId, runNode.getFlowRunJobId()).eq(FlowRunJobNode::getExecuteNodeId, runNode.getExecuteNodeId()).set(FlowRunJobNode::getExecuteTime, new Date()).set(FlowRunJobNode::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus()));
                flowRunJobPnodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobPnode>().eq(FlowRunJobPnode::getExecuteNodeId, runNode.getExecuteNodeId()).eq(FlowRunJobPnode::getFlowRunJobId, runNode.getFlowRunJobId()).eq(FlowRunJobPnode::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus()).eq(FlowRunJobPnode::getNextNodeStatus, TaskNextNodeStatusEnum.NEXT_NODE_STATUS_0.getStatus()).set(FlowRunJobPnode::getStatus, TaskStatusEnum.TO_BE_EXECUTED.getStatus()));
            }
        }

        //处理运行任务状态及任务状态
        //TF_FLOW_JOB--工作流运行JOB
        List<FlowJob> flowJobs = flowJobMapper.selectList(new LambdaQueryWrapper<FlowJob>().in(FlowJob::getStatus, TaskStatusEnum.RUN.getStatus(), TaskStatusEnum.TO_BE_EXECUTED.getStatus()));
        if (!ObjectUtils.isEmpty(flowJobs)) {
            List<String> flowRunJobIds = flowJobs.stream().map(v -> v.getFlowRunJobId()).collect(Collectors.toList());
            List<FlowRunJobNodeStatusVo> jobNodeStatusVos = flowRunJobNodeMapper.groupNodeStatusByIds(flowRunJobIds, TaskStatusEnum.SUCCED.getStatus(), TaskStatusEnum.FAIL.getStatus());
            for (FlowJob flowJob : flowJobs) {
                FlowRunJobNodeStatusVo vo = jobNodeStatusVos.stream().filter(v -> v.getFlowRunJobId().equals(flowJob.getFlowRunJobId())).findFirst().orElse(new FlowRunJobNodeStatusVo());
                int total = vo.getTotal();
                int succed = vo.getSucced();
                int failed = vo.getFailed();
                int status = TaskStatusEnum.TO_BE_EXECUTED.getStatus();
                if (succed > 0 || failed > 0) {
                    status = TaskStatusEnum.RUN.getStatus();
                }
                if (total - succed - failed == 0) {
                    status = TaskStatusEnum.TO_BE_ALLOCATED.getStatus();
                }
                //工作流
                LambdaUpdateWrapper<FlowJob> flowJobLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                flowJobLambdaUpdateWrapper.set(FlowJob::getStatus, status);
                flowJobLambdaUpdateWrapper.eq(FlowJob::getFlowJobId, flowJob.getFlowJobId());
                flowJobMapper.update(null, flowJobLambdaUpdateWrapper);
            }
        }

        //处理正在运行任务
        List<FlowRunJob> flowRunJobs = flowRunJobMapper.selectList(new LambdaQueryWrapper<FlowRunJob>().notIn(FlowRunJob::getStatus, TaskStatusEnum.SUCCED.getStatus(), TaskStatusEnum.FAIL.getStatus()));
        if (!ObjectUtils.isEmpty(flowRunJobs)) {
            List<String> flowRunJobIds = flowRunJobs.stream().map(v -> v.getFlowRunJobId()).collect(Collectors.toList());
            List<FlowRunJobNodeStatusVo> jobNodeStatusVos = flowRunJobNodeMapper.groupNodeStatusByIds(flowRunJobIds, TaskStatusEnum.SUCCED.getStatus(), TaskStatusEnum.FAIL.getStatus());
            for (FlowRunJob flowJob : flowRunJobs) {
                FlowRunJobNodeStatusVo vo = jobNodeStatusVos.stream().filter(v -> v.getFlowRunJobId().equals(flowJob.getFlowRunJobId())).findFirst().orElse(new FlowRunJobNodeStatusVo());
                int total = vo.getTotal();
                int succed = vo.getSucced();
                int failed = vo.getFailed();
                long totalUsageTime = vo.getTotalUsageTime();
                int status = TaskStatusEnum.SUCCED.getStatus();
                //运行的工作流
                LambdaUpdateWrapper<FlowRunJob> flowRunJobLambdaUpdateWrapper = new LambdaUpdateWrapper<FlowRunJob>();
                if (succed > 0 || failed > 0) {
                    //运行的工作流
                    status = TaskStatusEnum.RUN.getStatus();
                }
                if (total - succed - failed == 0) {
                    //运行的工作流
                    status = TaskStatusEnum.SUCCED.getStatus();
                    flowRunJobLambdaUpdateWrapper.set(FlowRunJob::getCompletedTime, new Date());
                }
                //如果有错误节点,且错误结束
                if (failed > 0 && flowJob.getFailurePolicy() == FailurePolicyEnum.FAIL_END.getStatus()) {
                    status = TaskStatusEnum.FAIL.getStatus();
                    flowRunJobLambdaUpdateWrapper.set(FlowRunJob::getCompletedTime, new Date());
                    flowRunJobNodeMapper.update(null, new LambdaUpdateWrapper<FlowRunJobNode>().eq(FlowRunJobNode::getFlowRunJobId, flowJob.getFlowRunJobId()).eq(FlowRunJobNode::getNodeType, NodeTypeEnum.END_NODE.getNodeType()).set(FlowRunJobNode::getUsageTime, totalUsageTime).set(FlowRunJobNode::getExecuteTime, new Date()).set(FlowRunJobNode::getCompletedTime, new Date()).set(FlowRunJobNode::getStatus, TaskStatusEnum.FAIL.getStatus()));
                }
                //运行的工作流
                flowRunJobLambdaUpdateWrapper.set(FlowRunJob::getStatus, status);
                flowRunJobLambdaUpdateWrapper.set(FlowRunJob::getFailedTaskNum, failed);
                flowRunJobLambdaUpdateWrapper.set(FlowRunJob::getUsageTime, totalUsageTime);
                flowRunJobLambdaUpdateWrapper.eq(FlowRunJob::getFlowRunJobId, flowJob.getFlowRunJobId());

                flowRunJobMapper.update(null, flowRunJobLambdaUpdateWrapper);
            }
        }
    }

}
