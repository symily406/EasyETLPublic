package com.easy.system.menu.vo;

import com.easy.system.menu.entity.Menu;

/**
*  系统菜单-视图实体类
 * @author
 * @time
 */
public class MenuVo extends Menu {

	/**
	 *
	 */
	private static final long serialVersionUID = 2590135204456507606L;
	/**
	 * 上级菜单
	 */
	private String parentMenuName;

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}
}
