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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "MENU_ID", type = IdType.INPUT)
	private String menuId;

	/**
	 * 上级ID
	 */
	@TableField(value = "PID")
	@Length(max = 32, message = "pid上级ID不能超过32个字符")
	private String pid;

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	/**
	 * 菜单全路径
	 */
	@TableField(value = "MENU_PATH")
	@Length(max = 128, message = "menuPath不能超过128个字符")
	private String menuPath;

	/**
	 * 菜单名称
	 */
	@TableField(value = "MENU_NAME")
	@Length(max = 32, message = "menuName菜单名称不能超过32个字符")
	private String menuName;
	/**
	 * 菜单显示名称
	 */
	@TableField(value = "SHOW_MENU_NAME")
	@Length(max = 32, message = "showMenuName菜单显示名称不能超过32个字符")
	private String showMenuName;
	/**
	 * 标题
	 */
	@TableField(value = "TITLE")
	@Length(max = 32, message = "title标题不能超过32个字符")
	private String title;
	/**
	 * 路由地址
	 */
	@TableField(value = "ROUTE_PATH")
	@Length(max = 64, message = "routePath路由地址不能超过64个字符")
	private String routePath;
	/**
	 * 页面名称
	 */
	@TableField(value = "PAGE_NAME")
	@Length(max = 32, message = "pageName页面名称不能超过32个字符")
	private String pageName;
	/**
	 * 访问地址
	 */
	@TableField(value = "URL")
	@Length(max = 512, message = "url访问地址不能超过512个字符")
	private String url;
	/**
	 * 图标
	 */
	@TableField(value = "ICON")
	@Length(max = 32, message = "icon图标不能超过32个字符")
	private String icon;
	/**
	 * 是否菜单
	 */
	@TableField(value = "IS_MENU")
	private Integer isMenu;
	/**
	 * 是否可以关闭
	 */
	@TableField(value = "IS_CLOSE")
	private Integer isClose;
	/**
	 * 是否外部地址
	 */
	@TableField(value = "IS_EXTERNAL")
	private Integer isExternal;
	/**
	 * 是否隐藏
	 */
	@TableField(value = "IS_HIDDEN")
	private Integer isHidden;

	public Menu() {
		super();
	}

	/**
	 * 主键-数据库字段:MENU_ID
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * 主键-数据库字段:MENU_ID
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
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
	 * 菜单显示名称-数据库字段:SHOW_MENU_NAME
	 */
	public String getShowMenuName() {
		return showMenuName;
	}

	/**
	 * 菜单显示名称-数据库字段:SHOW_MENU_NAME
	 */
	public void setShowMenuName(String showMenuName) {
		this.showMenuName = showMenuName;
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
	 * 路由地址-数据库字段:ROUT_PATH
	 */
	public String getRoutePath() {
		return routePath;
	}

	/**
	 * 路由地址-数据库字段:ROUT_PATH
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
	public Integer getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	/**
	 * 是否可以关闭-数据库字段:IS_CLOSE
	 */
	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	/**
	 * 是否外部地址-数据库字段:IS_EXTERNAL
	 */
	public Integer getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal;
	}

	/**
	 * 是否隐藏-数据库字段:IS_HIDDEN
	 */
	public Integer getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Integer isHidden) {
		this.isHidden = isHidden;
	}

}
