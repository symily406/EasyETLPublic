package com.easy.etl.runTaskJob.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.core.init.Config;
import com.easy.etl.datax.report.RunnerReport;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.runTaskJob.dto.RunTaskJobLogDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.model.DataTransferStats;
import com.easy.etl.runTaskJob.model.LogInfo;
import com.easy.etl.runTaskJob.service.IRunTaskJobLogService;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.easy.etl.runTaskJob.vo.RunTaskJobLogVo;
import com.easy.etl.machine.model.Sys;
import com.easy.etl.rabbitmq.task.TaskJobResult;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.JwtKit;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Login;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 任务运行日志-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/runTaskJobLog")
public class RunTaskJobLogController extends BaseController {

    @Autowired
    private IRunTaskJobLogService runTaskJobLogService;
    @Autowired
    private IRunTaskJobService runTaskJobService;

    /**
     * 查询运行日志
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "查询正在运行的JOB")
    @GetMapping(value = "/queryRunnerJob")
    public ApiResult queryRunnerJob(@NotNull(message = "任务编码不能为空") String taskId) {
        return ApiResult.ok(runTaskJobService.queryRunnerJob(taskId));
    }


    /**
     * 查询运行日志
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行日志-查询运行日志")
    @GetMapping(value = "/queryJobLogInfo")
    public ApiResult<Dict> queryJobLogInfo(@NotNull(message = "任务编码不能为空") String jobId) {
        LambdaQueryWrapper<RunTaskJob> queryJob = new LambdaQueryWrapper<>();
        queryJob.select(RunTaskJob::getTaskId, RunTaskJob::getJobId, RunTaskJob::getStatus, RunTaskJob::getHasResult, RunTaskJob::getEndTime);
        queryJob.eq(RunTaskJob::getJobId, jobId);
        List<RunTaskJob> jobs = runTaskJobService.list(queryJob);
        if (ObjectUtils.isEmpty(jobs)) {
            return ApiResult.fail("任务不存在");
        }
        RunTaskJobLogDTO param = new RunTaskJobLogDTO();
        param.setJobId(jobId);
        List<RunTaskJobLogVo> list = runTaskJobLogService.queryRunTaskJobLogAll(param);
        return ApiResult.ok(new Dict().set("job", jobs.get(0)).set("logInfo", list));
    }

    @DebugAnnotation(desc = "datax日志提交")
    @PostMapping(value = "/datax/{token}")
    public ApiResult dataxJobLog(@RequestBody RunnerReport report, @PathVariable String token) {
        if (StringUtils.isEmpty(token)) {
            return ApiResult.ok();
        }
        try {
            if (JwtKit.verify(GlobalConstant.dataxClaimName, token, GlobalConstant.dataxSalt)) {
                runTaskJobLogService.saveOrUpdateRunTaskJobLogSync(report);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.ok();
    }

    @PostMapping(value = "/sqlExecute/{token}")
    public ApiResult sqlExecute(@RequestBody String log, @PathVariable String token) {
        if (StringUtils.isEmpty(token)) {
            return ApiResult.ok();
        }
        if (StringUtils.isEmpty(log)) {
            return ApiResult.ok();
        }
        if (!StringUtils.equals(token, Config.token)) {
            return ApiResult.fail("token验证失败");
        }
        if (StringUtils.indexOf(log, "jobId") == -1) {
            return ApiResult.ok();
        }
        TaskJobResult jobResult = JsonKit.parseObject(log, TaskJobResult.class);
        Integer cacheResult = StringUtils.isEmpty(jobResult.getResult()) ? 0 : 1;
        LogInfo logInfo = new LogInfo();
        BeanUtils.copyProperties(jobResult, logInfo);
        logInfo.setDate(DateUtil.format(jobResult.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
        runTaskJobLogService.saveOrUpdateRunTaskJobLog(logInfo, cacheResult);
        return ApiResult.ok();
    }


    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行日志-分页查询")
    @GetMapping(value = "/queryAll")
    public ApiResult<List<RunTaskJobLogVo>> queryAll(RunTaskJobLogDTO param) {
        List<RunTaskJobLogVo> list = runTaskJobLogService.queryRunTaskJobLogAll(param);
        return ApiResult.ok(list);
    }

    /**
     * 根据主键返回
     *
     * @param logId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行日志-根据主键返回")
    @GetMapping(value = "/getById")
    @SaCheckPermission(value = {"admin:etl:runTaskJobLog:getById"})
    public ApiResult<RunTaskJobLog> getById(@NotNull(message = "主键不能为空") String logId) {
        return ApiResult.ok(runTaskJobLogService.getById(logId));
    }

    /**
     * 根据主键删除
     *
     * @param logId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务运行日志-根据主键删除")
    @GetMapping(value = "/deleteById")
    @SaCheckPermission(value = {"admin:etl:runTaskJobLog:deleteById"})
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String logId) {
        runTaskJobLogService.removeById(logId);
        return ApiResult.ok();
    }

}
