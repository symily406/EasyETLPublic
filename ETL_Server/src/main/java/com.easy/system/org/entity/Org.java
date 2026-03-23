package com.easy.system.org.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;

/**
 * 机构-实体类
 *
 * @table TD_ORG
 * @author
 * @time
 */
@TableName(value = "TD_ORG", autoResultMap = true)
public class Org extends Entity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ORG_ID", type = IdType.ASSIGN_ID)
	private String orgId;


	@TableField(value = "COMPANY_ID")
	private String companyId;

	@TableField(value = "ORG_FULL_NAME")
	private String orgFullName;

	@TableField(value = "ORG_NAME")
	private String orgName;

	@TableField(value = "ORG_CODE")
	private String orgCode;

	@TableField(value = "P_ORG_ID")
	private String pOrgId;

	@TableField(value = "ORG_PATH")
	private String orgPath;

	public Org() {
		super();
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getOrgFullName() {
		return orgFullName;
	}


	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getOrgCode() {
		return orgCode;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	public String getPOrgId() {
		return pOrgId;
	}


	public void setPOrgId(String pOrgId) {
		this.pOrgId = pOrgId;
	}


	public String getOrgPath() {
		return orgPath;
	}


	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

}
