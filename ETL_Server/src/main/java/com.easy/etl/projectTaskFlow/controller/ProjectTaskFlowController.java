package com.easy.etl.projectTaskFlow.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.em.NodeTypeEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.backup.service.ITaskBackupService;
import com.easy.etl.flowRunJob.dto.FlowRunJobNodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.service.IFlowRunJobNodeService;
import com.easy.etl.flowRunJob.service.IFlowRunJobService;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeVo;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTaskFlow.dto.ProjectTaskFlowDTO;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlow;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import com.easy.etl.projectTaskFlow.model.Flow;
import com.easy.etl.projectTaskFlow.model.FlowEdge;
import com.easy.etl.projectTaskFlow.model.FlowNode;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowNodeService;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowService;
import com.easy.etl.projectTaskFlow.service.IProjectTaskFlowSourceTargetNodeService;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowNodeVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowSourceTargetNodeVo;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.easy.etl.projectTaskScript.service.IProjectTaskScriptService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.xml.soap.Node;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目工作流-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskFlow")
public class ProjectTaskFlowController extends BaseController {

    @Autowired
    private IProjectTaskFlowService projectTaskFlowService;

    @Autowired
    private IProjectTaskFlowNodeService projectTaskFlowNodeService;

    @Autowired
    private IProjectTaskService projectTaskService;
    @Autowired
    private IProjectTaskParameterService projectTaskParameterService;
    @Autowired
    private IProjectTaskFlowSourceTargetNodeService projectTaskFlowSourceTargetNodeService;
    @Autowired
    private IFlowRunJobService flowRunJobService;
    @Autowired
    private IFlowRunJobNodeService flowRunJobNodeService;

    @Autowired
    private ITaskBackupService taskBackupService;

