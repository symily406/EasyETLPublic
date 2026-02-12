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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "ORG_ID", type = IdType.ASSIGN_ID)
	private String orgId;

	/**
	 * 归属企业
	 */
	@TableField(value = "COMPANY_ID")
	@NotEmpty(message = "companyId归属企业不能为空")
	@Length(max = 32, message = "companyId归属企业不能超过32个字符")
	private String companyId;
	/**
	 * 机构全称
	 */
	@TableField(value = "ORG_FULL_NAME")
	@NotEmpty(message = "orgFullName机构全称不能为空")
	@Length(max = 128, message = "orgFullName机构全称不能超过128个字符")
	private String orgFullName;
	/**
	 * 机构简称
	 */
	@TableField(value = "ORG_NAME")
	@Length(max = 128, message = "orgName机构简称不能超过128个字符")
	private String orgName;
	/**
	 * 机构编码
	 */
	@TableField(value = "ORG_CODE")
	@NotEmpty(message = "orgCode机构编码不能为空")
	@Length(max = 32, message = "orgCode机构编码不能超过32个字符")
	private String orgCode;
	/**
	 * 上级机构
	 */
	@TableField(value = "P_ORG_ID")
	@NotEmpty(message = "pOrgId上级机构不能为空")
	@Length(max = 32, message = "pOrgId上级机构不能超过32个字符")
	private String pOrgId;
	/**
	 * 机构路径
	 */
	@TableField(value = "ORG_PATH")
	@Length(max = 256, message = "orgPath机构路径不能超过256个字符")
	private String orgPath;

	public Org() {
		super();
	}

	/**
	 * 主键-数据库字段:ORG_ID
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 主键-数据库字段:ORG_ID
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 归属企业-数据库字段:COMPANY_ID
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * 归属企业-数据库字段:COMPANY_ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * 机构全称-数据库字段:ORG_FULL_NAME
	 */
	public String getOrgFullName() {
		return orgFullName;
	}

	/**
	 * 机构全称-数据库字段:ORG_FULL_NAME
	 */
	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	/**
	 * 机构简称-数据库字段:ORG_NAME
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 机构简称-数据库字段:ORG_NAME
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 机构编码-数据库字段:ORG_CODE
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 机构编码-数据库字段:ORG_CODE
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 上级机构-数据库字段:P_ORG_ID
	 */
	public String getPOrgId() {
		return pOrgId;
	}

	/**
	 * 上级机构-数据库字段:P_ORG_ID
	 */
	public void setPOrgId(String pOrgId) {
		this.pOrgId = pOrgId;
	}

	/**
	 * 机构路径-数据库字段:ORG_PATH
	 */
	public String getOrgPath() {
		return orgPath;
	}

	/**
	 * 机构路径-数据库字段:ORG_PATH
	 */
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

}
