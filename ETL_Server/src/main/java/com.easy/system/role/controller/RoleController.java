package com.easy.system.role.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.easy.run.cache.CacheManagement;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.menu.dto.MenuDTO;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.service.IMenuService;
import com.easy.system.menu.vo.MenuCompanyVo;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.role.dto.RoleDTO;
import com.easy.system.role.dto.RoleFunctionDTO;
import com.easy.system.role.entity.Role;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.service.IRoleFunctionService;
import com.easy.system.role.service.IRoleService;
import com.easy.system.role.vo.RoleFunctionVo;
import com.easy.system.role.vo.RoleTreeVo;
import com.easy.system.role.vo.RoleVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  角色-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IRoleFunctionService roleFunctionService;


	/**
	 * 获取角色列表
	 * @param role
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "获取角色列表")
	@GetMapping(value = "/queryList")
	public ApiResult<CompanyDTO> queryList(CompanyDTO companyDTO,RoleFunctionDTO fun){
		return ApiResult.ok(companyDTO);
	}


	/**
	 * 保存或修改
	 * @param role
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "角色-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  ={"admin:system:role:add","admin:system:role:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Role role,@NotEmpty(message = "角色对应的菜单权限不能为空") String menus) {
		List<RoleFunction> roleFunctions=JsonKit.parseList(menus, RoleFunction.class);
		if(roleService.saveOrUpdateRole(role,roleFunctions)) {
			CacheManagement.clearMenuRolePermissions();
			return ApiResult.ok(role);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 权限
	 * @param companyDTO
	 * @param orgDTO
	 * @param roleDTO
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "部门-角色")
	@GetMapping(value = "/queryOrgRoles")
	@SaCheckPermission(value  ={"admin:system:role:queryAll"})
	public ApiResult<List<RoleVo>> queryOrgRoles(RoleDTO roleDTO) {
		List<RoleVo> list = roleService.queryOrgRoles(roleDTO);
		return ApiResult.ok(list);
	}


	/**
	 * 权限
	 * @param companyDTO
	 * @param orgDTO
	 * @param roleDTO
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "角色- 权限")
	@GetMapping(value = "/queryAll")
	@SaCheckPermission(value  ={"admin:system:role:queryAll","admin:system:flowable:workflow:process:designer"}, mode = SaMode.OR)
	public ApiResult<List<RoleTreeVo>> queryAll(CompanyDTO companyDTO,OrgDTO orgDTO,RoleDTO roleDTO) {
		List<RoleTreeVo> list = roleService.queryRoleTree(companyDTO,orgDTO,roleDTO);
		return ApiResult.ok(list);
	}

	/**
	 * 权限树
	 * @param companyDTO
	 * @param orgDTO
	 * @param roleDTO
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "角色- 权限树")
	@GetMapping(value = "/queryRoleTree")
	@SaCheckPermission(value  ={"admin:system:role:queryRoleTree"})
	public ApiResult<Map<String, Object>> queryRoleTree(MenuDTO menu,MenuRoleDTO menuRole,RoleFunctionDTO roleFunctionDTO) {
		List<MenuCompanyVo> menus = menuService.menuRoleTree(menu, menuRole);
		List<RoleFunctionVo> funs = roleFunctionService.queryRoleFunctionAll(roleFunctionDTO);
		Map<String, Object> re=new HashMap<>();
		re.put("menus", menus);
		re.put("funs", funs);
		return ApiResult.ok(re);
	}




	/**
	 * 根据主键返回
	 *
	 * @param rId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "角色- 根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  ={ "admin:system:role:getById" })
	public ApiResult<Role> getById(@NotEmpty(message = "主键不能为空") String rId) {
		return ApiResult.ok(roleService.getById(rId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param rId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "角色- 根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:role:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String rId) {
		roleService.deleteRoleById(rId);
		CacheManagement.clearMenuRolePermissions();
		return ApiResult.ok();
	}
}
