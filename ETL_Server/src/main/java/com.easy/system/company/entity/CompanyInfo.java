package com.easy.system.company.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 企业简介-实体类
 *
 * @table TD_COMPANY_INFO
 * @author
 * @time
 */
@TableName(value = "TD_COMPANY_INFO", autoResultMap = true)
public class CompanyInfo extends Model<CompanyInfo>  implements Serializable {

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
	 * 简介
	 */
	@TableField(value = "CONTENT")
	@NotEmpty(message = "content简介不能为空")
	private String content;

	public CompanyInfo() {
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
	 * 简介-数据库字段:CONTENT
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 简介-数据库字段:CONTENT
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
