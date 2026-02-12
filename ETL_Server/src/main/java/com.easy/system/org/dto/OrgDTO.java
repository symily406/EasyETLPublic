package com.easy.system.org.dto;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.org.entity.Org;

/**
 * 机构-数据传输对象实体类
 *
 * @author
 * @time
 */
public class OrgDTO extends Org {

	/**
	 *
	 */
	private static final long serialVersionUID = -949566753920587953L;

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
