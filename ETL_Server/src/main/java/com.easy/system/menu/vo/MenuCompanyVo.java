package com.easy.system.menu.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 企业菜单树
 */
public class MenuCompanyVo implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6927891742283116026L;

	private String id;
	private String label;
	private String title;
	private String name;
	private String showName;
	//1:菜单,2:权限
	private int leval;
	private List<MenuCompanyVo> children;

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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLeval() {
		return leval;
	}
	public void setLeval(int leval) {
		this.leval = leval;
	}
	public List<MenuCompanyVo> getChildren() {
		return children;
	}
	public void setChildren(List<MenuCompanyVo> children) {
		this.children = children;
	}


}
