package com.easy.etl.runTaskJob.controller;


import com.easy.etl.core.controller.BaseController;
import com.easy.etl.flowRunJob.service.IFlowRunJobService;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = "/admin/etl/runTaskManually")
public class RunTaskManuallyController  extends BaseController {
    @Autowired
    private IRunTaskJobService iRunTaskJobService;
    @Autowired
    private IFlowRunJobService flowRunJobService;

    /**
     * 流程任务添加任务列表
     */
    @DebugAnnotation(desc = "流程任务添加任务列表-第一步")
    @GetMapping(value = "/flowJobAddTask")
    public ApiResult flowJobAddTask(){
//        flowRunJobService.flowJobAddTask();
        return  ApiResult.ok();
    }

    @DebugAnnotation(desc = "流程中执行的任务添加待运行列表-第二步")
    @GetMapping(value = "/flowJobAddRunJob")
    public ApiResult flowJobAddRnnJob(){
//        flowRunTaskService.flowJobAddRunJob();
        return  ApiResult.ok();
    }

    @DebugAnnotation(desc = "流程中执行的任务添加待运行列表-第三步")
    @GetMapping(value = "/dealWithFlowTaskStatus")
    public ApiResult dealWithFlowTaskStatus(){
//        flowRunJobService.dealWithFlowTaskStatus();
        return  ApiResult.ok();
    }

    @DebugAnnotation(desc = "添加任务到队列-第四步")
    @GetMapping(value = "/taskPush")
    public ApiResult taskAllot(){
        iRunTaskJobService.taskPush();
        return  ApiResult.ok();
    }
}
