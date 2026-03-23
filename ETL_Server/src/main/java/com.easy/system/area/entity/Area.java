package com.easy.system.area.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 地区表-实体类
 *
 * @table TD_AREA
 * @author
 * @time
 */
@TableName(value = "TD_AREA", autoResultMap = true)
public class Area extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "AREA_ID", type = IdType.ASSIGN_ID)
	private String areaId;


	@TableField(value = "P_AREA_ID")
	private String pAreaId;

	@TableField(value = "AREA_CODE")
	private String areaCode;

	@TableField(value = "AREA_NAME")
	private String areaName;

	@TableField(value = "FULL_PATH")
	private String fullPath;

	@TableField(value = "AREA_LEVEL")
	private Integer areaLevel;

	@TableField(value = "FIRST_CODE")
	private String firstCode;

	@TableField(value = "ID_CARD_CODE")
	private String idCardCode;

	public Area() {
		super();
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public String getPAreaId() {
		return pAreaId;
	}


	public void setPAreaId(String pAreaId) {
		this.pAreaId = pAreaId;
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


	public String getFullPath() {
		return fullPath;
	}


	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}


	public Integer getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}


	public String getFirstCode() {
		return firstCode;
	}


	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}


	public String getIdCardCode() {
		return idCardCode;
	}


	public void setIdCardCode(String idCardCode) {
		this.idCardCode = idCardCode;
	}

}
