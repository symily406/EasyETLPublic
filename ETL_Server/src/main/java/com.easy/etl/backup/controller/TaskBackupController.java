package com.easy.etl.backup.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.backup.dto.ProjectTaskBackupDTO;
import com.easy.etl.backup.entity.*;
import com.easy.etl.backup.service.ITaskBackupService;
import com.easy.etl.backup.vo.ProjectTaskBackupVo;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity.BatchProjectLayerTaskWriteField;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.vo.BatchProjectLayerTaskWriteFieldVo;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import com.easy.etl.projectTaskEvn.entity.ProjectTaskEvn;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlow;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
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

import java.util.List;
import java.util.stream.Collectors;


/**
 * 项目任务备份-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskBackup")
public class TaskBackupController extends BaseController {

    @Autowired
    private ITaskBackupService taskBackupService;

    @Autowired
    private IConfService confService;

    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务备份-回滚")
    @GetMapping(value = "/rollBack")
    public ApiResult rollBack(String taskId) {
        if (taskBackupService.rollBack(taskId)) {
            return ApiResult.ok("回滚成功");
        }
        return ApiResult.fail("回滚失败");
    }


    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务备份-列表")
    @GetMapping(value = "/queryBackups")
    public ApiResult<List<ProjectTaskBackupVo>> queryBackups(String backupTaskId) {
        ProjectTaskBackupDTO dto = new ProjectTaskBackupDTO();
        dto.setBackupTaskId(backupTaskId);
        List<ProjectTaskBackupVo> list = taskBackupService.backups(dto);
        return ApiResult.ok(list);
    }


    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<ProjectTaskVo> getById(@NotNull(message = "主键不能为空") String taskId) {
        ConfVo confVo = confService.getConf();
        ProjectTaskVo projectTaskVo = taskBackupService.queryProjectTaskBackupById(taskId);
        if (ObjectUtils.isEmpty(projectTaskVo)) {
            return ApiResult.fail("任务不存在");
        }
        projectTaskVo.setIsEnableCluster(confVo.getConfBase().getIsEnableCluster());
        return ApiResult.ok(projectTaskVo);
    }

    @SaCheckLogin
    @DebugAnnotation(desc = "项目参数")
    @GetMapping(value = "/queryParametersBackupAll")
    public ApiResult<List<ProjectTaskParameterBackup>> queryAll(ProjectTaskDTO param) {
        List<ProjectTaskParameterBackup> list = taskBackupService.queryParametersBackupByTaskId(param);
        return ApiResult.ok(list);
    }

    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务定时-根据主键返回")
    @GetMapping(value = "/queryProjectTaskSchedueBackupById")
    public ApiResult<ProjectTaskSchedueBackup> queryProjectTaskSchedueBackupById(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(taskBackupService.queryProjectTaskSchedueBackupById(taskId));
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目任务依懒-分页查询")
    @GetMapping(value = "/queryProjectTaskLazyBackupPage")
    public ApiResult<IPage<ProjectTaskLazyVo>> queryProjectTaskLazyBackupPage(Query query, ProjectTaskLazyDTO param) {
        page = taskBackupService.queryProjectTaskLazyBackupPage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务写入-根据主键返回")
    @GetMapping(value = "/queryBatchProjectLayerTaskWriteBakeByTaskId")
    public ApiResult<BatchProjectLayerTaskWriteBackup> queryBatchProjectLayerTaskWriteBakeByTaskId(@NotNull(message = "任务ID不能为空") String taskId) {
        BatchProjectLayerTaskWriteBackup taskWrite = taskBackupService.queryBatchProjectLayerTaskWriteBakeByTaskId(taskId);
        return ApiResult.ok(taskWrite);
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务来源字段-根据主键返回")
    @GetMapping(value = "/queryBatchProjectLayerTaskReadFieldBakeAll")
    public ApiResult<List<BatchProjectLayerTaskReadFieldVo>> queryBatchProjectLayerTaskReadFieldBakeAll(@NotNull(message = "主键不能为空") String taskId) {
        BatchProjectLayerTaskReadFieldDTO param = new BatchProjectLayerTaskReadFieldDTO();
        param.setTaskId(taskId);
        return ApiResult.ok(taskBackupService.queryBatchProjectLayerTaskReadFieldBakeAll(param));
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务写入字段-根据主键返回")
    @GetMapping(value = "/queryBatchProjectLayerTaskWriteFieldBackup")
    public ApiResult<List<BatchProjectLayerTaskWriteFieldVo>> queryBatchProjectLayerTaskWriteFieldBackup(@NotNull(message = "主键不能为空") String taskId) {
        List<BatchProjectLayerTaskWriteFieldVo> list = taskBackupService.queryBatchProjectLayerTaskWriteFieldBackup(taskId);
        return ApiResult.ok(list);
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务写入字段-根据主键返回")
    @GetMapping(value = "/queryBatchProjectLayerTaskFieldMapBackupAll")
    public ApiResult<List<BatchProjectLayerTaskFieldMapVo>> queryBatchProjectLayerTaskFieldMapBackupAll(BatchProjectLayerTaskFieldMapDTO param) {
        List<BatchProjectLayerTaskFieldMapVo> list = taskBackupService.queryBatchProjectLayerTaskFieldMapBackupAll(param);
        return ApiResult.ok(list);
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务来源-根据主键返回")
    @GetMapping(value = "/getBatchTaskReadByTaskId")
    public ApiResult<BatchProjectLayerTaskReadBackup> getBatchTaskReadByTaskId(@NotNull(message = "任务ID不能为空") String taskId) {
        BatchProjectLayerTaskReadBackup taskRead = taskBackupService.getBatchTaskReadByTaskId(taskId);
        return ApiResult.ok(taskRead);
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "通道控制-根据主键返回")
    @GetMapping(value = "/queryBatchProjectLayerTaskChannelBackupByTaskId")
    public ApiResult<BatchProjectLayerTaskChannelBackup> queryBatchProjectLayerTaskChannelBackupByTaskId(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(taskBackupService.queryBatchProjectLayerTaskChannelBackupByTaskId(taskId));
    }

    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "任务脚本-根据主键返回")
    @GetMapping(value = "/queryProjectTaskScriptBackupById")
    public ApiResult<ProjectTaskScript> queryProjectTaskScriptBackupById(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(taskBackupService.queryProjectTaskScriptBackupById(taskId));
    }


    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "环境变量-根据主键返回")
    @GetMapping(value = "/queryTaskEvnById")
    public ApiResult<ProjectTaskEvnBackup> queryTaskEvnById(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(taskBackupService.queryTaskEvnById(taskId));
    }

    /**
     * 根据主键返回
     *
     * @param taskId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "项目工作流-根据主键返回")
    @GetMapping(value = "/queryProjectTaskFlowBackupById")
    public ApiResult<ProjectTaskFlowVo> queryProjectTaskFlowBackupById(@NotNull(message = "主键不能为空") String taskId) {
        ProjectTask projectTask = taskBackupService.queryProjectTaskBackupById(taskId);
        ProjectTaskFlowVo projectTaskFlowVo = new ProjectTaskFlowVo();
        ProjectTaskFlow projectTaskFlow = taskBackupService.queryProjectTaskFlowBackupById(taskId);
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        BeanUtil.copyProperties(projectTaskFlow, projectTaskFlowVo);
        projectTaskFlowVo.setCorn(projectTask.getCorn());
        projectTaskFlowVo.setCornStatus(projectTask.getCornStatus());
        projectTaskFlowVo.setStartDate(projectTask.getStartDate());
        projectTaskFlowVo.setEndDate(projectTask.getEndDate());
        return ApiResult.ok(projectTaskFlowVo);
    }
}
