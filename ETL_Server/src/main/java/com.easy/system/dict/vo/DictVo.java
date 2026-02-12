package com.easy.system.dict.vo;

import com.easy.system.dict.entity.Dict;

/**
*  字典主表-视图实体类
 * @author
 * @time
 */
public class DictVo extends Dict {

	/**
	 *
	 */
	private static final long serialVersionUID = 4412375550129361987L;


	private String companyName;

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
