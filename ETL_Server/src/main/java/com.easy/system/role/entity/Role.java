package com.easy.system.role.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 角色-实体类
 *
 * @table TD_ROLE
 * @author
 * @time
 */
@TableName(value = "TD_ROLE", autoResultMap = true)
public class Role extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "R_ID", type = IdType.ASSIGN_ID)
	private String rId;


	@TableField(value = "CODE")
	private String code;



	@TableField(value = "NAME")
	private String name;

	@TableField(value = "COMPANY_ID")
	private String companyId;


	@TableField(value = "ORG_ID")
	private String orgId;


	@TableField(value = "STATE")
	private Integer state;


	@TableField(value = "INDEX_PATH")
	private String indexPath;

	public Role() {
		super();
	}


	public String getRId() {
		return rId;
	}


	public void setRId(String rId) {
		this.rId = rId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public String getIndexPath() {
		return indexPath;
	}


	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
