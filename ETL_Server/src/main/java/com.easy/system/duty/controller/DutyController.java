package com.easy.system.duty.controller;


import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.easy.system.duty.dto.DutyDTO;
import com.easy.system.duty.entity.Duty;
import com.easy.system.duty.service.IDutyService;
import com.easy.system.duty.vo.DutyVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  岗位管理-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/duty")
public class DutyController extends BaseController {

	@Autowired
	private IDutyService dutyService;


	/**
	 * 保存或修改
	 * @param duty
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "岗位管理-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:duty:add","admin:system:duty:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Duty duty) {
		if(dutyService.saveOrUpdateDuty(duty)) {
			return ApiResult.ok(duty);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 岗位列表
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "岗位列表")
	@GetMapping(value = "/queryDutys")
	public ApiResult<List<DutyVo>> queryDutys(DutyDTO param) {
		List<DutyVo> list = dutyService.queryDutyAll(param);
		return ApiResult.ok(list);
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "岗位管理-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:duty:queryPage"})
	public ApiResult<IPage<DutyVo>> queryPage(Query query,DutyDTO param) {
		page = dutyService.queryDutyPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param dutyId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "岗位管理-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:duty:getById" })
	public ApiResult<Duty> getById(@NotEmpty(message = "主键不能为空") String dutyId) {
		return ApiResult.ok(dutyService.getById(dutyId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param dutyId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "岗位管理-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:duty:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String dutyId) {
		dutyService.removeById(dutyId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param dutyIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "岗位管理-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  ={ "admin:system:duty:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String dutyIds) {
		dutyService.removeBatchByIds(Arrays.asList(StringUtils.split(dutyIds, ",")));
		return ApiResult.ok();
	}
}
