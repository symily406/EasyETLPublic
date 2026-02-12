package com.easy.etl.runTaskJob.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.common.db.kit.HiveKit;
import com.easy.etl.common.db.model.SqlTable;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.datax.model.PreScriptContent;
import com.easy.etl.em.TaskAddTypeEnum;
import com.easy.etl.em.TaskResultEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.service.IBatchProjectLayerTaskWriteService;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo.BatchProjectLayerTaskWriteVo;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.easy.etl.projectTaskEvn.service.IProjectTaskEvnService;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.easy.etl.runTaskJob.dto.RunTaskJobDTO;
import com.easy.etl.runTaskJob.dto.result.JobHeader;
import com.easy.etl.runTaskJob.dto.result.JobResult;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.easy.etl.runTaskJob.vo.RunTaskJobVo;
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
import java.util.*;


/**
 * 任务运行-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/runTaskJob")
public class RunTaskJobController extends BaseController {

    @Autowired
    private IRunTaskJobService runTaskJobService;
    @Autowired
    private IProjectTaskService projectTaskService;
    @Autowired
    private IDbSourceService dbSourceService;

    @Autowired
    private IProjectTaskParameterService projectTaskParameterService;
    @Autowired
    private IProjectTaskEvnService projectTaskEvnService;

    @Autowired
    private IBatchProjectLayerTaskWriteService batchProjectLayerTaskWriteService;

    @SaCheckLogin
    @DebugAnnotation(desc = "任务停止")
    @GetMapping(value = "/jobStop")
    public ApiResult jobStop(@NotNull(message = "主键不能为空") String jobId) {
        runTaskJobService.jobStop(Arrays.asList(jobId));
        return ApiResult.ok("任务停止成功");
    }

    /**
     * 保存或修改
     *
     * @param runTaskJob
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated RunTaskJob runTaskJob) {
        RunTaskJob runnerJob = runTaskJobService.queryRunnerJob(runTaskJob.getTaskId());
        if (!ObjectUtils.isEmpty(runnerJob)) {
            return ApiResult.ok(new Dict().set("jobId", runnerJob.getJobId()).set("isOldRunner", true));
        }

        ProjectTask projectTask = projectTaskService.getById(runTaskJob.getTaskId());
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }


        Map<String, String> parameters = projectTaskParameterService.parameters(projectTask.getTaskId());
        boolean hasResult = false;
        if (Integer.parseInt(projectTask.getWorkSpaceType()) == 2) {
            DbSource dbSource = dbSourceService.getById(runTaskJob.getDbSourceId());
            if (ObjectUtils.isEmpty(dbSource)) {
                return ApiResult.fail("数据源错误");
            }
            String scriptContent = Expression.parse(runTaskJob.getScriptContent(), parameters);
            SqlTable sqlTable = DbKit.parseSqlTables(DbKit.removeComments(scriptContent), dbSource.getJdbcDrive());
            if (!sqlTable.isSucced()) {
                return ApiResult.fail(sqlTable.getMessage());
            }
            hasResult = DbKit.sqlHasResult(sqlTable.getTables());
            runTaskJob.setScriptContent(scriptContent);
        }
        ProjectTaskEvn projectTaskEvn = projectTaskEvnService.getById(projectTask.getTaskId());
        if (!ObjectUtils.isEmpty(projectTaskEvn)) {
            runTaskJob.setEvnContent(projectTaskEvn.getEvnContent());
        }

        runTaskJob.setJobId(IdUtil.getSnowflakeNextIdStr());
        runTaskJob.setProjectId(projectTask.getProjectId());
        runTaskJob.setTaskId(projectTask.getTaskId());
        runTaskJob.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
        runTaskJob.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
        runTaskJob.setWorkSpaceType(projectTask.getWorkSpaceType());
        runTaskJob.setTaskFolderId(projectTask.getTaskFolderId());
        if (Integer.parseInt(projectTask.getWorkSpaceType()) == 1) {
            String scriptContent = Expression.parse(projectTaskService.createDataxJson(projectTask.getTaskId(), 1, 1), parameters);
            runTaskJob.setScriptContent(scriptContent);
        }
        runTaskJob.setAddType(TaskAddTypeEnum.ADD_TYPE_1.getType());
        runTaskJob.setStatus(TaskStatusEnum.TO_BE_ALLOCATED.getStatus());
        runTaskJob.setIsRetry(0);
        runTaskJob.setRunNum(0);
        runTaskJob.setNextRunTime(new Date());
        runTaskJob.setHasResult(hasResult == true ? TaskResultEnum.HAS_RESULT.getStatus() : TaskResultEnum.NO_RESULT.getStatus());

        //如果是同步任务
        if (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(projectTask.getWorkSpaceType())) == WorkSpaceTypeEnum.SYNC) {
            BatchProjectLayerTaskWriteVo write = batchProjectLayerTaskWriteService.queryBatchProjectLayerTaskWrite(projectTask.getTaskId());
            if (!ObjectUtils.isEmpty(write) && StringUtils.isNotBlank(write.getPt())) {
                PreScriptContent preScriptContent = new PreScriptContent();
                preScriptContent.setDbSourceId(write.getDbSourceId());
                preScriptContent.setJdbcUrl(write.getJdbcUrl());
                preScriptContent.setJdbcDrive(write.getJdbcDrive());
                preScriptContent.setUserName(write.getUserName());
                preScriptContent.setPassword(write.getPassword());
                preScriptContent.setScript(Expression.parse(HiveKit.createPtSql(write.getDbCatalog(), write.getDbSchema(), write.getTableName(), write.getPt()), parameters));
                runTaskJob.setPreScriptContent(JsonKit.toJsonString(preScriptContent));
            }
        }

        if (runTaskJobService.saveOrUpdateRunTaskJob(runTaskJob)) {
            return ApiResult.ok(new Dict().set("jobId", runTaskJob.getJobId()).set("isOldRunner", false));
        }
        return ApiResult.fail("添加运行任务失败");
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行-分页查询")
    @GetMapping(value = "/queryPage")
    public ApiResult<IPage<RunTaskJobVo>> queryPage(Query query, RunTaskJobDTO param) {
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
        String orderColumn = "D.ADD_TIME";

        if (StringUtils.isNotBlank(param.getOrderColumn())) {
            switch (param.getOrderColumn()) {

                case "cornStatusDesc":
                    orderColumn = "D.CORN_STATUS";
                    break;
                case "corn":
                    orderColumn = "D.CORN";
                    break;
                case "workSpaceTypeDesc":
                    orderColumn = "D1.VALUE";
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
                case "readRecords":
                    orderColumn = "D.READ_RECORDS";
                    break;
                case "readWriteFailures":
                    orderColumn = "D.READ_WRITE_FAILURES";
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

        page = runTaskJobService.queryRunTaskJobPage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 根据主键返回
     *
     * @param jobId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<RunTaskJob> getById(@NotNull(message = "主键不能为空") String jobId) {
        return ApiResult.ok(runTaskJobService.getById(jobId));
    }

    /**
     * 根据主键删除
     *
     * @param jobId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行-根据主键删除")
    @GetMapping(value = "/deleteById")
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String jobId) {
        runTaskJobService.removeById(jobId);
        return ApiResult.ok();
    }

    /**
     * 移除运行结果
     *
     * @param jobId
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "返回任务结果")
    @GetMapping(value = "/removeTaskJobResult")
    public ApiResult removeTaskJobResult(@NotNull(message = "任务ID不能为空") String taskId, @NotNull(message = "任务ID不能为空") String jobId) {
        return ApiResult.ok(runTaskJobService.removeTaskJobResult(jobId, taskId));
    }


    /**
     * 根据taskID返回有运行结果的任务
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "返回任务结果")
    @GetMapping(value = "/queryTaskJobByTaskId")
    public ApiResult queryTaskJobByTaskId(@NotNull(message = "任务ID不能为空") String taskId) {
        return ApiResult.ok(runTaskJobService.queryTaskJobByTaskId(taskId));
    }


    /**
     * 返回任务结果
     *
     * @param jobId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "返回任务结果")
    @GetMapping(value = "/queryJobResult")
    public ApiResult queryJobResult(@NotNull(message = "任务ID不能为空") String jobId) {
        String result = runTaskJobService.queryJobResult(jobId);
        RunTaskJob runTaskJob = runTaskJobService.getById(jobId);
        if (ObjectUtils.isEmpty(runTaskJob)) {
            return ApiResult.ok(20000, "无结果", null);
        }
        String runSql = DbKit.removeComments(runTaskJob.getScriptContent());
        if (StringUtils.isNotEmpty(result)) {
            List<Entity> list = JsonKit.parseList(result, Entity.class);
            if (ObjectUtils.isEmpty(list)) {
                return ApiResult.ok(new JobResult(runSql, runTaskJob.getUsageTime()));
            }
            Entity entity = list.get(0);
            List<JobHeader> headers = new ArrayList<>();
            for (Map.Entry<String, Object> entry : entity.entrySet()) {
                headers.add(new JobHeader(entry.getKey(), entry.getKey(), "180"));
            }
            return ApiResult.ok(new JobResult(list, headers, runSql, runTaskJob.getUsageTime()));
        }
        return ApiResult.ok(new JobResult(runSql, runTaskJob.getUsageTime()));
    }
}
