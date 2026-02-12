package com.easy.system.company.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.run.cache.CacheManagement;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.company.dto.CompanyFunctionDTO;
import com.easy.system.company.dto.InitCompanyDTO;
import com.easy.system.company.entity.Company;
import com.easy.system.company.entity.CompanyInfo;
import com.easy.system.company.service.ICompanyFunctionService;
import com.easy.system.company.service.ICompanyService;
import com.easy.system.company.vo.CompanyFunctionVo;
import com.easy.system.company.vo.CompanyVo;
import com.easy.system.menu.dto.MenuDTO;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.service.IMenuService;
import com.easy.system.menu.vo.MenuCompanyVo;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.service.IStaffService;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;



/**
 *  企业-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/company")
public class CompanyController extends BaseController {

	@Autowired
	private ICompanyService companyService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private ICompanyFunctionService companyFunctionService;
	@Autowired
	private IStaffService staffService;

	/**
	 * 初始化企业
	 * @param param
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业- 初始化企业")
	@PostMapping(value = "/initCompany")
	@SaCheckPermission(value = {"admin:system:company:initCompany"})
	public ApiResult initCompany(InitCompanyDTO param) {
		param.setInitSalt(IdUtil.fastSimpleUUID());
		if (StringUtils.isBlank(param.getInitUserName())) {
			return ApiResult.fail("用户名不能为空");
		}
		if (StringUtils.isBlank(param.getInitPassword())) {
			return ApiResult.fail("密码不能为空");
		}
		if (StringUtils.isBlank(param.getInitConfirmPassword())) {
			return ApiResult.fail("确认密码不能为空");
		}
		if (!StringUtils.equals(param.getInitPassword(), param.getInitConfirmPassword())) {
			return ApiResult.fail("两次输入的密码不一至");
		}
		long count=staffService.count(new QueryWrapper<Staff>().lambda().eq(Staff::getUserName, param.getInitUserName()));

		if (count>0) {
			return ApiResult.fail("用户名已存在");
		}
		param.setInitPassword(SecureUtil.md5(param.getInitPassword().concat(param.getInitSalt())));
		companyService.initCompany(param);
		return ApiResult.ok();
	}


	/**
	 * 企业部门下拉选择
	 *
	 * @param param
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-下拉选择接口")
	@GetMapping(value = "/queryCompanyDropDown")
	public ApiResult<List<CompanyVo>> queryCompanyDropDown(CompanyDTO param) {
		List<CompanyVo> list=companyService.queryCompanyDropDown(param);
		return ApiResult.ok(list);
	}


	/**
	 * 企业菜单接口
	 * @param menu
	 * @param menuRole
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-菜单接口")
	@ResponseBody
	@GetMapping(value = "/menuCompanyTree")
	@SaCheckPermission(value  = {"admin:system:company:add","admin:system:company:edit"})
	public ApiResult<Map<String, Object>> menuCompanyTree(MenuDTO menu,MenuRoleDTO menuRole,CompanyFunctionDTO fun) {
		List<CompanyFunctionVo> funs=new ArrayList<>();
		if (StringUtils.isNoneBlank(fun.getCompanyId())) {
			funs= companyFunctionService.queryCompanyFunctionAll(fun);
		}
		menu.setCompanyId(null);
		menuRole.setCompanyId(null);
		List<MenuCompanyVo> menus=menuService.menuCompanyTree(menu, menuRole);
		Map<String, Object> re=new HashMap<>();
		re.put("menus", menus);
		re.put("funs", funs);
		return ApiResult.ok(re);
	}


	/**
	 * 保存或修改
	 * @param company
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:company:add","admin:system:company:edit"}, mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Company company,@Validated CompanyInfo companyInfo,@NotEmpty(message = "请选择企业的功能权限") String menus) {
		List<CompanyFunctionDTO> menuDtos=JsonKit.parseList(menus, CompanyFunctionDTO.class);
		if(companyService.saveOrUpdateCompany(company,companyInfo,menuDtos)) {
			CacheManagement.clearMenuRolePermissions();
			return ApiResult.ok(company);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  = {"admin:system:company:queryPage"})
	public ApiResult<IPage<CompanyVo>> queryPage(Query query,CompanyDTO param) {
		page = companyService.queryCompanyPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param companyId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value={ "admin:system:company:getById" })
	public ApiResult<Company> getById(@NotEmpty(message = "主键不能为空") String companyId) {
		return ApiResult.ok(companyService.getById(companyId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param companyId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value={ "admin:system:company:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String companyId) {
		companyService.removeById(companyId);
		CacheManagement.clearMenuRolePermissions();
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param companyIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "企业-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value={ "admin:system:company:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String companyIds) {
		companyService.removeBatchByIds(Arrays.asList(StringUtils.split(companyIds, ",")));
		CacheManagement.clearMenuRolePermissions();
		return ApiResult.ok();
	}
}
