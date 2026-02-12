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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "AREA_ID", type = IdType.ASSIGN_ID)
	private String areaId;

	/**
	 * 上级地区
	 */
	@TableField(value = "P_AREA_ID")
	@NotEmpty(message = "pAreaId上级地区不能为空")
	@Length(max = 32, message = "pAreaId上级地区不能超过32个字符")
	private String pAreaId;
	/**
	 * 编号
	 */
	@TableField(value = "AREA_CODE")
	@Length(max = 32, message = "areaCode编号不能超过32个字符")
	private String areaCode;
	/**
	 * 地区名
	 */
	@TableField(value = "AREA_NAME")
	@NotEmpty(message = "areaName地区名不能为空")
	@Length(max = 64, message = "areaName地区名不能超过64个字符")
	private String areaName;
	/**
	 * 地区全路径
	 */
	@TableField(value = "FULL_PATH")
	@Length(max = 256, message = "fullPath地区全路径不能超过256个字符")
	private String fullPath;
	/**
	 * 层级
	 */
	@TableField(value = "AREA_LEVEL")
	@NotEmpty(message = "areaLevel层级不能为空")
	private Integer areaLevel;
	/**
	 * 首字母
	 */
	@TableField(value = "FIRST_CODE")
	@Length(max = 4, message = "firstCode首字母不能超过4个字符")
	private String firstCode;
	/**
	 * 身份证编码
	 */
	@TableField(value = "ID_CARD_CODE")
	@Length(max = 6, message = "idCardCode身份证编码不能超过6个字符")
	private String idCardCode;

	public Area() {
		super();
	}

	/**
	 * 主键-数据库字段:AREA_ID
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * 主键-数据库字段:AREA_ID
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * 上级地区-数据库字段:P_AREA_ID
	 */
	public String getPAreaId() {
		return pAreaId;
	}

	/**
	 * 上级地区-数据库字段:P_AREA_ID
	 */
	public void setPAreaId(String pAreaId) {
		this.pAreaId = pAreaId;
	}

	/**
	 * 编号-数据库字段:AREA_CODE
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 编号-数据库字段:AREA_CODE
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 地区名-数据库字段:AREA_NAME
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 地区名-数据库字段:AREA_NAME
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * 地区全路径-数据库字段:FULL_PATH
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * 地区全路径-数据库字段:FULL_PATH
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * 层级-数据库字段:AREA_LEVEL
	 */
	public Integer getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}

	/**
	 * 首字母-数据库字段:FIRST_CODE
	 */
	public String getFirstCode() {
		return firstCode;
	}

	/**
	 * 首字母-数据库字段:FIRST_CODE
	 */
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	/**
	 * 身份证编码-数据库字段:ID_CARD_CODE
	 */
	public String getIdCardCode() {
		return idCardCode;
	}

	/**
	 * 身份证编码-数据库字段:ID_CARD_CODE
	 */
	public void setIdCardCode(String idCardCode) {
		this.idCardCode = idCardCode;
	}

}
