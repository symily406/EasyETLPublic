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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "R_ID", type = IdType.ASSIGN_ID)
	private String rId;

	/**
	 * 角色编码
	 */
	@TableField(value = "CODE")
	@NotEmpty(message = "code角色编码不能为空")
	@Length(max = 32, message = "code角色编码不能超过32个字符")
	private String code;


	/**
	 * 角色名称
	 */
	@TableField(value = "NAME")
	@NotEmpty(message = "name角色名称不能为空")
	@Length(max = 64, message = "name角色名称不能超过64个字符")
	private String name;
	/**
	 * 归属企业
	 */
	@TableField(value = "COMPANY_ID")
	@Length(max = 32, message = "companyId归属企业不能超过32个字符")
	private String companyId;

	/**
	 * 归属部门
	 */
	@TableField(value = "ORG_ID")
	@Length(max = 32, message = "orgId部门不能超过32个字符")
	private String orgId;

	/**
	 * 状态
	 */
	@TableField(value = "STATE")
	private Integer state;

	/**
	 * 首页路径
	 */
	@TableField(value = "INDEX_PATH")
	@Length(max = 128, message = "indexPath首页路径不能超过128个字符")
	private String indexPath;

	public Role() {
		super();
	}

	/**
	 * 主键-数据库字段:R_ID
	 */
	public String getRId() {
		return rId;
	}

	/**
	 * 主键-数据库字段:R_ID
	 */
	public void setRId(String rId) {
		this.rId = rId;
	}

	/**
	 * 角色名称-数据库字段:NAME
	 */
	public String getName() {
		return name;
	}

	/**
	 * 角色名称-数据库字段:NAME
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * 状态-数据库字段:STATE
	 */
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 首页路径-数据库字段:INDEX_PATH
	 */
	public String getIndexPath() {
		return indexPath;
	}

	/**
	 * 首页路径-数据库字段:INDEX_PATH
	 */
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
