package com.easy.system.org.vo;


import java.util.List;

import com.easy.etl.core.entity.BaseEntity;

/**
 * 部门树
 * @author
 *
 */
public class OrgTreeVo extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = -97054990401902737L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 上级
	 */
	private String pid;

	private String companyId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 全称
	 */
	private String fullName;

	/**
	 * 全路径
	 */
	private String fullPath;

	private String orgCode;

	/**
	 * 是否是部门1:单位/企业,2:部门
	 */
	private int isOrg;

	private boolean disabled;

	/**
	 * 下级
	 */
	private List<OrgTreeVo> children;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public int getIsOrg() {
		return isOrg;
	}
	public void setIsOrg(int isOrg) {
		this.isOrg = isOrg;
	}
	public List<OrgTreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<OrgTreeVo> children) {
		this.children = children;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public boolean isDisabled() {
		if (getIsOrg()==2) {
			return false;
		}
		return true;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
