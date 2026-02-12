package com.easy.etl.taskJob.controller;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.backup.service.ITaskBackupService;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.easy.etl.projectTask.service.IProjectTaskLazyService;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.service.IProjectTaskScriptService;
import com.easy.etl.taskJob.dto.TaskJobDTO;
import com.easy.etl.taskJob.entity.TaskJob;
import com.easy.etl.taskJob.service.ITaskJobService;
import com.easy.etl.taskJob.vo.TaskJobVo;
import com.easy.etl.utils.CronCalculator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 待运行任务-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/taskJob")
public class TaskJobController extends BaseController {

    @Autowired
    private ITaskJobService taskJobService;

    @Autowired
    private IProjectTaskService projectTaskService;

    @Autowired
    private IProjectTaskParameterService projectTaskParameterService;

    @Autowired
    private IProjectTaskScriptService projectTaskScriptService;
    @Autowired
    private IProjectTaskLazyService projectTaskLazyService;
    @Autowired
    private ITaskBackupService taskBackupService;
    @Autowired
    private IDbSourceService dbSourceService;

    @SaCheckLogin
    @DebugAnnotation(desc = "任务-提交")
    @PostMapping(value = "/submitJob")
    public ApiResult submitJob(@NotNull(message = "任务Id不能为空") String taskId,String remarks) {
        ProjectTask projectTask = projectTaskService.getById(taskId);
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }

        ProjectTaskScript projectTaskScript = projectTaskScriptService.getById(taskId);
        if (projectTaskScript == null) {
            return ApiResult.fail("请先保存任务再提交");
        }

        //运行时间
        Date startTime = new Date();
        Date endTime = DateUtil.parseDateTime(String.format("%s %s", DateUtil.today(), "23:59:59"));

        //依懒任务
        ProjectTaskLazyDTO lazyDTO = new ProjectTaskLazyDTO();
        lazyDTO.setTaskId(taskId);
        List<ProjectTaskLazyVo> lazyVos = projectTaskLazyService.queryProjectTaskLazyAll(lazyDTO);
        if (!ObjectUtils.isEmpty(lazyVos)) {
            for (ProjectTaskLazyVo vo : lazyVos) {
                vo.setRunTimes(CronCalculator.getAllRunTimes(vo.getCorn(), startTime, endTime));
            }
        }
        //任务参数
        Map<String, String> parameters = projectTaskParameterService.dynamicParameters(taskId);
        if (taskJobService.submitTaskJob(projectTask, projectTaskScript.getScriptContent(), parameters, startTime, endTime, lazyVos)) {
            //任务备份
            taskBackupService.taskBackup(taskId,remarks);
            return ApiResult.ok(projectTaskScript);
        }
        return ApiResult.fail("任务提交失败");
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "待运行任务-分页查询")
    @GetMapping(value = "/queryPage")
    @SaCheckPermission(value = {"admin:etl:taskJob:taskJob:queryPage"})
    public ApiResult<IPage<TaskJobVo>> queryPage(Query query, TaskJobDTO param) {
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
        String orderColumn = "D.CORN,D.ADD_TIME";
        if (StringUtils.isNotBlank(param.getOrderColumn())) {
            switch (param.getOrderColumn()) {
                case "taskName":
                    orderColumn = "D1.TASK_NAME";
                    break;
                case "projectName":
                    orderColumn = "D2.PROJECT_NAME";
                    break;
                case "cornStatusDesc":
                    orderColumn = "D.CORN_STATUS";
                    break;
                case "corn":
                    orderColumn = "D.CORN";
                    break;
                case "workSpaceTypeDesc":
                    orderColumn = "D3.NAME";
                    break;
                case "addTime":
                    orderColumn = "D.ADD_TIME";
                    break;
                case "statusDesc":
                    orderColumn = "D.STATUS";
                    break;
                case "usageTime":
                    orderColumn = "D.USAGE_TIME";
                    break;
                case "startTime":
                    orderColumn = "D.START_TIME";
                    break;
                case "endTime":
                    orderColumn = "D.END_TIME";
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

        if (param.getUserLeval() != 100 || param.getUserLeval() != 0) {
            param.setProjectMember(param.getUserId());
        }
        page = taskJobService.queryTaskJobPage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 根据主键返回
     *
     * @param jobId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "待运行任务-根据主键返回")
    @GetMapping(value = "/getById")
    @SaCheckPermission(value = {"admin:etl:taskJob:taskJob:getById"})
    public ApiResult<TaskJob> getById(@NotNull(message = "主键不能为空") String jobId) {
        return ApiResult.ok(taskJobService.getById(jobId));
    }

    /**
     * 根据主键删除
     *
     * @param jobId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "待运行任务-根据主键删除")
    @GetMapping(value = "/deleteById")
    @SaCheckPermission(value = {"admin:etl:taskJob:taskJob:deleteById"})
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String jobId) {
        taskJobService.removeById(jobId);
        return ApiResult.ok();
    }


    /**
     * 根据主键批量删除
     *
     * @param jobIds
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "待运行任务-根据主键批量删除")
    @GetMapping(value = "/batchDeleteById")
    @SaCheckPermission(value = {"admin:etl:taskJob:taskJob:batchDeleteById"})
    public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String jobIds) {
        taskJobService.removeBatchByIds(Arrays.asList(StringUtils.split(jobIds, ",")));
        return ApiResult.ok();
    }
}
