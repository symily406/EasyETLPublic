package com.easy.system.role.vo;

import java.util.List;

import com.easy.etl.core.entity.BaseEntity;


/**
 * 权限树
 *
 * @author
 */
public class RoleTreeVo extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 8804535491763668539L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 所属企业编码
	 */
	private String companyId;
	/**
	 * 所在部门名称
	 */
	private String orgId;
	/**
	 * 层级1:企烽,2:部门,3:权限
	 */
	private Integer leval;



	private String indexPath;

	private boolean disabled;

	private List<RoleTreeVo> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}

	public List<RoleTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<RoleTreeVo> children) {
		this.children = children;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDisabled() {
		if (getLeval()==3) {
			return false;
		}
		return true;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
