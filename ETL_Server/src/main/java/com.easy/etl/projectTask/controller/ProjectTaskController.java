package com.easy.etl.projectTask.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.etl.projectDbSource.dto.ProjectDbSourceDTO;
import com.easy.etl.projectDbSource.service.IProjectDbSourceService;
import com.easy.etl.projectDbSource.vo.ProjectDbSourceVo;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskFolderDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskFolder;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTask.vo.ProjectTaskTreeVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import com.easy.etl.projectTask.vo.TaskTreeVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 项目任务-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTask")
public class ProjectTaskController extends BaseController {

    @Autowired
    private IProjectTaskService projectTaskService;
    @Autowired
    private IProjectDbSourceService projectDbSourceService;

    @Autowired
    private IProjectTaskParameterService projectTaskParameterService;

    @Autowired
    private IConfService confService;

    /**
     * 保存或修改
     *
     * @param projectTask
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated ProjectTask projectTask) {
        if (projectTaskService.saveOrUpdateProjectTask(projectTask)) {
            return ApiResult.ok(projectTask);
        }
        return ApiResult.fail("更新失败");
    }

    /**
     * 保存任务
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-保存任务")
    @PostMapping(value = "/saveTask")
    public ApiResult saveTask(ProjectTaskDTO projectTaskDTO, String schedue, String scriptContent, String taskParameters, String evnContent, int taskStep) {
        ProjectTask projectTask = projectTaskService.getById(projectTaskDTO.getTaskId());
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        projectTask.setCorn(projectTaskDTO.getCorn());
        projectTask.setCornStatus(projectTaskDTO.getCornStatus());
        projectTask.setStartDate(projectTaskDTO.getStartDate());
        projectTask.setEndDate(projectTaskDTO.getEndDate());
        projectTask.setDbSourceId(projectTaskDTO.getDbSourceId());
        List<ProjectTaskParameter> projectTaskParameter = JsonKit.parseList(taskParameters, ProjectTaskParameter.class);
        projectTaskParameter.stream().forEach(v -> {
            v.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
            v.setTaskFolderId(projectTask.getTaskFolderId());
        });
        ProjectTaskSchedue taskSchedue = JsonKit.parseObject(schedue, ProjectTaskSchedue.class);
        if (projectTaskService.saveTask(projectTask, taskSchedue, scriptContent, projectTaskParameter, evnContent, taskStep)) {
            return ApiResult.ok();
        }
        return ApiResult.fail("更新失败");
    }


    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-分页查询")
    @GetMapping(value = "/queryPage")
    public ApiResult<IPage<ProjectTaskVo>> queryPage(Query query, ProjectTaskDTO param) {

        param.setOrderBy("D.SHOW_ORDER DESC,D.ADD_TIME DESC");
        param.setNoWorkSpaceTypes(CollUtil.newArrayList("5"));
        page = projectTaskService.queryProjectTaskPage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务查询")
    @GetMapping(value = "/queryTask")
    public ApiResult<List<ProjectTaskVo>> queryTask(ProjectTaskDTO param) {
        if (StringUtils.isEmpty(param.getProjectId())) {
            return ApiResult.fail("项目ID不能为空");
        }
        if (StringUtils.isEmpty(param.getKeyword())) {
            return ApiResult.fail("请输入关键字");
        }
        List<ProjectTaskVo> page = projectTaskService.queryProjectTaskAll(param);
        return ApiResult.ok(page);
    }

    /**
     * 项目任务树
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务树")
    @GetMapping(value = "/queryProjectTree")
    public ApiResult<TaskTreeVo> queryProjectTree(ProjectTaskDTO param, ProjectMemberDTO memberDTO, ProjectTaskFolderDTO taskFolderDTO) {
        if (StringUtils.isEmpty(param.getProjectId())) {
            return ApiResult.fail("项目ID不能为空");
        }
        TaskTreeVo vo = projectTaskService.queryProjectTaskTree(param, memberDTO, taskFolderDTO);
        return ApiResult.ok(vo);
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "引用的数据源-全量-任务下接使用")
    @GetMapping(value = "/queryProjectDbSource")
    public ApiResult<List<ProjectDbSourceVo>> queryPage(ProjectDbSourceDTO param) {
        List<ProjectDbSourceVo> list = projectDbSourceService.queryProjectDbSourceAll(param);
        return ApiResult.ok(list);
    }

    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<ProjectTaskVo> getById(@NotNull(message = "主键不能为空") String taskId) {
        ConfVo confVo = confService.getConf();
        ProjectTaskVo projectTaskVo = projectTaskService.queryProjectTaskById(taskId);
        if (ObjectUtils.isEmpty(projectTaskVo)) {
            return ApiResult.fail("任务不存在");
        }
        projectTaskVo.setIsEnableCluster(confVo.getConfBase().getIsEnableCluster());
        return ApiResult.ok(projectTaskVo);
    }

    /**
     * 根据主键删除
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-根据主键删除")
    @GetMapping(value = "/deleteById")
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String taskId) {
        ProjectTask projectTask = projectTaskService.getById(taskId);
        if (ObjectUtils.isEmpty(projectTask)) {
            return ApiResult.fail("任务不存在");
        }
        projectTaskService.removeById(taskId);
        return ApiResult.ok(projectTask);
    }

    /**
     * 根据主键删除
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-根据主键删除")
    @GetMapping(value = "/createDataxJson")
    public ApiResult createDataxJson(@NotNull(message = "主键不能为空") String taskId) {
        Map<String, String> parameters = projectTaskParameterService.parameters(taskId);
        String scriptContent = Expression.parse(projectTaskService.createDataxJson(taskId, 0, 0), parameters);
        return ApiResult.ok(scriptContent);
    }

}
