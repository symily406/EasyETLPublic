package com.easy.system.file.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 文件夹-实体类
 *
 * @table TD_FILE_FOLDER
 * @author
 * @time
 */
@TableName(value = "TD_FILE_FOLDER", autoResultMap = true)
public class FileFolder extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "FOLDER_ID", type = IdType.ASSIGN_ID)
	private String folderId;

	/**
	 * 文件夹名称
	 */
	@TableField(value = "FOLDER_NAME")
	@NotEmpty(message = "folderName文件夹名称不能为空")
	@Length(max = 128, message = "folderName文件夹名称不能超过128个字符")
	private String folderName;
	/**
	 * 归属文件配置
	 */
	@TableField(value = "CONFIG_ID")
	@NotEmpty(message = "configId归属文件配置不能为空")
	@Length(max = 32, message = "configId归属文件配置不能超过32个字符")
	private String configId;
	/**
	 * 上级文件夹
	 */
	@TableField(value = "PARENT_FOLDER_ID")
	@NotEmpty(message = "parentFolderId上级文件夹不能为空")
	@Length(max = 32, message = "parentFolderId上级文件夹不能超过32个字符")
	private String parentFolderId;

	public FileFolder() {
		super();
	}

	/**
	 * 文件夹-数据库字段:FOLDER_ID
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * 文件夹-数据库字段:FOLDER_ID
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * 文件夹名称-数据库字段:FOLDER_NAME
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * 文件夹名称-数据库字段:FOLDER_NAME
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * 归属文件配置-数据库字段:CONFIG_ID
	 */
	public String getConfigId() {
		return configId;
	}

	/**
	 * 归属文件配置-数据库字段:CONFIG_ID
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}

	/**
	 * 上级文件夹-数据库字段:PARENT_FOLDER_ID
	 */
	public String getParentFolderId() {
		return parentFolderId;
	}

	/**
	 * 上级文件夹-数据库字段:PARENT_FOLDER_ID
	 */
	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

}
