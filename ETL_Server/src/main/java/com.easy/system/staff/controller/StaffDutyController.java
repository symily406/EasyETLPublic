package com.easy.system.staff.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.easy.system.staff.dto.StaffDutyDTO;
import com.easy.system.staff.service.IStaffDutyService;
import com.easy.system.staff.vo.StaffDutyVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;



/**
 *  用户岗位-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/staffDuty")
public class StaffDutyController extends BaseController {

	@Autowired
	private IStaffDutyService staffDutyService;


	/**
	    * 用户岗位
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户岗位")
	@GetMapping(value = "/queryDutys")
	public ApiResult<List<StaffDutyVo>> queryDutys(StaffDutyDTO param) {
		List<StaffDutyVo> list = staffDutyService.queryStaffDutyAll(param);
		return ApiResult.ok(list);
	}

}
