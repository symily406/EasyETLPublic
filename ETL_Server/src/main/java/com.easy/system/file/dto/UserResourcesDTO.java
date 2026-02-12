package com.easy.system.file.dto;

import java.util.List;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.file.entity.UserResources;

/**
 * 用户资源-数据传输对象实体类
 *
 * @author
 * @time
 */
public class UserResourcesDTO extends UserResources {

	/**
	 *
	 */
	private static final long serialVersionUID = -2892020638615146144L;

	private List<String> queryFileTypes;

	public List<String> getQueryFileTypes() {
		return queryFileTypes;
	}

	public void setQueryFileTypes(List<String> queryFileTypes) {
		this.queryFileTypes = queryFileTypes;
	}

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
