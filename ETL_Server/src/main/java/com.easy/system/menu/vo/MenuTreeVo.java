package com.easy.system.menu.vo;

import java.io.Serializable;
import java.util.List;

import com.easy.system.menu.entity.Menu;

/**
 * 菜单权限树
 */
public class MenuTreeVo extends Menu implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1096994568553785911L;

	/**
	 * 上级菜单名称
	 */
	private String parentMenuName;

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	/**
	 * 权限
	 */
	private List<String> roles;

	/**
	 * 下级菜单
	 */
	private List<MenuTreeVo> children;


	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<MenuTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeVo> children) {
		this.children = children;
	}
}
