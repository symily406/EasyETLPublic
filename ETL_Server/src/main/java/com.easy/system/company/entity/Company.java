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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "COMPANY_ID", type = IdType.INPUT)
	private String companyId;


	@TableField(value = "COMPANY_NAME")
	private String companyName;

	@TableField(value = "PLATFORM_NAME")
	private String platformName;

	@TableField(value = "PROVINCE")
	private String province;

	@TableField(value = "CITY")
	private String city;

	@TableField(value = "COUNTY")
	private String county;

	@TableField(value = "ADDRESS")
	private String address;

	@TableField(value = "INDEX_PATH")
	private String indexPath;


	@TableField(value = "LEVAL")
	private Integer leval;


	@TableField(value = "IS_INIT")
	private Integer isInit;



	public Company() {
		super();
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getPlatformName() {
		return platformName;
	}


	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCounty() {
		return county;
	}


	public void setCounty(String county) {
		this.county = county;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getIndexPath() {
		return indexPath;
	}


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
