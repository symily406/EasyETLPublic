package com.easy.system.org.controller;


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

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.org.entity.Org;
import com.easy.system.org.service.IOrgService;
import com.easy.system.org.vo.OrgDropDownVo;
import com.easy.system.org.vo.OrgTreeVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  机构-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/org")
public class OrgController extends BaseController {

	@Autowired
	private IOrgService orgService;


	/**
	    * 部门下拉树
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "部门下拉树")
	@GetMapping(value = "/queryOrgDropDown")
	public ApiResult<List<OrgDropDownVo>> queryOrgDropDown(OrgDTO param) {
		List<OrgDropDownVo>  list =orgService.queryOrgDropDown(param);
		return ApiResult.ok(list);
	}


	/**
	 * 保存或修改
	 * @param org
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "机构-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:org:add","admin:system:org:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Org org) {
		if(orgService.saveOrUpdateOrg(org)) {
			return ApiResult.ok(org);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "机构-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:org:queryPage","admin:system:flowable:workflow:process:designer","admin:system:flowable:work:todo:todoList"},mode =SaMode.OR)
	public ApiResult<List<OrgTreeVo>> queryPage(CompanyDTO companyDTO,OrgDTO param) {
		List<OrgTreeVo>  list =orgService.queryOrgAll(companyDTO,param);
		return ApiResult.ok(list);
	}

	/**
	 * 根据主键返回
	 *
	 * @param orgId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "机构-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:org:getById" })
	public ApiResult<Org> getById(@NotEmpty(message = "主键不能为空") String orgId) {
		return ApiResult.ok(orgService.getById(orgId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param orgId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "机构-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  ={ "admin:system:org:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String orgId) {
		orgService.removeById(orgId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param orgIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "机构-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  ={ "admin:system:org:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String orgIds) {
		orgService.removeBatchByIds(Arrays.asList(StringUtils.split(orgIds, ",")));
		return ApiResult.ok();
	}
}
