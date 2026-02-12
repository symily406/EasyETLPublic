package com.easy.etl.projectTaskScript.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.service.IProjectTaskScriptService;
import com.easy.etl.taskJob.service.ITaskJobService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;


/**
 * 任务脚本-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskScript")
public class ProjectTaskScriptController extends BaseController {

    @Autowired
    private IProjectTaskScriptService projectTaskScriptService;

    @Autowired
    private IProjectTaskService projectTaskService;

    @Autowired
    private IProjectTaskParameterService projectTaskParameterService;
    @Autowired
    private ITaskJobService taskJobService;


    @SaCheckLogin
    @DebugAnnotation(desc = "任务脚本-提交")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@NotNull(message = "任务Id不能为空") String taskId) {
        ProjectTask projectTask = projectTaskService.getById(taskId);
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        //任务参数
        Map<String, String> parameters = projectTaskParameterService.dynamicParameters(taskId);

        ProjectTaskScript projectTaskScript = new ProjectTaskScript();
        projectTaskScript.setTaskId(taskId);
        projectTaskScript.setProjectId(projectTask.getProjectId());
        projectTaskScript.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
        projectTaskScript.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        projectTaskScript.setWorkSpaceType(projectTask.getWorkSpaceType());
        projectTaskScript.setTaskFolderId(projectTask.getTaskFolderId());
        switch (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(projectTask.getWorkSpaceType()))) {
            case SYNC://数据集成
                projectTaskScript.setScriptContent(projectTaskService.createDataxJson(taskId, 1, 0));
                break;
        }
        if (projectTaskScriptService.saveOrUpdateProjectTaskScript(projectTaskScript)) {
            Date endTime = DateUtil.parseDateTime(String.format("%s %s", DateUtil.today(), "23:59:59"));
            return ApiResult.ok(projectTaskScript);
        }
        return ApiResult.fail("脚本提交成功");
    }


    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务脚本-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<ProjectTaskScript> getById(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(projectTaskScriptService.getById(taskId));
    }


}
