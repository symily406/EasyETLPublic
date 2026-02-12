package com.easy.system.file.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 用户资源-实体类
 *
 * @table TF_USER_RESOURCES
 * @author
 * @time
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "TF_USER_RESOURCES", autoResultMap = true)
public class UserResources extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;

	/**
	 * 名称
	 */
	@TableField(value = "NAME")
	@NotEmpty(message = "name名称不能为空")
	@Length(max = 512, message = "name名称不能超过512个字符")
	private String name;
	/**
	 * 上级ID
	 */
	@TableField(value = "PARENT_ID")
	@NotEmpty(message = "parentId上级ID不能为空")
	@Length(max = 32, message = "parentId上级ID不能超过32个字符")
	private String parentId;
	/**
	 * 0:文件夹1:图片,2:音频,3:视频,4:文件
	 */
	@TableField(value = "SOURCE_TYPE")
	private String sourceType;
	/**
	 * 文件ID
	 */
	@TableField(value = "FILE_ID")
	@Length(max = 32, message = "fileId文件ID不能超过32个字符")
	private String fileId;
	/**
	 * 全路径
	 */
	@TableField(value = "FULL_PATH")
	@Length(max = 512, message = "fullPath全路径不能超过512个字符")
	private String fullPath;
	/**
	 * 启用
	 */
	@TableField(value = "ENABLE")
	private Integer enable;
	/**
	 * 显示排序
	 */
	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;

	/**
	 * 显示排序
	 */
	@TableField(value = "LEVAL")
	private Integer leval;

	public UserResources() {
		super();
	}

	/**
	 * 主键-数据库字段:ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 主键-数据库字段:ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 名称-数据库字段:NAME
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称-数据库字段:NAME
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 上级ID-数据库字段:PARENT_ID
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 上级ID-数据库字段:PARENT_ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * 文件ID-数据库字段:FILE_ID
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * 文件ID-数据库字段:FILE_ID
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * 全路径-数据库字段:FULL_PATH
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * 全路径-数据库字段:FULL_PATH
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * 启用-数据库字段:ENABLE
	 */
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	/**
	 * 显示排序-数据库字段:SHOW_ORDER
	 */
	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Integer getLeval() {
		if (StringUtils.isBlank(getFullPath())) {
			return 0;
		}
		return StringUtils.split(getFullPath(),"|").length;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}

}
