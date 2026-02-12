package com.easy.system.menu.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.menu.dto.MenuDTO;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.entity.Menu;
import com.easy.system.menu.vo.StaffMenuVo;
import com.easy.etl.core.dto.UserInfoDTO;
import com.easy.system.menu.vo.MenuCompanyVo;
import com.easy.system.menu.vo.MenuTreeVo;
import com.easy.system.menu.vo.MenuVo;

/**
 * 系统菜单-服务类
 *
 * @author
 * @time
 */
public interface IMenuService extends IBaseService<Menu> {

	/**
	 * 企业权限配置树
	 * @param menu
	 * @param menuRole
	 * @return
	 */
	public List<MenuCompanyVo> menuRoleTree(MenuDTO menu,MenuRoleDTO menuRole);


	/**
	 * 企业菜单接口
	 * @param menu
	 * @param menuRole
	 * @return
	 */
	public List<MenuCompanyVo> menuCompanyTree(MenuDTO menu,MenuRoleDTO menuRole);

	/**
	 * 前端菜单接口
	 *
	 * @param param
	 * @return
	 */
	public List<StaffMenuVo> staffMenuTree(MenuDTO menu);

	/**
	 * 根据ID批量删除
	 * @param menuIds
	 * @return
	 */
	public boolean removeBatchByIds(String menuIds);

	/**
	 * 菜单树
	 *
	 * @return
	 */
	public List<MenuTreeVo> menuTree(MenuDTO param);

	// ================代码生成器生成代码开始=================
	/**
	 * 保存或修改Entity
	 *
	 * @param menu
	 */
	public boolean saveOrUpdateMenu(Menu menu,UserInfoDTO userInfoDTO);


	/**
	 * 更新菜单启用状态
	 * @param menu
	 * @param userInfoDTO
	 * @return
	 */
	public boolean updateMenuEnable(Menu menu);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<StaffMenuVo> queryMenuPage(IPage<StaffMenuVo> page, MenuDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<MenuVo> queryMenuAll(MenuDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	public StaffMenuVo queryMenuByCondition(MenuDTO param);
	// ================代码生成器生成代码结束=================
}
