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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "FILE_ID", type = IdType.ASSIGN_ID)
	private String fileId;

	/**
	 * MD5
	 */
	@TableField(value = "MD5")
	@Length(max = 32, message = "md5MD5不能超过32个字符")
	private String md5;
	/**
	 * 文件名
	 */
	@TableField(value = "FILE_NAME")
	@NotEmpty(message = "fileName文件名不能为空")
	@Length(max = 512, message = "fileName文件名不能超过512个字符")
	private String fileName;
	/**
	 * 保存文件夹
	 */
	@TableField(value = "SAVE_FOLDER")
	@NotEmpty(message = "saveFolder保存文件夹不能为空")
	@Length(max = 32, message = "saveFolder保存文件夹不能超过32个字符")
	private String saveFolder;
	/**
	 * 文件大小
	 */
	@TableField(value = "FILE_SIZE")
	@NotEmpty(message = "fileSize文件大小不能为空")
	private Integer fileSize;
	/**
	 * 保存文件名
	 */
	@TableField(value = "SAVE_NAME")
	@NotEmpty(message = "saveName保存文件名不能为空")
	@Length(max = 64, message = "saveName保存文件名不能超过64个字符")
	private String saveName;
	/**
	 * 文件后缀
	 */
	@TableField(value = "SUFFIX")
	@Length(max = 32, message = "suffix文件后缀不能超过32个字符")
	private String suffix;
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
	 * 是否系统图标
	 */
	@TableField(value = "IS_SYSTEM")
	private Integer isSystem;

	public FileInfo() {
		super();
	}

	/**
	 * 主键-数据库字段:FILE_ID
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * 主键-数据库字段:FILE_ID
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * MD5-数据库字段:MD5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * MD5-数据库字段:MD5
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * 文件名-数据库字段:FILE_NAME
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 文件名-数据库字段:FILE_NAME
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 保存文件夹-数据库字段:SAVE_FOLDER
	 */
	public String getSaveFolder() {
		return saveFolder;
	}

	/**
	 * 保存文件夹-数据库字段:SAVE_FOLDER
	 */
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	/**
	 * 文件大小-数据库字段:FILE_SIZE
	 */
	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 保存文件名-数据库字段:SAVE_NAME
	 */
	public String getSaveName() {
		return saveName;
	}

	/**
	 * 保存文件名-数据库字段:SAVE_NAME
	 */
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	/**
	 * 文件后缀-数据库字段:SUFFIX
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * 文件后缀-数据库字段:SUFFIX
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}
