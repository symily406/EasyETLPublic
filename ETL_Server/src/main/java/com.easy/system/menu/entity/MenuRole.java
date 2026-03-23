package com.easy.system.menu.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;

/**
 * 菜单权限-实体类
 *
 * @table TD_MENU_ROLE
 * @author
 * @time
 */
@TableName(value = "TD_MENU_ROLE", autoResultMap = true)
public class MenuRole extends Entity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ROLE_ID", type = IdType.INPUT)
	private String roleId;


	@TableField(value = "MENU_ID")
	private String menuId;

	@TableField(value = "ROLE_NAME")
	private String roleName;

	@TableField(value = "ROLE")
	private String role;



	@TableField(value = "MENU_PATH")
	private String menuPath;

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public MenuRole() {
		super();
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getMenuId() {
		return menuId;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

}
