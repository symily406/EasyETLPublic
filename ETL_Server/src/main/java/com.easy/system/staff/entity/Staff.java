package com.easy.system.staff.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 用户表-实体类
 *
 * @table TD_STAFF
 * @author
 * @time
 */
@TableName(value = "TD_STAFF", autoResultMap = true)
public class Staff extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "STAFF_ID", type = IdType.ASSIGN_ID)
	private String staffId;


	@TableField(value = "USER_NAME")
	private String userName;

	@JsonIgnore
	@TableField(value = "PASSWORD")
	private String password;

	@TableField(value = "LOGIN_NUM")
	private Integer loginNum;

	@TableField(value = "IS_LOCK")
	private Integer isLock;

	@TableField(value = "EXPIRE_TIME")
	private String expireTime;


	@JsonIgnore
	@TableField(value = "SALT")
	private String salt;


	@TableField(value = "LEVAL")
	private Integer leval;



	@TableField(value = "LOGIN_CODE")
	private String loginCode;


	@TableField(value = "IS_SINGLE_POINT_LOGIN")
	private Integer isSinglePointLogin;

	public Staff() {
		super();
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}


	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}


	public String getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}



	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public Integer getIsSinglePointLogin() {
		return isSinglePointLogin;
	}

	public void setIsSinglePointLogin(Integer isSinglePointLogin) {
		this.isSinglePointLogin = isSinglePointLogin;
	}
}
