package com.easy.system.menu.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.entity.MenuRole;
import com.easy.system.menu.vo.MenuRoleVo;
import com.easy.etl.core.dto.UserInfoDTO;

/**
 * 菜单权限-服务类
 * @author
 * @time
 */
public interface  IMenuRoleService extends IBaseService<MenuRole> {

	/**
	 * 批量删除权限
	 * @param roleIds
	 * @return
	 */

	public boolean beatchRemoveMenuRole(String roleIds);

	/**
	   *批量添加权限
	 */
	public boolean saveOrUpdateMenuRoleBatch(List<MenuRole> roles,UserInfoDTO userInfoDTO);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param menuRole
	 */
	public boolean saveOrUpdateMenuRole(MenuRole menuRole,UserInfoDTO userInfoDTO);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<MenuRoleVo> queryMenuRolePage(IPage<MenuRoleVo> page,MenuRoleDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<MenuRoleVo> queryMenuRoleAll(MenuRoleDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public MenuRoleVo queryMenuRoleByCondition(MenuRoleDTO param);
	//================代码生成器生成代码结束=================
}
