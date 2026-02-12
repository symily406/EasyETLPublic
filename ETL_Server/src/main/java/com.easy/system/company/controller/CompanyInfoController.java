package com.easy.system.company.controller;


import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.easy.system.company.entity.CompanyInfo;
import com.easy.system.company.service.ICompanyInfoService;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;



/**
 *  企业简介-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/companyInfo")
public class CompanyInfoController extends BaseController {

	@Autowired
	private ICompanyInfoService companyInfoService;



	/**
	 * 根据主键返回
	 *
	 * @param companyId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业简介-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:company:getById" })
	public ApiResult<CompanyInfo> getById(@NotEmpty(message = "主键不能为空") String companyId) {
		return ApiResult.ok(companyInfoService.getById(companyId));
	}
}
