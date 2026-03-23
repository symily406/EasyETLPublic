package com.easy.system.menu.entity;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.Entity;

/**
 * 系统菜单-实体类
 *
 * @table TD_MENU
 * @author
 * @time
 */
@TableName(value = "TD_MENU", autoResultMap = true)
public class Menu extends Entity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "MENU_ID", type = IdType.INPUT)
	private String menuId;


	@TableField(value = "PID")
	private String pid;

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}


	@TableField(value = "MENU_PATH")
	private String menuPath;


	@TableField(value = "MENU_NAME")
	private String menuName;

	@TableField(value = "SHOW_MENU_NAME")
	private String showMenuName;

	@TableField(value = "TITLE")
	private String title;

	@TableField(value = "ROUTE_PATH")
	private String routePath;

	@TableField(value = "PAGE_NAME")
	private String pageName;

	@TableField(value = "URL")
	private String url;

	@TableField(value = "ICON")
	private String icon;

	@TableField(value = "IS_MENU")
	private Integer isMenu;

	@TableField(value = "IS_CLOSE")
	private Integer isClose;

	@TableField(value = "IS_EXTERNAL")
	private Integer isExternal;

	@TableField(value = "IS_HIDDEN")
	private Integer isHidden;

	public Menu() {
		super();
	}


	public String getMenuId() {
		return menuId;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public String getShowMenuName() {
		return showMenuName;
	}


	public void setShowMenuName(String showMenuName) {
		this.showMenuName = showMenuName;
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


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public Integer getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}


	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}


	public Integer getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal;
	}


	public Integer getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Integer isHidden) {
		this.isHidden = isHidden;
	}

}
