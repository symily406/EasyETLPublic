package com.easy.system.company.vo;

import java.io.Serializable;

/**
 * 企业列表-下拉视图
 */
public class CompanyDropDownVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 企业主键
	 */
	private String companyId;

	/**
	 * 企业名称
	 */
	private String companyName;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
