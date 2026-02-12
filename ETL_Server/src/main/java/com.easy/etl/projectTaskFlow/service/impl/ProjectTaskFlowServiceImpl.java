package com.easy.etl.projectTaskFlow.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.em.NodeTypeEnum;
import com.easy.etl.em.TaskAddTypeEnum;
import com.easy.etl.em.TaskNextNodeStatusEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.mapper.DbSourceMapper;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.mapper.FlowRunJobMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobNodeMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobPnodeMapper;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.easy.etl.projectTask.mapper.ProjectTaskSchedueMapper;
import com.easy.etl.projectTaskFlow.dto.ProjectTaskFlowDTO;
import com.easy.etl.projectTaskFlow.entity.*;
import com.easy.etl.projectTaskFlow.mapper.*;
import com.easy.etl.projectTaskFlow.model.Flow;
import com.easy.etl.projectTaskFlow.model.FlowEdge;
import com.easy.etl.projectTaskFlow.model.FlowNode;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowNodeService;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowService;
import com.easy.etl.projectTaskFlow.utils.ParameterUtils;
import com.easy.etl.projectTaskFlow.vo.FlowJobVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowNodeVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowSourceTargetNodeVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.mapper.ProjectTaskParameterMapper;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目工作流-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskFlowServiceImpl extends BaseServiceImpl<ProjectTaskFlowMapper, ProjectTaskFlow> implements IProjectTaskFlowService {

    @Autowired
    private ProjectTaskFlowNodeMapper projectTaskFlowNodeMapper;
    @Autowired
    private ProjectTaskFlowSourceTargetNodeMapper projectTaskFlowSourceTargetNodeMapper;

    @Autowired
    private ProjectTaskParameterMapper projectTaskParameterMapper;

    @Autowired
    private FlowJobMapper flowJobMapper;
    @Autowired
    private FlowJobNodeMapper flowJobNodeMapper;
    @Autowired
    private FlowJobPnodeMapper flowJobPnodeMapper;
    @Autowired
    private FlowJobParameterMapper flowJobParameterMapper;
    @Autowired
    private FlowJobContentMapper flowJobContentMapper;

    @Autowired
    private ProjectTaskMapper projectTaskMapper;


    @Autowired
    private FlowRunJobMapper flowRunJobMapper;
    @Autowired
    private FlowRunJobPnodeMapper flowRunJobPnodeMapper;
    @Autowired
    private FlowRunJobNodeMapper flowRunJobNodeMapper;
    @Autowired
    private FlowRunJobContentMapper flowRunJobContentMapper;

    @Autowired
    private ProjectTaskSchedueMapper projectTaskSchedueMapper;


    /**
     * 保存或修改Entity
     *
     * @param projectTaskFlow
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateProjectTaskFlow(ProjectTaskFlow projectTaskFlow, ProjectTask task, ProjectTaskSchedue taskSchedue, List<ProjectTaskFlowNode> taskFlowNodes, List<ProjectTaskFlowSourceTargetNode> taskFlowSourceTargetNodes, List<ProjectTaskParameter> projectTaskParameters) {

        projectTaskFlowNodeMapper.delete(new LambdaQueryWrapper<ProjectTaskFlowNode>().eq(ProjectTaskFlowNode::getTaskId, projectTaskFlow.getTaskId()));
        for (ProjectTaskFlowNode taskFlowNode : taskFlowNodes) {
            projectTaskFlowNodeMapper.insert(taskFlowNode);
        }
        projectTaskFlowSourceTargetNodeMapper.delete(new LambdaQueryWrapper<ProjectTaskFlowSourceTargetNode>().eq(ProjectTaskFlowSourceTargetNode::getTaskId, projectTaskFlow.getTaskId()));
        for (ProjectTaskFlowSourceTargetNode taskFlowSourceTargetNode : taskFlowSourceTargetNodes) {
            projectTaskFlowSourceTargetNodeMapper.insert(taskFlowSourceTargetNode);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("taskId", projectTaskFlow.getTaskId());
        projectTaskParameterMapper.deleteByCondition("DELETE FROM TF_PROJECT_TASK_PARAMETER WHERE TASK_ID=:taskId", param);
        for (ProjectTaskParameter projectTaskParameter : projectTaskParameters) {
            projectTaskParameter.setId(idBulider());
            projectTaskParameterMapper.insert(projectTaskParameter);
        }

        projectTaskMapper.updateById(task);

        //任务调度方式

        if(projectTaskSchedueMapper.selectCount(new LambdaUpdateWrapper<ProjectTaskSchedue>().eq(ProjectTaskSchedue::getTaskId,task.getTaskId()))>0){
            projectTaskSchedueMapper.updateById(taskSchedue);
        }else{
            projectTaskSchedueMapper.insert(taskSchedue);
        }
        return saveOrUpdate(projectTaskFlow);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ProjectTaskFlowVo> queryProjectTaskFlowPage(IPage<ProjectTaskFlowVo> page, ProjectTaskFlowDTO param) {
        return page.setRecords(baseMapper.queryProjectTaskFlowPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<ProjectTaskFlowVo> queryProjectTaskFlowAll(ProjectTaskFlowDTO param) {
        return baseMapper.queryProjectTaskFlowAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public ProjectTaskFlowVo queryProjectTaskFlowByCondition(ProjectTaskFlowDTO param) {
        return baseMapper.queryProjectTaskFlowByCondition(param);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submitFlowTask(ProjectTask projectTask,ProjectTaskFlow taskFlow, List<ProjectTaskFlowNodeVo> flowNodes, List<ProjectTaskFlowSourceTargetNodeVo> sourceTargetNodes, List<ProjectTaskParameter> flowParams) {
        String flowJobId = projectTask.getTaskId();

        flowJobNodeMapper.delete(new LambdaQueryWrapper<FlowJobNode>().eq(FlowJobNode::getFlowJobId, flowJobId));
        flowJobPnodeMapper.delete(new LambdaQueryWrapper<FlowJobPnode>().eq(FlowJobPnode::getFlowJobId, flowJobId));

        for (ProjectTaskFlowNodeVo flowNode : flowNodes) {
            //工作流Node
            FlowJobNode flowJobNode = new FlowJobNode();
            flowJobNode.setFlowJobId(flowJobId);
            flowJobNode.setTaskId(flowNode.getTaskId());
            flowJobNode.setWorkSpaceType(flowNode.getWorkSpaceType());
            flowJobNode.setExecuteNodeId(flowNode.getExecuteNodeId());
            flowJobNode.setExecuteTaskId(flowNode.getExecuteTaskId());
            flowJobNode.setNodeType(flowNode.getNodeType());
            flowJobNode.setScriptContent(flowNode.getScriptContent());
            flowJobNode.setStatus(TaskStatusEnum.PARENT_RUN.getStatus());
            flowJobNode.setSeqIndex(flowNode.getSeqIndex());
            if (flowNode.getNodeType() == NodeTypeEnum.START_NODE.getNodeType()) {
                flowJobNode.setStatus(TaskStatusEnum.SUCCED.getStatus());
            }
            flowJobNodeMapper.insert(flowJobNode);
        }

        for (ProjectTaskFlowSourceTargetNodeVo nodeVo : sourceTargetNodes) {
            //工作流依懒Node
            FlowJobPnode flowJobPnode = new FlowJobPnode();
            flowJobPnode.setFlowJobId(flowJobId);
            flowJobPnode.setTaskId(nodeVo.getTaskId());
            flowJobPnode.setExecuteNodeId(nodeVo.getExecuteNodeId());
            flowJobPnode.setExecuteTaskId(nodeVo.getExecuteTaskId());
            flowJobPnode.setSourceNodeId(nodeVo.getSourceNodeId());
            flowJobPnode.setSourceTaskId(nodeVo.getSourceTaskId());
            flowJobPnode.setStatus(TaskStatusEnum.PARENT_RUN.getStatus());
            if (nodeVo.getNodeType() == NodeTypeEnum.START_NODE.getNodeType()) {
                flowJobPnode.setStatus(TaskStatusEnum.SUCCED.getStatus());
            }
            flowJobPnodeMapper.insert(flowJobPnode);
        }
        //工作流JOB参数
        flowJobParameterMapper.delete(new LambdaQueryWrapper<FlowJobParameter>().eq(FlowJobParameter::getFlowJobId, flowJobId));
        for (ProjectTaskParameter parameter : flowParams) {
            FlowJobParameter flowJobParameter = new FlowJobParameter();
            flowJobParameter.setFlowJobId(flowJobId);
            flowJobParameter.setTaskId(projectTask.getTaskId());
            flowJobParameter.setParamKey(parameter.getParamKey());
            flowJobParameter.setParamValue(parameter.getParamValue());
            flowJobParameter.setParamType(parameter.getParamType());
            flowJobParameterMapper.insert(flowJobParameter);
        }
        //工作流内容
        flowJobContentMapper.deleteById(flowJobId);
        FlowJobContent flowJobContent=new FlowJobContent();
        flowJobContent.setFlowJobId(flowJobId);
        flowJobContent.setFlowContent(taskFlow.getFlowContent());
        flowJobContentMapper.insert(flowJobContent);

        //工作流JOB
        FlowJob flowJob = new FlowJob();
        flowJob.setFlowJobId(flowJobId);
        flowJob.setTaskId(projectTask.getTaskId());
        flowJob.setCornStatus(projectTask.getCornStatus());
        flowJob.setStatus(TaskStatusEnum.TO_BE_ALLOCATED.getStatus());
        flowJob.setStartDate(projectTask.getStartDate());
        flowJob.setEndDate(projectTask.getEndDate());
        flowJob.setCorn(projectTask.getCorn());
        flowJob.setFailurePolicy(projectTask.getFailurePolicy());
        flowJob.setNextExecuteTime(CronCalculator.calculator(projectTask.getCorn()));
        flowJob.setExecuteCycle(CronCalculator.cronCycle(projectTask.getCorn()));
        flowJob.setEndDate(projectTask.getEndDate());
        flowJob.setAllowDelete(projectTask.getAllowDelete());
        flowJob.setAllowEdit(projectTask.getAllowEdit());
        flowJob.setEnable(projectTask.getEnable());
        flowJob.setIsDel(projectTask.getIsDel());
        if (flowJobMapper.exists(new LambdaQueryWrapper<FlowJob>().eq(FlowJob::getFlowJobId, projectTask.getTaskId()))) {
            flowJobMapper.updateById(flowJob);
        } else {
            flowJobMapper.insert(flowJob);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String runFlowTask(ProjectTask projectTask,ProjectTaskFlow taskFlow, List<ProjectTaskFlowNodeVo> flowNodes, List<ProjectTaskFlowSourceTargetNodeVo> sourceTargetNodes, List<ProjectTaskParameter> flowParams) {
        String flowRunJobId = IdBulider.id();
        String flowJobId = projectTask.getTaskId();
        Date planeTime = new Date();
        //--工作流参数
        Map<String, String> parameters = ParameterUtils.parse(flowParams.stream().collect(Collectors.toMap(ProjectTaskParameter::getParamKey, ProjectTaskParameter::getParamValue)));

        for (ProjectTaskFlowNodeVo flowNode : flowNodes) {
            FlowRunJobNode flowRunJobNode = new FlowRunJobNode();
            flowRunJobNode.setFlowRunJobId(flowRunJobId);
            flowRunJobNode.setFlowJobId(flowJobId);
            flowRunJobNode.setTaskId(flowNode.getTaskId());
            flowRunJobNode.setWorkSpaceType(flowNode.getWorkSpaceType());
            flowRunJobNode.setExecuteNodeId(flowNode.getExecuteNodeId());
            flowRunJobNode.setExecuteTaskId(flowNode.getExecuteTaskId());
            flowRunJobNode.setNodeType(flowNode.getNodeType());
            flowRunJobNode.setStatus(TaskStatusEnum.PARENT_RUN.getStatus());
            flowRunJobNode.setPlaneTime(planeTime);
            flowRunJobNode.setSeqIndex(flowNode.getSeqIndex());
            if (StringUtils.isNotBlank(flowNode.getScriptContent())) {
                String scriptContent = Expression.parse(flowNode.getScriptContent(), parameters);
                flowRunJobNode.setScriptContent(scriptContent);
            }
            flowRunJobNode.setDbSourceId(flowNode.getDbSourceId());
            flowRunJobNodeMapper.insert(flowRunJobNode);
        }

        for (ProjectTaskFlowSourceTargetNodeVo nodeVo : sourceTargetNodes) {
            //工作流依懒Node
            FlowRunJobPnode flowRunJobPnode = new FlowRunJobPnode();
            flowRunJobPnode.setFlowRunJobId(flowRunJobId);
            flowRunJobPnode.setFlowJobId(flowJobId);
            flowRunJobPnode.setTaskId(nodeVo.getTaskId());
            flowRunJobPnode.setExecuteNodeId(nodeVo.getExecuteNodeId());
            flowRunJobPnode.setExecuteTaskId(nodeVo.getExecuteTaskId());
            flowRunJobPnode.setSourceNodeId(nodeVo.getSourceNodeId());
            flowRunJobPnode.setSourceTaskId(nodeVo.getSourceTaskId());
            flowRunJobPnode.setFailurePolicy(projectTask.getFailurePolicy());
            flowRunJobPnode.setNextNodeStatus(TaskNextNodeStatusEnum.NEXT_NODE_STATUS_0.getStatus());
            flowRunJobPnode.setStatus(TaskStatusEnum.PARENT_RUN.getStatus());
            if (nodeVo.getNodeType() == NodeTypeEnum.START_NODE.getNodeType()) {
                flowRunJobPnode.setStatus(TaskStatusEnum.SUCCED.getStatus());
            }
            flowRunJobPnodeMapper.insert(flowRunJobPnode);
        }

        FlowRunJobContent flowRunJobContent=new FlowRunJobContent();
        flowRunJobContent.setFlowRunJobId(flowRunJobId);
        flowRunJobContent.setFlowJobId(flowJobId);
        flowRunJobContent.setFlowContent(taskFlow.getFlowContent());
        flowRunJobContentMapper.insert(flowRunJobContent);

        //工作流JOB
        FlowRunJob flowRunJob = new FlowRunJob();
        flowRunJob.setFlowJobId(flowJobId);
        flowRunJob.setFlowRunJobId(flowRunJobId);
        flowRunJob.setTaskId(projectTask.getTaskId());
        flowRunJob.setStatus(TaskStatusEnum.TO_BE_ALLOCATED.getStatus());
        flowRunJob.setFailurePolicy(projectTask.getFailurePolicy());
        flowRunJob.setPlaneTime(planeTime);
        flowRunJob.setExecuteCycle(CronCalculator.cronCycle(projectTask.getCorn()));
        flowRunJob.setAddType(TaskAddTypeEnum.ADD_TYPE_1.getType());
        flowRunJobMapper.insert(flowRunJob);
		return flowRunJobId;
    }
}
