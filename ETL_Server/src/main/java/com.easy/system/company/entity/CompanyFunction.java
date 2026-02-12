package com.easy.system.company.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 企业功能-实体类
 *
 * @table TD_COMPANY_FUNCTION
 * @author
 * @time
 */
@TableName(value = "TD_COMPANY_FUNCTION", autoResultMap = true)
public class CompanyFunction extends Model<CompanyFunction> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "CF_ID", type = IdType.ASSIGN_ID)
	private String cfId;

	/**
	 * 归属企业
	 */
	@TableField(value = "COMPANY_ID")
	@NotEmpty(message = "companyId归属企业不能为空")
	@Length(max = 32, message = "companyId归属企业不能超过32个字符")
	private String companyId;
	/**
	 * 功能主键
	 */
	@TableField(value = "F_ID")
	@NotEmpty(message = "fId功能主键不能为空")
	@Length(max = 32, message = "fId功能主键不能超过32个字符")
	private String fId;

	/**
	 * 层级
	 */
	@TableField(value = "LEVAL")
	private Integer leval;

	public CompanyFunction() {
		super();
	}

	public CompanyFunction(String cfId,String companyId,String fId,Integer leval) {
		this.cfId=cfId;
		this.companyId=companyId;
		this.fId=fId;
		this.leval=leval;
	}

	/**
	 * 主键-数据库字段:CF_ID
	 */
	public String getCfId() {
		return cfId;
	}

	/**
	 * 主键-数据库字段:CF_ID
	 */
	public void setCfId(String cfId) {
		this.cfId = cfId;
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


	public String getfId() {
		return fId;
	}

	public void setfId(String fId) {
		this.fId = fId;
	}

	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}



}
