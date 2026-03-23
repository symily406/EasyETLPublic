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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;


	@TableField(value = "NAME")
	private String name;

	@TableField(value = "PARENT_ID")
	private String parentId;

	@TableField(value = "SOURCE_TYPE")
	private String sourceType;

	@TableField(value = "FILE_ID")
	private String fileId;

	@TableField(value = "FULL_PATH")
	private String fullPath;

	@TableField(value = "ENABLE")
	private Integer enable;

	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;


	@TableField(value = "LEVAL")
	private Integer leval;

	public UserResources() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getFullPath() {
		return fullPath;
	}


	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
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
