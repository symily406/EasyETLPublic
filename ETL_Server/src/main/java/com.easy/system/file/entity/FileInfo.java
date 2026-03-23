package com.easy.system.file.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 附件-实体类
 *
 * @table TD_FILE_INFO
 * @author
 * @time
 */
@TableName(value = "TD_FILE_INFO", autoResultMap = true)
public class FileInfo extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "FILE_ID", type = IdType.ASSIGN_ID)
	private String fileId;


	@TableField(value = "MD5")
	private String md5;

	@TableField(value = "FILE_NAME")
	private String fileName;

	@TableField(value = "SAVE_FOLDER")
	private String saveFolder;

	@TableField(value = "FILE_SIZE")
	private Integer fileSize;

	@TableField(value = "SAVE_NAME")
	private String saveName;

	@TableField(value = "SUFFIX")
	private String suffix;

	@TableField(value = "ENABLE")
	private Integer enable;

	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;


	@TableField(value = "IS_SYSTEM")
	private Integer isSystem;

	public FileInfo() {
		super();
	}


	public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}


	public String getMd5() {
		return md5;
	}


	public void setMd5(String md5) {
		this.md5 = md5;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getSaveFolder() {
		return saveFolder;
	}


	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}


	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}


	public String getSaveName() {
		return saveName;
	}


	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}
