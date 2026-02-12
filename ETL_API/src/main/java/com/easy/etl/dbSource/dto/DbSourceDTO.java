package com.easy.etl.dbSource.dto;


import com.easy.etl.dbSource.entity.DbSource;
import com.spring.boot.ext.kit.StrKit;

/**
*  数据源-数据传输对象实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class DbSourceDTO extends DbSource {

	private String searchKey;

	private String projectId;


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

	private Integer isCatalog;

	/**
	 * 是否有scheam
	 */
	private Integer isScheam;

	private Integer isAccount;

	private Integer isPassword;



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
		if (getUserLeval() != 100&&getUserDutyLeval()==4) {
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

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getIsCatalog() {
		return isCatalog;
	}

	public void setIsCatalog(Integer isCatalog) {
		this.isCatalog = isCatalog;
	}

	public Integer getIsScheam() {
		return isScheam;
	}

	public void setIsScheam(Integer isScheam) {
		this.isScheam = isScheam;
	}

	public Integer getIsAccount() {
		return isAccount;
	}

	public void setIsAccount(Integer isAccount) {
		this.isAccount = isAccount;
	}

	public Integer getIsPassword() {
		return isPassword;
	}

	public void setIsPassword(Integer isPassword) {
		this.isPassword = isPassword;
	}
}
