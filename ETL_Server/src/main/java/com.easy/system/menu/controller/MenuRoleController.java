package com.easy.system.menu.controller;

import java.util.ArrayList;
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
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.easy.run.cache.CacheManagement;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.entity.Menu;
import com.easy.system.menu.entity.MenuRole;
import com.easy.system.menu.service.IMenuRoleService;
import com.easy.system.menu.service.IMenuService;
import com.easy.system.menu.vo.MenuRoleVo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.core.dto.UserInfoDTO;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.crypto.SecureUtil;

/**
 * 菜单权限-控制器
 *
 * @author
 * @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/menu/menuRole")
public class MenuRoleController extends BaseController {

	@Autowired
	private IMenuRoleService menuRoleService;
	@Autowired
	private IMenuService menuService;

	/**
	 * 保存或修改
	 *
	 * @param menuRole
	 */
	@SaCheckLogin
	@DebugAnnotation
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:menuRole:add","admin:system:menuRole:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated MenuRole menuRole,UserInfoDTO userInfoDTO) {

		if (StringUtils.isEmpty(menuRole.getRoleId())) {
			menuRole.setRoleId(SecureUtil.md5(menuRole.getRole()));
		}

		menuRole.setMenuPath(StrKit.clearDuplicateValue(menuRole.getMenuPath().concat("|").concat(menuRole.getMenuId()).concat("|").concat(menuRole.getRoleId()), "|"));
		if (menuRoleService.saveOrUpdateMenuRole(menuRole,userInfoDTO)) {
			CacheManagement.clearMenuRolePermissions();
			return ApiResult.ok(menuRole);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 批量添加权限
	 */
	@SaCheckLogin
	@DebugAnnotation
	@GetMapping(value = "/beatchAdd")
	@SaCheckPermission(value  = {"admin:system:menuRole:beatchAdd"})
	public ApiResult beatchAdd(@NotEmpty(message = "菜单主键不能为空") String menuId,UserInfoDTO userInfoDTO) {
		Menu menu=menuService.getById(menuId);
		if (menu==null) {
			return ApiResult.fail("菜单不存在");
		}
		String rolePath=StringUtils.replace(menu.getRoutePath(), "/",":").substring(1);

		MenuRole role=null;
		List<MenuRole> roles=new ArrayList<>();
		role=new MenuRole();
		role.setRole(rolePath.concat(":add"));
		role.setRoleId(SecureUtil.md5(role.getRole()));
		role.setMenuPath(menu.getMenuPath().concat("|").concat(role.getRoleId()));
		role.setRoleName("添加");
		role.setShowOrder(100);
		role.setMenuId(menuId);
		roles.add(role);

		role=new MenuRole();
		role.setRole(rolePath.concat(":edit"));
		role.setRoleId(SecureUtil.md5(role.getRole()));
		role.setMenuPath(menu.getMenuPath().concat("|").concat(role.getRoleId()));
		role.setRoleName("修改");
		role.setShowOrder(90);
		role.setMenuId(menuId);
		roles.add(role);

		role=new MenuRole();
		role.setRole(rolePath.concat(":deleteById"));
		role.setRoleId(SecureUtil.md5(role.getRole()));
		role.setMenuPath(menu.getMenuPath().concat("|").concat(role.getRoleId()));
		role.setRoleName("删除");
		role.setShowOrder(80);
		role.setMenuId(menuId);
		roles.add(role);


		role=new MenuRole();
		role.setRole(rolePath.concat(":batchDeleteById"));
		role.setRoleId(SecureUtil.md5(role.getRole()));
		role.setMenuPath(menu.getMenuPath().concat("|").concat(role.getRoleId()));
		role.setRoleName("批量删除");
		role.setShowOrder(70);
		role.setMenuId(menuId);
		roles.add(role);



		role=new MenuRole();
		role.setRole(rolePath.concat(":queryPage"));
		role.setRoleId(SecureUtil.md5(role.getRole()));
		role.setMenuPath(menu.getMenuPath().concat("|").concat(role.getRoleId()));
		role.setRoleName("查询");
		role.setShowOrder(60);
		role.setMenuId(menuId);
		roles.add(role);

		role=new MenuRole();
		role.setRole(rolePath.concat(":getById"));
		role.setRoleId(SecureUtil.md5(role.getRole()));
		role.setMenuPath(menu.getMenuPath().concat("|").concat(role.getRoleId()));
		role.setRoleName("获取单条记录");
		role.setShowOrder(50);
		role.setMenuId(menuId);
		roles.add(role);

		if(menuRoleService.saveOrUpdateMenuRoleBatch(roles,userInfoDTO)) {
			CacheManagement.clearMenuRolePermissions();
			return ApiResult.ok();
		}

		return ApiResult.fail("批量添加成功");
	}



	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单权限-查询所有")
	@GetMapping(value = "/queryAll")
	@SaCheckPermission(value  ={"admin:system:menuRole:queryAll"})
	public ApiResult<List<MenuRoleVo>> queryPage(MenuRoleDTO param) {
		List<MenuRoleVo> list = menuRoleService.queryMenuRoleAll(param);
		return ApiResult.ok(list);
	}


	/**
	 * 根据主键删除
	 *
	 * @param roleId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单权限-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:menuRole:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String roleId) {
		menuRoleService.beatchRemoveMenuRole(roleId);
		CacheManagement.clearMenuRolePermissions();
		return ApiResult.ok();
	}

	/**
	 * 根据主键批量删除
	 *
	 * @param roleIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:menuRole:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String roleIds) {
		menuRoleService.beatchRemoveMenuRole(roleIds);
		CacheManagement.clearMenuRolePermissions();
		return ApiResult.ok();
	}
}
