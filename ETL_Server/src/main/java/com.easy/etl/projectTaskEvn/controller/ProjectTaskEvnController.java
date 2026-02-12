package com.easy.etl.projectTaskEvn.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.conf.entity.Conf;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.utils.ConfConvert;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.easy.etl.projectTaskEvn.service.IProjectTaskEvnService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * 任务环境变量-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskEvn")
public class ProjectTaskEvnController extends BaseController {

    @Autowired
    private IProjectTaskEvnService projectTaskEvnService;
    @Autowired
    private IProjectTaskService projectTaskService;
    @Autowired
    private IConfService confService;

    /**
     * 保存或修改
     *
     * @param projectTaskEvn
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务环境变量-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated ProjectTaskEvn projectTaskEvn, int taskStep) {
        ProjectTask projectTask = projectTaskService.getById(projectTaskEvn.getTaskId());
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        projectTaskEvn.setId(projectTask.getTaskId());
        if (projectTaskEvnService.saveOrUpdateProjectTaskEvn(projectTaskEvn, taskStep)) {
            return ApiResult.ok(projectTaskEvn);
        }
        return ApiResult.fail("更新失败");
    }


    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务环境变量-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<ProjectTaskEvn> getById(@NotNull(message = "taskId不能为空") String taskId) {
        ConfVo confVo = confService.getConf();
        if (ObjectUtils.isEmpty(confVo)) {
            return ApiResult.fail("请先对系统进行配置");
        }
        ProjectTask projectTask = projectTaskService.getById(taskId);
        if (ObjectUtils.isEmpty(projectTask)) {
            return ApiResult.fail("任务不存在");
        }
        ProjectTaskEvn projectTaskEvn = projectTaskEvnService.getById(taskId);
        if (ObjectUtils.isEmpty(projectTaskEvn)) {
            if (!ObjectUtils.isEmpty(projectTask)) {
                projectTaskEvn = new ProjectTaskEvn();
                projectTaskEvn.setEvnContent(ConfConvert.convertToStr(confVo, projectTask.getWorkSpaceType()));
            }
        }
        if (StringUtils.isBlank(projectTaskEvn.getEvnContent())) {
            if (confVo.getConfBase().getIsEnableCluster() == 1) {
                projectTaskEvn.setEvnContent(ConfConvert.convertToStr(confVo, projectTask.getWorkSpaceType()));
            }
        }
        return ApiResult.ok(projectTaskEvn);
    }
}