    /**
     * 保存或修改
     *
     * @param projectTaskFlow
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated ProjectTaskFlow projectTaskFlow, ProjectTask task, String taskParameters, String schedue) {
        ProjectTask projectTask = projectTaskService.getById(projectTaskFlow.getTaskId());
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        projectTaskFlow.setTaskFolderId(projectTask.getTaskFolderId());
        projectTaskFlow.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        Flow flow = JsonKit.parseObject(projectTaskFlow.getFlowContent(), Flow.class);
        List<FlowNode> nodes = flow.getNodes();
        if (nodes.isEmpty() || nodes.size() == 0) {
            return ApiResult.fail("请添加节点");
        }
        List<FlowEdge> edges = flow.getEdges();
        if (edges.isEmpty() || edges.size() == 0) {
            return ApiResult.fail("请为节点添加关联关系");
        }
        List<ProjectTaskFlowNode> taskFlowNodes = new ArrayList<>();
        for (FlowNode node : nodes) {
            ProjectTaskFlowNode taskFlowNode = new ProjectTaskFlowNode();
            taskFlowNode.setNodeId(node.getId());
            taskFlowNode.setTaskId(projectTaskFlow.getTaskId());
            taskFlowNode.setExecuteTaskId(node.getProperties().getTaskId());
            taskFlowNode.setNodeType(node.getProperties().getNodeType());
            taskFlowNode.setSeqIndex(node.getIndex());
            taskFlowNodes.add(taskFlowNode);
        }
        List<ProjectTaskFlowSourceTargetNode> taskFlowSourceTargetNodes = new ArrayList<>();
        for (FlowEdge edge : edges) {
            ProjectTaskFlowSourceTargetNode taskFlowSourceTargetNode = new ProjectTaskFlowSourceTargetNode();
            taskFlowSourceTargetNode.setTaskId(projectTaskFlow.getTaskId());
            taskFlowSourceTargetNode.setSourceNodeId(edge.getSourceNodeId());
            taskFlowSourceTargetNode.setTargetNodeId(edge.getTargetNodeId());
            taskFlowSourceTargetNodes.add(taskFlowSourceTargetNode);
        }

        List<ProjectTaskParameter> projectTaskParameters = JsonKit.parseList(taskParameters, ProjectTaskParameter.class);
        projectTaskParameters.stream().forEach(v -> {
            v.setTaskId(projectTaskFlow.getTaskId());
            v.setProjectId(projectTask.getProjectId());
            v.setWorkSpaceType(projectTaskFlow.getWorkSpaceType());
            v.setProjectWorkSpaceLayerRelId(projectTaskFlow.getProjectWorkSpaceLayerRelId());
            v.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
            v.setTaskFolderId(projectTask.getTaskFolderId());
        });
        task.setTaskFolderId(projectTask.getTaskFolderId());

        ProjectTaskSchedue taskSchedue = JsonKit.parseObject(schedue, ProjectTaskSchedue.class);
        taskSchedue.setTaskId(projectTask.getTaskId());
        taskSchedue.setProjectId(projectTask.getProjectId());
        taskSchedue.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
        taskSchedue.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        taskSchedue.setWorkSpaceType(projectTask.getWorkSpaceType());
        taskSchedue.setTaskFolderId(projectTask.getTaskFolderId());
        if (projectTaskFlowService.saveOrUpdateProjectTaskFlow(projectTaskFlow, task, taskSchedue, taskFlowNodes, taskFlowSourceTargetNodes, projectTaskParameters)) {
            return ApiResult.ok(projectTaskFlow);
        }
        return ApiResult.fail("更新失败");
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-分页查询")
    @GetMapping(value = "/queryPage")
    @SaCheckPermission(value = {"admin:etl:projectTaskFlow:queryPage"})
    public ApiResult<IPage<ProjectTaskFlowVo>> queryPage(Query query, ProjectTaskFlowDTO param) {
        page = projectTaskFlowService.queryProjectTaskFlowPage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<ProjectTaskFlowVo> getById(@NotNull(message = "主键不能为空") String taskId) {
        ProjectTask projectTask = projectTaskService.getById(taskId);
        ProjectTaskFlowVo projectTaskFlowVo = new ProjectTaskFlowVo();
        ProjectTaskFlow projectTaskFlow = projectTaskFlowService.getById(taskId);
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        BeanUtil.copyProperties(projectTaskFlow, projectTaskFlowVo);
        projectTaskFlowVo.setCorn(projectTask.getCorn());
        projectTaskFlowVo.setCornStatus(projectTask.getCornStatus());
        projectTaskFlowVo.setStartDate(projectTask.getStartDate());
        projectTaskFlowVo.setEndDate(projectTask.getEndDate());
        return ApiResult.ok(projectTaskFlowVo);
    }

    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-根据主键返回所有节点参数")
    @GetMapping(value = "/queryNodeParameters")
    public ApiResult<List<ProjectTaskParameter>> queryNodeParameters(@NotNull(message = "任务不能为空") String taskIds) {
        ProjectTaskDTO param = new ProjectTaskDTO();
        param.setTaskIds(Arrays.asList(StringUtils.split(taskIds, ",")));
        return ApiResult.ok(projectTaskParameterService.queryParametersByTaskId(param));
    }

    /**
     * 根据主键删除
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-根据主键删除")
    @GetMapping(value = "/deleteById")
    @SaCheckPermission(value = {"admin:etl:projectTaskFlow:deleteById"})
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String taskId) {
        projectTaskFlowService.removeById(taskId);
        return ApiResult.ok();
    }


    /**
     * 根据主键批量删除
     *
     * @param taskIds
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-根据主键批量删除")
    @GetMapping(value = "/batchDeleteById")
    @SaCheckPermission(value = {"admin:etl:projectTaskFlow:batchDeleteById"})
    public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String taskIds) {
        projectTaskFlowService.removeBatchByIds(Arrays.asList(StringUtils.split(taskIds, ",")));
        return ApiResult.ok();
    }


    /**
     * 提交流程
     *
     * @param taskId
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-提交流程")
    @PostMapping(value = "/submitFlowTask")
    public ApiResult submitFlowTask(@NotNull(message = "主键不能为空") String taskId, String remarks) {
        ProjectTask projectTask = projectTaskService.getById(taskId);
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        if (StringUtils.isEmpty(projectTask.getCorn())) {
            return ApiResult.fail("请配置调度时间");
        }
        List<ProjectTaskFlowNodeVo> flowNodes = projectTaskFlowNodeService.queryFlowNodes(taskId);
        List<ProjectTaskFlowNodeVo> startNodes = Optional.of(flowNodes.stream().filter(v -> v.getNodeType() == NodeTypeEnum.START_NODE.getNodeType()).collect(Collectors.toList())).orElse(new ArrayList<>());
        List<ProjectTaskFlowNodeVo> endNodes = Optional.of(flowNodes.stream().filter(v -> v.getNodeType() == NodeTypeEnum.END_NODE.getNodeType()).collect(Collectors.toList())).orElse(new ArrayList<>());
        if (startNodes.size() == 0) {
            return ApiResult.fail("请配置一个开始节点");
        }
        if (startNodes.size() > 1) {
            return ApiResult.fail("流程中只能有一个开始节点");
        }
        if (endNodes.size() == 0) {
            return ApiResult.fail("请配置一个结束节点");
        }
        if (endNodes.size() > 1) {
            return ApiResult.fail("流程中只能有一个结束节点");
        }

        List<ProjectTaskFlowSourceTargetNodeVo> sourceTargetNodes = Optional.of(projectTaskFlowSourceTargetNodeService.queryProjectTaskFlowSourceTargetNodes(taskId)).orElse(new ArrayList<>());
        if (sourceTargetNodes.size() == 0) {
            return ApiResult.fail("请为流程配置一个任务节点");
        }
        ProjectTaskDTO param = new ProjectTaskDTO();
        param.setTaskId(taskId);
        List<ProjectTaskParameter> flowParams = projectTaskParameterService.queryParametersByTaskId(param);

        ProjectTaskFlow taskFlow = projectTaskFlowService.getById(taskId);
        if (projectTaskFlowService.submitFlowTask(projectTask, taskFlow, flowNodes, sourceTargetNodes, flowParams)) {
            //任务备份
            taskBackupService.taskBackup(taskId, remarks);
        }
        return ApiResult.ok();
    }

    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-运行工作流")
    @PostMapping(value = "/runFlowTask")
    public ApiResult runFlowTask(@NotNull(message = "主键不能为空") String taskId) {
        FlowRunJob flowRunJob = flowRunJobService.queryRunnerFlow(taskId);
        if (!ObjectUtils.isEmpty(flowRunJob)) {
            return ApiResult.ok(new Dict().set("flowRunId", flowRunJob.getFlowRunJobId()).set("isOldRunner", true));
        }
        ProjectTask projectTask = projectTaskService.getById(taskId);
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        if (StringUtils.isEmpty(projectTask.getCorn())) {
            return ApiResult.fail("请配置调度时间");
        }
        List<ProjectTaskFlowNodeVo> flowNodes = projectTaskFlowNodeService.queryFlowNodes(taskId);
        List<ProjectTaskFlowNodeVo> startNodes = Optional.of(flowNodes.stream().filter(v -> v.getNodeType() == NodeTypeEnum.START_NODE.getNodeType()).collect(Collectors.toList())).orElse(new ArrayList<>());
        List<ProjectTaskFlowNodeVo> endNodes = Optional.of(flowNodes.stream().filter(v -> v.getNodeType() == NodeTypeEnum.END_NODE.getNodeType()).collect(Collectors.toList())).orElse(new ArrayList<>());
        if (startNodes.size() == 0) {
            return ApiResult.fail("请配置一个开始节点");
        }
        if (startNodes.size() > 1) {
            return ApiResult.fail("流程中只能有一个开始节点");
        }
        if (endNodes.size() == 0) {
            return ApiResult.fail("请配置一个结束节点");
        }
        if (endNodes.size() > 1) {
            return ApiResult.fail("流程中只能有一个结束节点");
        }

        List<ProjectTaskFlowSourceTargetNodeVo> sourceTargetNodes = Optional.of(projectTaskFlowSourceTargetNodeService.queryProjectTaskFlowSourceTargetNodes(taskId)).orElse(new ArrayList<>());
        if (sourceTargetNodes.size() == 0) {
            return ApiResult.fail("请为流程配置一个任务节点");
        }
        ProjectTaskDTO param = new ProjectTaskDTO();
        param.setTaskId(taskId);
        List<ProjectTaskParameter> flowParams = projectTaskParameterService.queryParametersByTaskId(param);
        ProjectTaskFlow taskFlow = projectTaskFlowService.getById(taskId);
        String flowRunId = projectTaskFlowService.runFlowTask(projectTask, taskFlow, flowNodes, sourceTargetNodes, flowParams);
        return ApiResult.ok(new Dict().set("flowRunId", flowRunId).set("isOldRunner", false));
    }

    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-查询工作流运行状态")
    @GetMapping(value = "/queryFlowTaskRunStatus")
    public ApiResult queryFlowTaskRunStatus(@NotNull(message = "项目工作流-查询工作流运行状态") String flowRunId) {
        FlowRunJob flowRunJob = flowRunJobService.getById(flowRunId);
        if (flowRunJob == null) {
            return ApiResult.fail("任务不存在");
        }
        FlowRunJobNodeDTO param = new FlowRunJobNodeDTO();
        param.setFlowRunJobId(flowRunId);
        List<FlowRunJobNodeVo> nodes = flowRunJobNodeService.queryFlowRunJobNodeAll(param);
        //父级节点失败
        List<FlowRunJobNodeVo> parentNodeFails = nodes.stream().filter(v -> v.getStatus() == TaskStatusEnum.PARENT_FAIL.getStatus()).collect(Collectors.toList());
        //开始节点
        FlowRunJobNodeVo startNode = nodes.stream().filter(v -> v.getNodeType() == NodeTypeEnum.START_NODE.getNodeType()).findFirst().orElse(null);
        //结束节点
        FlowRunJobNodeVo endNode = nodes.stream().filter(v -> v.getNodeType() == NodeTypeEnum.END_NODE.getNodeType()).findFirst().orElse(null);

        nodes.removeIf(v -> v.getNodeType() == NodeTypeEnum.START_NODE.getNodeType() || v.getNodeType() == NodeTypeEnum.END_NODE.getNodeType());
        nodes.removeIf(v -> v.getStatus() == TaskStatusEnum.PARENT_RUN.getStatus() || v.getStatus() == TaskStatusEnum.PARENT_FAIL.getStatus());

        List<Dict> nodeDicts = new ArrayList<>();
        nodeDicts.add(new Dict().set("jobRunNodeId", startNode.getJobRunNodeId()).set("flowRunJobId", startNode.getFlowRunJobId()).set("flowJobId", startNode.getFlowJobId()).set("taskId", startNode.getTaskId()).set("executeNodeId", startNode.getExecuteNodeId()).set("executeTaskId", startNode.getExecuteTaskId()).set("status", startNode.getStatus()).set("planeTime", startNode.getPlaneTime()).set("executeTime", startNode.getExecuteTime()).set("completedTime", startNode.getCompletedTime()).set("desc", "开始执行").set("nodeType", startNode.getNodeType()).set("usageTime", startNode.getUsageTime()));
        nodeDicts.addAll(nodes.stream().map(v -> {
            Dict node = new Dict().set("jobRunNodeId", v.getJobRunNodeId()).set("flowRunJobId", v.getFlowRunJobId()).set("flowJobId", v.getFlowJobId()).set("taskId", v.getTaskId()).set("executeNodeId", v.getExecuteNodeId()).set("executeTaskId", v.getExecuteTaskId()).set("status", v.getStatus()).set("planeTime", v.getPlaneTime()).set("jobId", v.getJobId()).set("desc", v.getDesc()).set("nodeType", v.getNodeType()).set("executeTime", v.getExecuteTime()).set("completedTime", v.getCompletedTime()).set("usageTime", v.getUsageTime());
            return node;
        }).collect(Collectors.toList()));
        nodeDicts.addAll(parentNodeFails.stream().map(v -> {
            Dict node = new Dict().set("jobRunNodeId", v.getJobRunNodeId()).set("flowRunJobId", v.getFlowRunJobId()).set("flowJobId", v.getFlowJobId()).set("taskId", v.getTaskId()).set("executeNodeId", v.getExecuteNodeId()).set("executeTaskId", v.getExecuteTaskId()).set("status", v.getStatus()).set("planeTime", v.getPlaneTime()).set("jobId", v.getJobId()).set("desc", v.getDesc()).set("nodeType", v.getNodeType()).set("executeTime", v.getExecuteTime()).set("completedTime", v.getCompletedTime()).set("usageTime", v.getUsageTime());
            return node;
        }).collect(Collectors.toList()));
        nodeDicts.add(new Dict().set("jobRunNodeId", endNode.getJobRunNodeId()).set("flowRunJobId", endNode.getFlowRunJobId()).set("flowJobId", endNode.getFlowJobId()).set("taskId", endNode.getTaskId()).set("executeNodeId", endNode.getExecuteNodeId()).set("executeTaskId", endNode.getExecuteTaskId()).set("status", endNode.getStatus()).set("planeTime", endNode.getPlaneTime()).set("executeTime", endNode.getExecuteTime()).set("completedTime", endNode.getCompletedTime()).set("jobId", endNode.getJobId()).set("desc", endNode.getDesc()).set("nodeType", endNode.getNodeType()).set("usageTime", endNode.getUsageTime()));

        Dict dict = new Dict();
        dict.set("status", flowRunJob.getStatus());
        dict.set("executeTime", flowRunJob.getExecuteTime());
        dict.set("completedTime", flowRunJob.getCompletedTime());
        dict.set("usageTime", flowRunJob.getUsageTime());
        dict.set("nodes", nodeDicts);
        return ApiResult.ok(dict);
    }
}
