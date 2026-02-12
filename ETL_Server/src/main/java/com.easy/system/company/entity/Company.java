package com.easy.system.company.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;

/**
 * 企业-实体类
 *
 * @table TD_COMPANY
 * @author
 * @time
 */
@TableName(value = "TD_COMPANY", autoResultMap = true)
public class Company extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "COMPANY_ID", type = IdType.INPUT)
	private String companyId;

	/**
	 * 企业名称
	 */
	@TableField(value = "COMPANY_NAME")
	@NotEmpty(message = "companyName企业名称不能为空")
	@Length(max = 128, message = "companyName企业名称不能超过128个字符")
	private String companyName;
	/**
	 * 平台名称
	 */
	@TableField(value = "PLATFORM_NAME")
	@Length(max = 128, message = "platformName平台名称不能超过128个字符")
	private String platformName;
	/**
	 * 省
	 */
	@TableField(value = "PROVINCE")
	@Length(max = 32, message = "province省不能超过32个字符")
	private String province;
	/**
	 * 市
	 */
	@TableField(value = "CITY")
	@Length(max = 32, message = "city市不能超过32个字符")
	private String city;
	/**
	 * 县
	 */
	@TableField(value = "COUNTY")
	@Length(max = 32, message = "county县不能超过32个字符")
	private String county;
	/**
	 * 地址
	 */
	@TableField(value = "ADDRESS")
	@Length(max = 128, message = "address地址不能超过128个字符")
	private String address;
	/**
	 * 后台首页路径
	 */
	@TableField(value = "INDEX_PATH")
	@Length(max = 64, message = "indexPath后台首页路径不能超过64个字符")
	private String indexPath;

	/**
	 * 系统层级100:初始化系统,2:开发系统
	 */
	@TableField(value = "LEVAL")
	private Integer leval;

	/**
	 * 是否初始化1:已初始化
	 */
	@TableField(value = "IS_INIT")
	private Integer isInit;



	public Company() {
		super();
	}

	/**
	 * 主键-数据库字段:COMPANY_ID
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * 主键-数据库字段:COMPANY_ID
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * 企业名称-数据库字段:COMPANY_NAME
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 企业名称-数据库字段:COMPANY_NAME
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 平台名称-数据库字段:PLATFORM_NAME
	 */
	public String getPlatformName() {
		return platformName;
	}

	/**
	 * 平台名称-数据库字段:PLATFORM_NAME
	 */
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	/**
	 * 省-数据库字段:PROVINCE
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 省-数据库字段:PROVINCE
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 市-数据库字段:CITY
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 市-数据库字段:CITY
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 县-数据库字段:COUNTY
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * 县-数据库字段:COUNTY
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * 地址-数据库字段:ADDRESS
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 地址-数据库字段:ADDRESS
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 后台首页路径-数据库字段:INDEX_PATH
	 */
	public String getIndexPath() {
		return indexPath;
	}

	/**
	 * 后台首页路径-数据库字段:INDEX_PATH
	 */
	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}

	public Integer getIsInit() {
		return isInit;
	}

	public void setIsInit(Integer isInit) {
		this.isInit = isInit;
	}

}
