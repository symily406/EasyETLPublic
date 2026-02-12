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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "STAFF_ID", type = IdType.ASSIGN_ID)
	private String staffId;

	/**
	 * 用户名
	 */
	@TableField(value = "USER_NAME")
	@NotEmpty(message = "userName用户名不能为空")
	@Length(max = 64, message = "userName用户名不能超过64个字符")
	private String userName;
	/**
	 * 密码
	 */
	@JsonIgnore
	@TableField(value = "PASSWORD")
	@Length(max = 64, message = "password密码不能超过64个字符")
	private String password;
	/**
	 * 登陆次数
	 */
	@TableField(value = "LOGIN_NUM")
	private Integer loginNum;
	/**
	 * 锁定
	 */
	@TableField(value = "IS_LOCK")
	private Integer isLock;
	/**
	 * 过期时间
	 */
	@TableField(value = "EXPIRE_TIME")
	@Length(max = 16, message = "expireTime过期时间不能超过16个字符")
	private String expireTime;


	@JsonIgnore
	@TableField(value = "SALT")
	@Length(max = 32, message = "shiro盐值")
	private String salt;

	/**
	 * 归属机构
	 */
	@TableField(value = "LEVAL")
	private Integer leval;


	/**
	 * 登陆编码-用于单点登陆标识
	 */
	@TableField(value = "LOGIN_CODE")
	private String loginCode;

	/**
	 * 是否开启单点登陆:1开启
	 */
	@TableField(value = "IS_SINGLE_POINT_LOGIN")
	private Integer isSinglePointLogin;

	public Staff() {
		super();
	}

	/**
	 * 主键-数据库字段:STAFF_ID
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * 主键-数据库字段:STAFF_ID
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * 用户名-数据库字段:USER_NAME
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 用户名-数据库字段:USER_NAME
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 密码-数据库字段:PASSWORD
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 密码-数据库字段:PASSWORD
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 登陆次数-数据库字段:LOGIN_NUM
	 */
	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	/**
	 * 锁定-数据库字段:IS_LOCK
	 */
	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	/**
	 * 过期时间-数据库字段:EXPIRE_TIME
	 */
	public String getExpireTime() {
		return expireTime;
	}

	/**
	 * 过期时间-数据库字段:EXPIRE_TIME
	 */
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
