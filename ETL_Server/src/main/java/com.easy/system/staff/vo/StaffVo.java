package com.easy.system.staff.vo;

import com.easy.system.staff.entity.Staff;

/**
*  用户表-视图实体类
 * @author
 * @time
 */
public class StaffVo extends Staff {

	/**
	 *
	 */
	private static final long serialVersionUID = 2366557376219307241L;

	/**
	 * 企业名称
	 */
	private String companyName;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 锁定
	 */
	private String lock;

	private String singlePointLogin;

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 头像
	 */
	private String headPortrait;
	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 用户岗位层级
	 */
	private Integer dutyLeval;



	public String getLock() {
		return getIsLock().toString();
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getDutyLeval() {
		return dutyLeval;
	}

	public void setDutyLeval(Integer dutyLeval) {
		this.dutyLeval = dutyLeval;
	}

	public String getSinglePointLogin() {
		return getIsSinglePointLogin().toString();
	}

	public void setSinglePointLogin(String singlePointLogin) {
		this.singlePointLogin = singlePointLogin;
	}

}
