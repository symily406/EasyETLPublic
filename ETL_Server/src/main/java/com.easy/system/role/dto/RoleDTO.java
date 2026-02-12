package com.easy.system.role.dto;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.role.entity.Role;

/**
*  角色-数据传输对象实体类
 * @author
 * @time
 */
public class RoleDTO extends Role{

	/**
	 *
	 */
	private static final long serialVersionUID = 7413515232540153367L;


	/**
	 * 归属企业
	 */
	private String companyId;


	public String getCompanyId() {
		if (StrKit.notBlank(companyId)) {
			return companyId;
		}
		if (getUserLeval() == 100) {
			return companyId;
		}
		if (StrKit.isBlank(companyId)) {
			companyId = getUserCompanyId();
		}
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


}
