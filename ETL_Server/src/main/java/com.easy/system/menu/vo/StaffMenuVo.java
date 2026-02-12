package com.easy.system.menu.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 */
public class StaffMenuVo implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 3779781338696390216L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 上级ID
	 */
	private String pid;

	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 路由地址
	 */
	private String routePath;
	/**
	 * 页面名称
	 */
	private String pageName;
	/**
	 * 访问地址
	 */
	private String url;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 是否菜单
	 */
	private boolean isMenu;
	/**
	 * 是否可以关闭
	 */
	private boolean isClose;
	/**
	 * 是否外部地址
	 */
	private boolean isExternal;
	/**
	 * 是否隐藏
	 */
	private boolean isHidden;


	/**
	 * 下级菜单或权限
	 */
	private List<StaffMenuVo> children;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 上级ID-数据库字段:PID
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * 上级ID-数据库字段:PID
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * 菜单名称-数据库字段:MENU_NAME
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * 菜单名称-数据库字段:MENU_NAME
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}



	/**
	 * 标题-数据库字段:TITLE
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 标题-数据库字段:TITLE
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 路由地址-数据库字段:ROUTE_PATH
	 */
	public String getRoutePath() {
		return routePath;
	}

	/**
	 * 路由地址-数据库字段:ROUTE_PATH
	 */
	public void setRoutePath(String routePath) {
		this.routePath = routePath;
	}

	/**
	 * 页面名称-数据库字段:PAGE_NAME
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * 页面名称-数据库字段:PAGE_NAME
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * 访问地址-数据库字段:URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 访问地址-数据库字段:URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 图标-数据库字段:ICON
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 图标-数据库字段:ICON
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 是否菜单-数据库字段:IS_MENU
	 */
	public boolean getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}

	/**
	 * 是否可以关闭-数据库字段:IS_CLOSE
	 */
	public boolean getIsClose() {
		return isClose;
	}

//	public void setIsClose(Integer isClose) {
//		this.isClose = false;
//		if(isClose==1) {
//			this.isClose=true;
//		}
//	}
	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
	}

	/**
	 * 是否外部地址-数据库字段:IS_EXTERNAL
	 */
	public boolean getIsExternal() {
		return isExternal;
	}

//	public void setIsExternal(Integer isExternal) {
//		this.isExternal = false;
//		if(isExternal==1) {
//			this.isExternal=true;
//		}
//	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	/**
	 * 是否隐藏-数据库字段:IS_HIDDEN
	 */
	public boolean getIsHidden() {
		return isHidden;
	}

//	public void setIsHidden(Integer isHidden) {
//		this.isHidden = false;
//		if(isHidden==1) {
//			this.isHidden=true;
//		}
//	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden=isHidden;
	}


	public List<StaffMenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<StaffMenuVo> children) {
		this.children = children;
	}


}
