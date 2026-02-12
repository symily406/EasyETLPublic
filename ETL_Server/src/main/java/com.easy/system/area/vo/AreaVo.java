package com.easy.system.area.vo;

import java.io.Serializable;

/**
 * 地区表-视图实体类
 *
 * @author
 * @time
 */
public class AreaVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1966804499052099954L;

	/**
	 * 主键
	 */
	private String areaId;

	/**
	 * 编号
	 */
	private String areaCode;
	/**
	 * 地区名
	 */
	private String areaName;
	/**
	 * 层级
	 */
	private Integer areaLevel;

	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getAreaLevel() {
		return areaLevel;
	}
	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}


}
