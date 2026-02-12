package com.easy.etl.flowRunJob.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.em.NodeTypeEnum;
import com.easy.etl.flowRunJob.dto.FlowRunJobDTO;
import com.easy.etl.flowRunJob.dto.FlowRunJobNodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.service.IFlowRunJobNodeService;
import com.easy.etl.flowRunJob.service.IFlowRunJobService;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeVo;
import com.easy.etl.flowRunJob.vo.FlowRunJobTreeVo;
import com.easy.etl.flowRunJob.vo.FlowRunJobVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 工作流运行JOB-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/flowRunJob")
public class FlowRunJobController extends BaseController {

    @Autowired
    private IFlowRunJobService flowRunJobService;

    @Autowired
    private IFlowRunJobNodeService flowRunJobNodeService;


    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "工作流运行JOB-分页查询")
    @GetMapping(value = "/queryPage")
    @SaCheckPermission(value = {"admin:etl:flowRunJob:queryPage"})
    public ApiResult<IPage<FlowRunJobTreeVo>> queryPage(Query query, FlowRunJobDTO param) {
        IPage<FlowRunJobTreeVo> pageVo = Condition.getPage(query);
        pageVo.setRecords(new ArrayList<>());
        if (param.getUserLeval() != 100 || param.getUserLeval() != 0) {
            param.setProjectMember(param.getUserId());
        }

        String orderType = "DESC";
        if (StringUtils.isNotBlank(param.getOrderType())) {
            switch (param.getOrderType()) {
                case "ascending":
                    orderType = "ASC";
                    break;
                case "descending":
                    orderType = "DESC";
                    break;
            }
        }
        param.setOrderType(orderType);
        String orderColumn = "D.PLANE_TIME,D.ADD_TIME";
        if (StringUtils.isNotBlank(param.getOrderColumn())) {
            switch (param.getOrderColumn()) {
                case "taskName":
                    orderColumn = "D2.TASK_NAME";
                    break;
                case "projectName":
                    orderColumn = "D5.PROJECT_NAME";
                    break;
                case "addTime":
                    orderColumn = "D.ADD_TIME";
                    break;
                case "cornStatusDesc":
                    orderColumn = "D2.CORN_STATUS";
                    break;
                case "statusName":
                    orderColumn = "D.STATUS";
                    break;
                case "usageTime":
                    orderColumn = "D.USAGE_TIME";
                    break;
                case "planeTime":
                    orderColumn = "D.PLANE_TIME";
                    break;
                case "executeTime":
                    orderColumn = "D.EXECUTE_TIME";
                    break;
                case "completedTime":
                    orderColumn = "D.COMPLETED_TIME";
                    break;
                case "failedTaskNum":
                    orderColumn = "D.FAILED_TASK_NUM";
                    break;
            }
        }
        List<String> orderColumns = Arrays.asList(StringUtils.split(orderColumn, ","));
        StringBuilder orderBy = new StringBuilder();
        for (String column : orderColumns) {
            if (StringUtils.isNotBlank(orderBy)) {
                orderBy.append(",");
            }
            orderBy.append(column).append(" ").append(param.getOrderType());
        }
        param.setOrderBy(orderBy.toString());

        IPage<FlowRunJobVo> page = flowRunJobService.queryFlowRunJobPage(Condition.getPage(query), param);
        if (!ObjectUtils.isEmpty(page.getRecords())) {
            FlowRunJobNodeDTO dto = new FlowRunJobNodeDTO();
            dto.setFlowRunJobIds(page.getRecords().stream().map(FlowRunJobVo::getFlowRunJobId).collect(Collectors.toList()));
            dto.setNotInNodeTypes(Arrays.asList(NodeTypeEnum.START_NODE.getNodeType(),NodeTypeEnum.END_NODE.getNodeType()));
            List<FlowRunJobNodeVo> list = flowRunJobNodeService.queryFlowRunJobNodeAll(dto);
            for (FlowRunJobVo vo : page.getRecords()) {
                FlowRunJobTreeVo treeVo = new FlowRunJobTreeVo();
                BeanUtils.copyProperties(vo, treeVo);
                treeVo.setId(vo.getFlowRunJobId());
                treeVo.setLeaf(false);
                treeVo.setChildren(
                        Optional.of(list.stream()
                                        .filter(v -> v.getFlowRunJobId().equals(vo.getFlowRunJobId()))
                                        .collect(Collectors.toList())).
                                orElse(new ArrayList<>())
                                .stream().map(v -> {
                                    FlowRunJobTreeVo treeNodeVo = new FlowRunJobTreeVo();
                                    treeNodeVo.setId(v.getJobRunNodeId());
                                    treeNodeVo.setJobRunNodeId(v.getJobRunNodeId());
                                    treeNodeVo.setFlowRunJobId(v.getFlowRunJobId());
                                    treeNodeVo.setFlowJobId(v.getFlowJobId());
                                    treeNodeVo.setTaskId(v.getExecuteTaskId());
                                    treeNodeVo.setWorkSpaceType(v.getWorkSpaceType());
                                    treeNodeVo.setWorkSpaceTypeDesc(v.getWorkSpaceTypeDesc());
                                    treeNodeVo.setTaskName(v.getTaskName());
                                    treeNodeVo.setProjectId(v.getProjectId());
                                    treeNodeVo.setProjectWorkSpaceLayerRelId(v.getProjectWorkSpaceLayerRelId());
                                    treeNodeVo.setProjectWorkSpaceId(v.getProjectWorkSpaceId());
                                    treeNodeVo.setWorkSpaceType(v.getWorkSpaceType());
                                    treeNodeVo.setExecuteTaskId(v.getExecuteTaskId());
                                    treeNodeVo.setAddTime(v.getAddTime());
                                    treeNodeVo.setStatus(v.getStatus());
                                    treeNodeVo.setExecuteTime(v.getExecuteTime());
                                    treeNodeVo.setPlaneTime(v.getPlaneTime());
                                    treeNodeVo.setCompletedTime(v.getCompletedTime());
                                    treeNodeVo.setStatusName(v.getStatusDesc());
                                    treeNodeVo.setUsageTime(v.getUsageTime());
                                    treeNodeVo.setNodeType(v.getNodeType());
                                    treeNodeVo.setCornStatus(vo.getCornStatus());
                                    treeNodeVo.setProjectName(v.getProjectName());
                                    treeNodeVo.setLeaf(true);
                                    return treeNodeVo;
                                }).collect(Collectors.toList())
                );
                pageVo.getRecords().add(treeVo);
            }
            pageVo.setTotal(page.getTotal());
            pageVo.setPages(page.getPages());
            pageVo.setSize(page.getSize());
            pageVo.setCurrent(page.getCurrent());
        }
        return ApiResult.ok(pageVo);
    }

    /**
     * 根据主键返回
     *
     * @param flowJobId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "工作流运行JOB-根据主键返回")
    @GetMapping(value = "/getById")
    @SaCheckPermission(value = {"admin:etl:flowRunJob:getById"})
    public ApiResult<FlowRunJob> getById(@NotNull(message = "主键不能为空") String flowJobId) {
        return ApiResult.ok(flowRunJobService.getById(flowJobId));
    }
}
