package com.easy.system.org.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 部门-下拉选择接口
 *
 * @author
 *
 */
public class OrgDropDownVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8765246536614152485L;

	/**
	 * 部门编码
	 */
	private String id;

	/**
	 * 部门名称
	 */
	private String label;

	/**
	 * 子部门
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<OrgDropDownVo> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<OrgDropDownVo> getChildren() {
		return children;
	}

	public void setChildren(List<OrgDropDownVo> children) {
		this.children = children;
	}

}
