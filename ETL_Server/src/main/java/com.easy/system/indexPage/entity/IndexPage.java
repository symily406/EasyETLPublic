package com.easy.system.indexPage.entity;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

import cn.hutool.db.Entity;

/**
 * 系统首页-实体类
 *
 * @table TD_SYSTEM_INDEX_PAGE
 * @author
 * @time
 */
@TableName(value = "TD_SYSTEM_INDEX_PAGE", autoResultMap = true)
public class IndexPage extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "MENU_ID", type = IdType.ASSIGN_ID)
	private String menuId;



	@TableField(value = "MENU_PATH")
	private String menuPath;

	@TableField(value = "MENU_NAME")
	private String menuName;

	@TableField(value = "TITLE")
	private String title;

	@TableField(value = "ROUTE_PATH")
	private String routePath;

	@TableField(value = "PAGE_NAME")
	private String pageName;

	@TableField(value = "URL")
	private String url;

	@TableField(value = "IS_EXTERNAL")
	private Integer isExternal;

	@TableField(value = "ENABLE")
	private Integer enable;

	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;

	public IndexPage() {
		super();
	}


	public String getMenuId() {
		return menuId;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}



	public String getMenuPath() {
		return menuPath;
	}


	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}



	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getRoutePath() {
		return routePath;
	}


	public void setRoutePath(String routePath) {
		this.routePath = routePath;
	}


	public String getPageName() {
		return pageName;
	}


	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}



	public Integer getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal;
	}



	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}


	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

}
