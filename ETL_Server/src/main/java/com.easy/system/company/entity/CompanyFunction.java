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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "CF_ID", type = IdType.ASSIGN_ID)
	private String cfId;


	@TableField(value = "COMPANY_ID")
	private String companyId;

	@TableField(value = "F_ID")
	private String fId;


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


	public String getCfId() {
		return cfId;
	}


	public void setCfId(String cfId) {
		this.cfId = cfId;
	}


	public String getCompanyId() {
		return companyId;
	}


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
