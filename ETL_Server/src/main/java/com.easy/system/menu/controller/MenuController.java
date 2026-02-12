package com.easy.system.menu.controller;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.auth.IAuth;
import com.spring.boot.enums.LoginEnum;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.run.cache.CacheManagement;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.service.IFileConfigService;
import com.easy.system.menu.dto.MenuDTO;
import com.easy.system.menu.entity.Menu;
import com.easy.system.menu.service.IMenuService;
import com.easy.system.menu.vo.MenuTreeVo;
import com.easy.system.menu.vo.StaffMenuVo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.core.dto.UserInfoDTO;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.util.IdUtil;

@Validated
@RestController
@RequestMapping(value = "/admin/system/menu/menu")
public class MenuController extends BaseController {

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IFileConfigService fileConfigService;

	@Autowired
	private IAuth auth;



	@SaCheckLogin
	@DebugAnnotation(desc = "菜单-获取用户菜单")
	@ResponseBody
	@GetMapping(value = "/menus")
	public ApiResult<Map<String, Object>> menus(MenuDTO menu) {

		menu.setStaffId(menu.getUserId());
		List<StaffMenuVo> staffMenuVos=menuService.staffMenuTree(menu);
		// 用户权限
		List<String> permissions = auth.queryStaffPermissions(menu.getUserId(),String.valueOf(LoginEnum.PC.getLoginType()));
		//允许上传文件类型
		FileConfigDTO dto = new FileConfigDTO();
		dto.setCompanyId(menu.getUserCompanyId());
		String fileAccept = fileConfigService.queryFileAccept(dto);


		Map<String, Object> re=new HashMap<>();
		re.put("menus",staffMenuVos);
		re.put("permissions", permissions);
		re.put("fileAccept", fileAccept);
		return ApiResult.ok(re);
	}

	/**
	 * 设置菜单可用状态
	 *
	 * @param menu
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "设置菜单可用状态")
	@GetMapping(value = "/updateMenuEnable")
	@SaCheckPermission(value  = { "admin:system:menu:updateMenuEnable"})
	public ApiResult updateMenuEnable(@Validated Menu menu) {
		if (menuService.updateMenuEnable(menu)) {
			CacheManagement.clearMenuRolePermissions();
			return ApiResult.ok(menu);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 保存或修改
	 *
	 * @param menu
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = { "admin:system:menu:add", "admin:system:menu:edit" },mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Menu menu,UserInfoDTO userInfoDTO) {
		String MENU_ID=IdUtil.fastSimpleUUID();
		if (StringUtils.isNotBlank(menu.getMenuId())) {
			MENU_ID=menu.getMenuId();
		}
		menu.setMenuId(MENU_ID);
		menu.setMenuPath(StrKit.clearDuplicateValue(menu.getPid().concat("|").concat(menu.getMenuPath()).concat("|").concat(menu.getMenuId()),"|"));
		if (menuService.saveOrUpdateMenu(menu,userInfoDTO)) {
			CacheManagement.clearMenuRolePermissions();
			return ApiResult.ok(menu);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 菜单树
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单-菜单树")
	@GetMapping(value = "/menuTree")
	@SaCheckPermission(value  ={ "admin:system:menu:menuTree" })
	public ApiResult<List<MenuTreeVo>> menuTree(MenuDTO param) {
		List<MenuTreeVo> list = menuService.menuTree(param);
		return ApiResult.ok(list);
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  = { "admin:system:menu:queryPage" })
	public ApiResult<IPage<Menu>> queryPage(Query query, MenuDTO param) {
		page = menuService.queryMenuPage(Condition.getPage(query), param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param menuId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  ={ "admin:system:menu:getById" })
	public ApiResult<Menu> getById(@NotEmpty(message = "主键不能为空") String menuId) {
		return ApiResult.ok(menuService.getById(menuId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param menuId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "菜单-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:menu:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String menuId) {
		menuService.removeBatchByIds(menuId);
		CacheManagement.clearMenuRolePermissions();
		return ApiResult.ok();
	}

}
