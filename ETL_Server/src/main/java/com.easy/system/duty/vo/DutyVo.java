package com.easy.system.duty.vo;

import com.easy.system.duty.entity.Duty;

/**
*  岗位管理-视图实体类
 * @author
 * @time
 */
public class DutyVo extends Duty {

	/**
	 *
	 */
	private static final long serialVersionUID = -9084107203415555231L;


	/**
	 * 企业名称
	 */
	private String companyName;

	/**
	 * 部门名称
	 */
	private String orgName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
