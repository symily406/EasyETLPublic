package com.easy.system.taskSchedule.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.system.taskSchedule.dto.TaskScheduleLogDTO;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.easy.system.taskSchedule.service.ITaskScheduleLogService;
import com.easy.system.taskSchedule.vo.TaskScheduleLogVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;

import java.util.Arrays;


/**
 *  任务调度日志-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/taskSchedule/logInfo")
public class TaskScheduleLogController extends BaseController {

	@Autowired
	private ITaskScheduleLogService taskScheduleLogService;



	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度日志-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:taskSchedule:info:queryPage"})
	public ApiResult<IPage<TaskScheduleLogVo>> queryPage(Query query, TaskScheduleLogDTO param) {
		page = taskScheduleLogService.queryTaskScheduleLogPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}


	/**
	 * 根据主键批量删除
	 * @param logIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度日志-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:taskSchedule:info:batchDeleteById" })
	public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String logIds) {
		taskScheduleLogService.removeBatchByIds(Arrays.asList(StringUtils.split(logIds, ",")));
		return ApiResult.ok();
	}
}
