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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "FOLDER_ID", type = IdType.ASSIGN_ID)
	private String folderId;


	@TableField(value = "FOLDER_NAME")
	private String folderName;

	@TableField(value = "CONFIG_ID")
	private String configId;

	@TableField(value = "PARENT_FOLDER_ID")
	private String parentFolderId;

	public FileFolder() {
		super();
	}


	public String getFolderId() {
		return folderId;
	}


	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}


	public String getFolderName() {
		return folderName;
	}


	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}


	public String getConfigId() {
		return configId;
	}


	public void setConfigId(String configId) {
		this.configId = configId;
	}


	public String getParentFolderId() {
		return parentFolderId;
	}


	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

}
