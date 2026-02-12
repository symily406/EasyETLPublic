package com.easy.system.operationLog.controller;


import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.system.operationLog.dto.OperationLogDTO;
import com.easy.system.operationLog.entity.OperationLog;
import com.easy.system.operationLog.service.IOperationLogService;
import com.easy.system.operationLog.vo.OperationLogVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  操作日志-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/operationLog")
public class OperationLogController extends BaseController {

	@Autowired
	private IOperationLogService operationLogService;


	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "登陆日志-分页查询")
	@GetMapping(value = "/queryLoginPage")
	@SaCheckPermission(value  ={"admin:system:log:login:queryPage"})
	public ApiResult<IPage<OperationLogVo>> queryLoginPage(Query query,OperationLogDTO param) {
		param.setUserLeval(100);
		param.setUserDutyLeval(5);
		param.setLogTypes(Arrays.asList(new Integer[] {1,2}));
		page = operationLogService.queryOperationLogPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "操作日志-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:operationLog:queryPage","admin:system:log:login:queryPage"},mode = SaMode.OR)
	public ApiResult<IPage<OperationLogVo>> queryPage(Query query,OperationLogDTO param) {
		page = operationLogService.queryOperationLogPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param logId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "操作日志-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:operationLog:getById","admin:system:log:login:getById"})
	public ApiResult<OperationLog> getById(OperationLogDTO param) {
		if (StringUtils.isBlank(param.getLogId())) {
			return ApiResult.fail("logId不能为空");
		}
		return ApiResult.ok(operationLogService.queryOperationLogByCondition(param));
	}



	/**
	 * 根据主键批量删除
	 * @param logIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "操作日志-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:operationLog:batchDeleteById","admin:system:log:login:batchDeleteById" })
	public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String logIds) {
		operationLogService.batchDelete(Arrays.asList(StringUtils.split(logIds, ",")));
		return ApiResult.ok();
	}
}
