package com.easy.etl.projectTaskFlow.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.flow.service.IFlowService;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.service.IFlowRunJobService;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;


@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskFlowRun")
public class ProjectTaskFlowRunController extends BaseController {
    @Autowired
    private IFlowService flowService;
    @Autowired
    private IFlowRunJobService flowRunJobService;

    @Autowired
    private IRunTaskJobService runTaskJobService;

    @DebugAnnotation(desc = "工作流任务停止")
    @GetMapping(value = "/queryRunnerFlow")
    public ApiResult queryRunnerFlow(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(flowRunJobService.queryRunnerFlow(taskId));
    }


    @DebugAnnotation(desc = "工作流任务停止")
    @GetMapping(value = "/stopTaskFlow")
    public ApiResult stopTaskFlow(@NotNull(message = "主键不能为空") String flowRunId) {
        List<String> jobIds = flowService.stopTaskFlow(Arrays.asList(flowRunId));
        runTaskJobService.jobStop(jobIds);
        return ApiResult.ok("任务停止成功", jobIds);
    }

    /**
     * 处理工作流任务脚本
     *
     * @return
     */
    @DebugAnnotation(desc = "更新工作流任务任务状态")
    @GetMapping(value = "/dealWithFlowTaskStatus")
    public ApiResult dealWithFlowTaskStatus() {
        flowService.dealWithFlowTaskStatus();
        return ApiResult.ok();
    }

    /**
     * 添加工作流任务到工作流任务列表
     *
     * @return
     */
    @DebugAnnotation(desc = "添加工作流任务到工作流任务列表")
    @GetMapping(value = "/flowJobAddTask")
    public ApiResult flowJobAddTask() {
        flowService.flowJobAddTask();
        return ApiResult.ok();
    }

    /**
     * 添加工作流任务到运行任务表
     *
     * @return
     */
    @DebugAnnotation(desc = "添加工作流任务到运行任务表")
    @GetMapping(value = "/flowJobAddRunJob")
    public ApiResult flowJobAddRunJob() {
        flowService.flowJobAddRunJob();
        return ApiResult.ok();
    }

    @DebugAnnotation(desc = "添加工作流任务到运行任务表")
    @GetMapping(value = "/taskPush")
    public ApiResult taskPush() {
        runTaskJobService.taskPush();
        return ApiResult.ok();
    }
}
