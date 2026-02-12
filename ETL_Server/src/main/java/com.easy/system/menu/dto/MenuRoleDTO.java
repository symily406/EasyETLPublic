package com.easy.system.menu.dto;

import com.easy.system.menu.entity.MenuRole;

/**
*  菜单权限-数据传输对象实体类
 * @author
 * @time
 */
public class MenuRoleDTO extends MenuRole{

	/**
	 *
	 */
	private static final long serialVersionUID = 593398141138126179L;

	private String companyId;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


}
