package com.easy.system.indexPage.dto;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.indexPage.entity.IndexPage;

/**
 * 系统首页-数据传输对象实体类
 *
 * @author
 * @time
 */
public class IndexPageDTO extends IndexPage {

	/**
	 *
	 */
	private static final long serialVersionUID = 8850329186956569606L;

	/*** 归属企业 */
	private String companyId;

	/**
	 * 归属机构
	 */
	private String orgId;

	/**
	 * 添加用户
	 */
	private String addUserId;

	public String getCompanyId() {
		if (StrKit.notBlank(companyId)) {
			return companyId;
		}
		if (getUserLeval() != 100) {
			return getUserCompanyId();
		}
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOrgId() {
		if (StrKit.notBlank(orgId)) {
			return orgId;
		}
		if (getUserLeval() != 100) {
			return getUserOrgId();
		}
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAddUserId() {
		if (StrKit.notBlank(addUserId)) {
			return addUserId;
		}
		if (getUserDutyLeval() == 0) {
			return getUserId();
		}
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}
}
