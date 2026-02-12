package com.easy.etl.projectTask.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskFolder;
import com.easy.etl.projectTask.service.IProjectTaskFolderService;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * 项目任务目录-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskFolder")
public class ProjectTaskFolderController extends BaseController {

    @Autowired
    private IProjectTaskFolderService projectTaskFolderService;

    @Autowired
    private IProjectTaskService projectTaskService;


    /**
     * 保存或修改
     *
     * @param projectTaskFolder
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务目录-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated ProjectTaskFolder projectTaskFolder) {
        projectTaskFolder.setFolderName(StrUtil.trim(projectTaskFolder.getFolderName()));
        if (projectTaskFolderService.count(new QueryWrapper<ProjectTaskFolder>().lambda().eq(ProjectTaskFolder::getTaskFolderPid, projectTaskFolder.getTaskFolderPid()).eq(ProjectTaskFolder::getFolderName, projectTaskFolder.getFolderName())) > 1) {
            return ApiResult.fail("名称相同文件夹已存在");
        }
        if (projectTaskFolderService.saveOrUpdate(projectTaskFolder)) {
            return ApiResult.ok(projectTaskFolder);
        }
        return ApiResult.fail("更新失败");
    }


    /**
     * 根据主键删除
     *
     * @param taskFolderId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务目录-根据主键删除")
    @GetMapping(value = "/deleteById")
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String taskFolderId) {
        if (projectTaskFolderService.count(new QueryWrapper<ProjectTaskFolder>().lambda().eq(ProjectTaskFolder::getTaskFolderPid, taskFolderId).eq(ProjectTaskFolder::getIsDel, 1)) > 0) {
            return ApiResult.fail("当前文件夹下有子节点,删除失败!");
        }

        if (projectTaskService.count(new LambdaQueryWrapper<ProjectTask>().eq(ProjectTask::getTaskFolderId, taskFolderId).eq(ProjectTask::getIsDel, 1)) > 0) {
            return ApiResult.fail("当前文件夹下有子节点,删除失败!");
        }

        ProjectTaskFolder folder = projectTaskFolderService.getById(taskFolderId);
        if (ObjectUtils.isEmpty(folder)) {
            return ApiResult.fail("文件夹不存在");
        }
        projectTaskFolderService.removeById(taskFolderId);
        return ApiResult.ok(folder);
    }

    /**
     * 根据主键返回
     *
     * @param taskFolderId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务目录-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult getById(@NotNull(message = "主键不能为空") String taskFolderId) {
        ProjectTaskFolder folder = projectTaskFolderService.getById(taskFolderId);
        if (ObjectUtils.isEmpty(folder)) {
            return ApiResult.fail("文件夹不存在");
        }
        return ApiResult.ok(folder);
    }

}
