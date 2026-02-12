package com.easy.system.staff.vo;

import com.easy.system.staff.entity.StaffRole;

/**
*  角色用户关联-视图实体类
 * @author
 * @time
 */
public class StaffRoleVo extends StaffRole {

	/**
	 *
	 */
	private static final long serialVersionUID = -7145201390426259461L;

	/**
	 * 角色名
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
