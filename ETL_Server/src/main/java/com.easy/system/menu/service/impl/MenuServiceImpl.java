package com.easy.system.menu.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.run.cache.CacheManagement;
import com.easy.etl.core.init.Config;
import com.easy.system.company.entity.CompanyFunction;
import com.easy.system.company.mapper.CompanyFunctionMapper;
import com.easy.system.menu.dto.MenuDTO;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.entity.Menu;
import com.easy.system.menu.entity.MenuRole;
import com.easy.system.menu.mapper.MenuMapper;
import com.easy.system.menu.mapper.MenuRoleMapper;
import com.easy.system.menu.service.IMenuService;
import com.easy.system.menu.vo.MenuCompanyVo;
import com.easy.system.menu.vo.MenuRoleVo;
import com.easy.system.menu.vo.MenuTreeVo;
import com.easy.system.menu.vo.MenuVo;
import com.easy.system.menu.vo.StaffMenuVo;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.mapper.RoleFunctionMapper;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.mapper.StaffRoleMapper;
import com.easy.etl.core.dto.UserInfoDTO;

import cn.hutool.crypto.SecureUtil;

/**
 * 系统菜单-服务类
 * @author
 * @time
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {
	@Autowired
	private MenuRoleMapper menuRoleMapper;
	@Autowired
	private CompanyFunctionMapper companyFunctionMapper;
	@Autowired
	private StaffRoleMapper staffRoleMapper;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateMenu(Menu menu,UserInfoDTO userInfoDTO) {
		saveOrUpdate(menu);
		//增加企业菜单
		CompanyFunction companyFunction=companyFunctionMapper.selectOne(new QueryWrapper<CompanyFunction>()
				.lambda()
				.eq(CompanyFunction::getCompanyId, userInfoDTO.getCompanyId())
				.eq(CompanyFunction::getfId, menu.getMenuId())
				);
		if (companyFunction==null) {
			int leval=0;
			if(menu.getIsMenu()==1) {
				leval=1;
			}
			//企业菜单
			companyFunction=new CompanyFunction();
			companyFunction.setCompanyId(userInfoDTO.getCompanyId());
			companyFunction.setfId(menu.getMenuId());
			companyFunction.setLeval(leval);
			companyFunctionMapper.insert(companyFunction);

			List<StaffRole> staffRoles=staffRoleMapper.selectList(new QueryWrapper<StaffRole>()
					.lambda()
					.eq(StaffRole::getStaffId, userInfoDTO.getUserId())
					);
			for (StaffRole staffRole : staffRoles) {
				RoleFunction roleFunction=roleFunctionMapper.selectOne(new QueryWrapper<RoleFunction>()
						.lambda()
						.eq(RoleFunction::getrId, staffRole.getRoleId())
						.eq(RoleFunction::getfId, menu.getMenuId())
						);
				if (roleFunction==null) {
					roleFunction=new RoleFunction();
					roleFunction.setrId(staffRole.getRoleId());
					roleFunction.setfId(menu.getMenuId());
					roleFunction.setLeval(leval);
					roleFunctionMapper.insert(roleFunction);
				}
			}

		}
		return true;
	}


	@Override
	public boolean updateMenuEnable(Menu menu) {
		return saveOrUpdate(menu);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<StaffMenuVo> queryMenuPage(IPage<StaffMenuVo> page, MenuDTO param) {
		return page.setRecords(baseMapper.queryMenuPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<MenuVo> queryMenuAll(MenuDTO param) {
		return baseMapper.queryMenuAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public StaffMenuVo queryMenuByCondition(MenuDTO param) {
		return baseMapper.queryMenuByCondition(param);
	}

	/**
	 * 菜单树
	 *@return
	 */
	@Override
	public List<MenuTreeVo> menuTree(MenuDTO param) {
		List<MenuVo> menus=baseMapper.queryMenuAll(param);
		return recursiveMenuTree(menus,menus.stream().filter(v->v.getPid().equals("0")).collect(Collectors.toList()));
	}

	/**
	 * 递归生成菜单树
	 * @return
	 */
	private List<MenuTreeVo> recursiveMenuTree(List<MenuVo> menus,List<MenuVo> childerMenus) {
		if(childerMenus==null||childerMenus.isEmpty()) {
			return null;
		}
		List<MenuTreeVo> menuTrees=new ArrayList<>();
		childerMenus.forEach(menu->{
			MenuTreeVo treeVo=new MenuTreeVo();
			treeVo.setMenuId(menu.getMenuId());
			treeVo.setPid(menu.getPid());
			treeVo.setMenuPath(menu.getMenuPath());
			treeVo.setMenuName(menu.getMenuName());
			treeVo.setShowMenuName(menu.getShowMenuName());
			treeVo.setPageName(menu.getPageName());
			treeVo.setRoutePath(menu.getRoutePath());
			treeVo.setUrl(menu.getUrl());
			treeVo.setIcon(menu.getIcon());
			treeVo.setIsMenu(menu.getIsMenu());
			treeVo.setIsClose(menu.getIsClose());
			treeVo.setIsExternal(menu.getIsExternal());
			treeVo.setIsHidden(menu.getIsHidden());
			treeVo.setAddTime(menu.getAddTime());
			treeVo.setAllowEdit(menu.getAllowEdit());
			treeVo.setAllowDelete(menu.getAllowDelete());
			treeVo.setEnable(menu.getEnable());
			treeVo.setIsDel(menu.getIsDel());
			treeVo.setShowOrder(menu.getShowOrder());
			treeVo.setParentMenuName(menu.getParentMenuName());
			treeVo.setAddTime(menu.getAddTime());
			treeVo.setUpdateTime(menu.getUpdateTime());
			treeVo.setChildren(recursiveMenuTree(menus,menus.stream().filter(v->v.getPid().equals(menu.getMenuId())).collect(Collectors.toList())));
			menuTrees.add(treeVo);
		});
		return menuTrees;
	}

	/**
	 * 根据ID批量删除
	 * @param menuIds
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeBatchByIds(String menuId) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("menuId", menuId);

		List<MenuRole> menuRoles=menuRoleMapper.findEntity("SELECT * FROM TD_MENU_ROLE D WHERE INSTR(MENU_PATH,:menuId)>0", param);
		if (menuRoles!=null&&!menuRoles.isEmpty()) {
			for (MenuRole menuRole : menuRoles) {
				roleFunctionMapper.deleteByCondition("DELETE FROM TR_ROLE_FUNCTION WHERE F_ID='"+menuRole.getRoleId()+"'",null);
			}
		}
		List<Menu> menus=baseMapper.findEntity("SELECT * FROM TD_MENU D WHERE INSTR(MENU_PATH,:menuId)>0",param);
		if (menus!=null&&!menus.isEmpty()) {
			for (Menu menu : menus) {
				companyFunctionMapper.deleteByCondition("DELETE FROM TD_COMPANY_FUNCTION WHERE F_ID='"+menu.getMenuId()+"'", null);
			}
		}
		boolean b1=this.deleteByCondition("DELETE FROM TD_MENU_ROLE WHERE INSTR(MENU_PATH,'"+menuId+"')>0");
		boolean b2=this.deleteByCondition("DELETE FROM TD_MENU WHERE INSTR(MENU_PATH,'"+menuId+"')>0");
		return b1&&b2;
	}

	/**
	 * 前端菜单接口
	 * @param param
	 * @return
	 */
	@Override
	public List<StaffMenuVo> staffMenuTree(MenuDTO menu) {
		menu.setEnable(1);
		List<StaffMenuVo> list=CacheManagement.getStaffMenuCache(menu.getStaffId());
		if (!CollectionUtils.isEmpty(list)) {
			return list;
		}
		List<MenuVo> menus=baseMapper.queryMenuAll(menu);
		list=recursiveMenuApiTree(menus,menus.stream().filter(v->v.getPid().equals("0")).collect(Collectors.toList()));
		CacheManagement.setStaffMenuCache(menu.getStaffId(), list);
		return list;
	}

	/**
	 * 递归生成前端菜单树
	 * @return
	 */
	private List<StaffMenuVo> recursiveMenuApiTree(List<MenuVo> menus,List<MenuVo> childerMenus) {
		if(childerMenus==null||childerMenus.isEmpty()) {
			return null;
		}
		List<StaffMenuVo> menuTrees=new ArrayList<>();
		childerMenus.forEach(menu->{
			StaffMenuVo treeVo=new StaffMenuVo();
			treeVo.setId(menu.getMenuId());
			treeVo.setPid(menu.getPid());
			treeVo.setMenuName(menu.getMenuName());
			treeVo.setTitle(menu.getTitle());
			treeVo.setPageName(menu.getPageName());
			treeVo.setRoutePath(menu.getRoutePath());
			treeVo.setUrl(menu.getUrl());
			treeVo.setIcon(menu.getIcon());
			treeVo.setIsMenu(menu.getIsMenu()==1?true:false);
			treeVo.setIsClose(menu.getIsClose()==1?true:false);
			treeVo.setIsExternal(menu.getIsExternal()==1?true:false);
			treeVo.setIsHidden(menu.getIsHidden()==1?true:false);
			treeVo.setChildren(recursiveMenuApiTree(menus,menus.stream().filter(v->v.getPid().equals(menu.getMenuId())).collect(Collectors.toList())));
			menuTrees.add(treeVo);
		});
		return menuTrees;
	}

	/**
	 * 企业菜单接口
	 * @param menu
	 * @param menuRole
	 * @return
	 */
	@Override
	public List<MenuCompanyVo> menuCompanyTree(MenuDTO menu, MenuRoleDTO menuRole) {
		List<MenuVo> menus=baseMapper.queryMenuAll(menu);
		List<MenuRoleVo> roles=menuRoleMapper.queryMenuRoleAll(menuRole);
		return recursiveMenuCompanyTree(menus,roles,menus.stream().filter(v->v.getPid().equals("0")).collect(Collectors.toList()));
	}

	/**
	 * 递归生成前商菜单树
	 * @return
	 */
	private List<MenuCompanyVo> recursiveMenuCompanyTree(List<MenuVo> menus,List<MenuRoleVo> menuRoles,List<MenuVo> childerMenus) {
		if(childerMenus==null||childerMenus.isEmpty()) {
			return null;
		}
		List<MenuCompanyVo> menuTrees=new ArrayList<>();
		childerMenus.forEach(menu->{
			MenuCompanyVo treeVo=new MenuCompanyVo();
			treeVo.setId(menu.getMenuId());
			treeVo.setName(menu.getMenuName());
			treeVo.setLabel(menu.getMenuName());
			treeVo.setShowName(menu.getShowMenuName());
			treeVo.setTitle(menu.getTitle());
			treeVo.setLeval(menu.getIsMenu());

			if (menu.getIsMenu()==1) {
				List<MenuCompanyVo> roleRe=new ArrayList<>();
				List<MenuRoleVo> roles=menuRoles.stream().filter(role->role.getMenuId().equals(menu.getMenuId())).collect(Collectors.toList());
				for (MenuRoleVo role : roles) {
					MenuCompanyVo roleVo=new MenuCompanyVo();
					roleVo.setId(role.getRoleId());
					roleVo.setLabel(role.getRoleName());
					roleVo.setName(role.getRoleName());
					roleVo.setShowName(role.getRoleName());
					roleVo.setTitle(role.getRoleName());
					roleVo.setLeval(2);
					roleRe.add(roleVo);
				}
				treeVo.setChildren(roleRe);
			}else {
				treeVo.setChildren(recursiveMenuCompanyTree(menus,menuRoles,menus.stream().filter(v->v.getPid().equals(menu.getMenuId())).collect(Collectors.toList())));
			}
			menuTrees.add(treeVo);
		});
		return menuTrees;
	}

	/**
	 * 企业权限配置树
	 * @param menu
	 * @param menuRole
	 * @return
	 */
	@Override
	public List<MenuCompanyVo> menuRoleTree(MenuDTO menu, MenuRoleDTO menuRole) {
		List<MenuVo> menus=baseMapper.queryMenuAll(menu);
		List<MenuRoleVo> roles=menuRoleMapper.queryMenuRoleAll(menuRole);
		return recursiveMenuCompanyTree(menus,roles,menus.stream().filter(v->v.getPid().equals("0")).collect(Collectors.toList()));
	}

}
