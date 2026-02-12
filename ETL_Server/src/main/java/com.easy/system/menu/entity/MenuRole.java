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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "ROLE_ID", type = IdType.INPUT)
	private String roleId;

	/**
	 * 所属菜单
	 */
	@TableField(value = "MENU_ID")
	@NotEmpty(message = "menuId所属菜单不能为空")
	@Length(max = 32, message = "menuId所属菜单不能超过32个字符")
	private String menuId;
	/**
	 * 权限名称
	 */
	@TableField(value = "ROLE_NAME")
	@NotEmpty(message = "roleName权限名称不能为空")
	@Length(max = 64, message = "roleName权限名称不能超过64个字符")
	private String roleName;
	/**
	 * 权限
	 */
	@TableField(value = "ROLE")
	@NotEmpty(message = "role权限不能为空")
	@Length(max = 64, message = "role权限不能超过64个字符")
	private String role;


	/**
	 * 菜单全路径
	 */
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

	/**
	 * 主键-数据库字段:ROLE_ID
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * 主键-数据库字段:ROLE_ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 所属菜单-数据库字段:MENU_ID
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * 所属菜单-数据库字段:MENU_ID
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 权限名称-数据库字段:ROLE_NAME
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 权限名称-数据库字段:ROLE_NAME
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 权限-数据库字段:ROLE
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 权限-数据库字段:ROLE
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
