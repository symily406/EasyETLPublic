package com.easy.system.company.dto;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.company.entity.Company;

/**
*  企业-数据传输对象实体类
 * @author
 * @time
 */
public class CompanyDTO extends Company{

	private static final long serialVersionUID = 5006825340884920575L;

	private String companyId;

	public String getCompanyId() {
		if(StrKit.notBlank(companyId)) {
			return companyId;
		}
		if(getUserLeval()==100) {
			return companyId;
		}
		if(StrKit.isBlank(companyId)) {
			companyId=getUserCompanyId();
		}
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
