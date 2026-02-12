package com.easy.system.taskSchedule.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.common.exception.util.ServiceExceptionUtil;
import com.easy.etl.core.controller.BaseController;
import com.easy.system.taskSchedule.dto.TaskScheduleDTO;
import com.easy.system.taskSchedule.em.TaskScheduleLevalEnum;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.service.ITaskScheduleService;
import com.easy.system.taskSchedule.vo.TaskScheduleVo;
import javafx.concurrent.Task;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
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



/**
 *  任务调度-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/taskSchedule")
public class TaskScheduleController extends BaseController {

	@Autowired
	private ITaskScheduleService taskScheduleService;

	@SaCheckLogin
	@DebugAnnotation(desc = "手动执行一次任务")
	@GetMapping(value = "/manuallyOnceTaskSchedule")
	@SaCheckPermission(value  = {"admin:system:taskSchedule:manuallyOnceTaskSchedule"})
	public ApiResult manuallyOnceTaskSchedule(@NotNull(message = "任务编号不能为空") String taskScheduleId) {
		TaskSchedule taskSchedule = taskScheduleService.getById(taskScheduleId);
		if (ObjectUtils.isEmpty(taskSchedule)){
			return ApiResult.fail("任务不存在");
		}
		if(taskScheduleService.manuallyOnceTaskSchedule(taskSchedule)){
			return  ApiResult.ok();
		}
		return ApiResult.fail("执行失败");
	}

	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度-起动任务")
	@GetMapping(value = "/addTaskSchedule")
	@SaCheckPermission(value  = {"admin:system:taskSchedule:addTaskSchedule"})
	public ApiResult addTaskSchedule(@NotNull(message = "任务编号不能为空") String taskScheduleId) {
		TaskSchedule taskSchedule = taskScheduleService.getById(taskScheduleId);
		if (ObjectUtils.isEmpty(taskSchedule)){
			return ApiResult.fail("任务不存在");
		}
		taskScheduleService.addTaskSchedule(taskSchedule);
		return  ApiResult.ok();
	}

	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度-任务停止")
	@GetMapping(value = "/cancelTaskSchedule")
	@SaCheckPermission(value  = {"admin:system:taskSchedule:cancelTaskSchedule"})
	public ApiResult cancelTaskSchedule(@NotNull(message = "任务编号不能为空") String taskScheduleId) {
		TaskSchedule taskSchedule = taskScheduleService.getById(taskScheduleId);
		if(ObjectUtils.isEmpty(taskSchedule)){
			return ApiResult.fail("任务不存在");
		}
		if (taskSchedule.getLevel()==TaskScheduleLevalEnum.SYSTEM.getLeval()){
			return ApiResult.fail("系统任务不能停止");
		}
		taskScheduleService.cancelTaskSchedule(taskScheduleId);
		return  ApiResult.ok();
	}



	/**
	 * 保存或修改
	 * @param taskSchedule
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:taskSchedule:add","admin:system:taskSchedule:edit"})
	public ApiResult saveOrUpdate(@Validated TaskSchedule taskSchedule) {
		if (StringUtils.isBlank(taskSchedule.getTaskScheduleId())){
			taskSchedule.setLevel(TaskScheduleLevalEnum.NORMAL.getLeval());
		}
		if(taskScheduleService.saveOrUpdateTaskSchedule(taskSchedule)) {
			return ApiResult.ok(taskSchedule);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:taskSchedule:queryPage"})
	public ApiResult<IPage<TaskScheduleVo>> queryPage(Query query, TaskScheduleDTO param) {
		page = taskScheduleService.queryTaskSchedulePage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param taskScheduleId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:taskSchedule:getById" })
	public ApiResult<TaskSchedule> getById(@NotNull(message = "主键不能为空") String taskScheduleId) {
		return ApiResult.ok(taskScheduleService.getById(taskScheduleId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param taskScheduleId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "任务调度-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:taskSchedule:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String taskScheduleId) {
		TaskSchedule taskSchedule = taskScheduleService.getById(taskScheduleId);
		if(ObjectUtils.isEmpty(taskSchedule)){
			return ApiResult.fail("任务不存在");
		}
		if (taskSchedule.getLevel()==TaskScheduleLevalEnum.SYSTEM.getLeval()||taskSchedule.getLevel()==TaskScheduleLevalEnum.INTERNAL.getLeval()){
			return ApiResult.fail("系统任务和内置任务不能删除");
		}
		taskScheduleService.removeById(taskScheduleId);
		return ApiResult.ok();
	}
}
