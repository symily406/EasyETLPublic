package com.easy.etl.projectTask.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.service.IProjectTaskSchedueService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * 项目任务定时-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskSchedue")
public class ProjectTaskSchedueController extends BaseController {

    @Autowired
    private IProjectTaskSchedueService projectTaskSchedueService;


    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务定时-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<ProjectTaskSchedue> getById(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(projectTaskSchedueService.getById(taskId));
    }


}
