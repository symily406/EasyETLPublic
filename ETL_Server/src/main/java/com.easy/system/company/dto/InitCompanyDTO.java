package com.easy.system.company.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

/**
 * 初始化企业
 *
 * @author
 *
 */
public class InitCompanyDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5556383098465727480L;

	// 企业编码
	@NotEmpty(message = "companyId企业不能为空")
	private String companyId;
	// 机构全称
	@NotEmpty(message = "initOrgFullName机构全称不能为空")
	private String initOrgFullName;
	// 机构简称
	@NotEmpty(message = "initOrgName机构简称不能为空")
	private String initOrgName;
	// 机构编码
	@NotEmpty(message = "initOrgCode机构编码不能为空")
	private String initOrgCode;
	// 角色编码
	@NotEmpty(message = "initRoleCode角色编码不能为空")
	private String initRoleCode;
	// 角色名称
	@NotEmpty(message = "initRoleName角色名称不能为空")
	private String initRoleName;
	// 用户名
	@NotEmpty(message = "initUserName用户名不能为空")
	private String initUserName;
	// 密码
	@NotEmpty(message = "initPassword密码不能为空")
	private String initPassword;
	// 确认密码
	@NotEmpty(message = "initConfirmPassword确认密码不能为空")
	private String initConfirmPassword;
	// 过期时间
	@NotEmpty(message = "initExpireTime过期时间不能为空")
	private String initExpireTime;
	// 用户姓名
	@NotEmpty(message = "initName用户姓名不能为空")
	private String initName;
	// 用户手机
	@NotEmpty(message = "initPhone用户手机不能为空")
	private String initPhone;
	// 用户性别
	private Integer initSex;

	// 盐值
	private String initSalt;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getInitOrgFullName() {
		return initOrgFullName;
	}

	public void setInitOrgFullName(String initOrgFullName) {
		this.initOrgFullName = initOrgFullName;
	}

	public String getInitOrgName() {
		return initOrgName;
	}

	public void setInitOrgName(String initOrgName) {
		this.initOrgName = initOrgName;
	}

	public String getInitOrgCode() {
		return initOrgCode;
	}

	public void setInitOrgCode(String initOrgCode) {
		this.initOrgCode = initOrgCode;
	}

	public String getInitRoleCode() {
		return initRoleCode;
	}

	public void setInitRoleCode(String initRoleCode) {
		this.initRoleCode = initRoleCode;
	}

	public String getInitRoleName() {
		return initRoleName;
	}

	public void setInitRoleName(String initRoleName) {
		this.initRoleName = initRoleName;
	}

	public String getInitUserName() {
		return initUserName;
	}

	public void setInitUserName(String initUserName) {
		this.initUserName = initUserName;
	}

	public String getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(String initPassword) {
		this.initPassword = initPassword;
	}

	public String getInitConfirmPassword() {
		return initConfirmPassword;
	}

	public void setInitConfirmPassword(String initConfirmPassword) {
		this.initConfirmPassword = initConfirmPassword;
	}

	public String getInitExpireTime() {
		return initExpireTime;
	}

	public void setInitExpireTime(String initExpireTime) {
		this.initExpireTime = initExpireTime;
	}

	public String getInitName() {
		return initName;
	}

	public void setInitName(String initName) {
		this.initName = initName;
	}

	public String getInitPhone() {
		return initPhone;
	}

	public void setInitPhone(String initPhone) {
		this.initPhone = initPhone;
	}

	public Integer getInitSex() {
		return initSex;
	}

	public void setInitSex(Integer initSex) {
		this.initSex = initSex;
	}

	public String getInitSalt() {
		return initSalt;
	}

	public void setInitSalt(String initSalt) {
		this.initSalt = initSalt;
	}

}
