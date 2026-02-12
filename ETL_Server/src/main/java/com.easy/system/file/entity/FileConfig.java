package com.easy.system.file.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 文件上传配置-实体类
 *
 * @table TD_FILE_CONFIG
 * @author
 * @time
 */
@TableName(value = "TD_FILE_CONFIG", autoResultMap = true)
public class FileConfig extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "CONFIG_ID", type = IdType.ASSIGN_ID)
	private String configId;

	/**
	 * 文件类型
	 */
	@TableField(value = "FILE_TYPE")
	@NotEmpty(message = "fileType文件类型不能为空")
	@Length(max = 32, message = "fileType文件类型不能超过32个字符")
	private String fileType;
	/**
	 * 文件存储方式
	 */
	@TableField(value = "SAVE_TYPE")
	@Length(max = 32, message = "saveType文件存储方式不能超过32个字符")
	private String saveType;
	/**
	 * 名称
	 */
	@TableField(value = "NAME")
	@NotEmpty(message = "name名称不能为空")
	@Length(max = 128, message = "name名称不能超过128个字符")
	private String name;
	/**
	 * 允许上传类型
	 */
	@TableField(value = "ACCEPT")
	@NotEmpty(message = "accept允许上传类型不能为空")
	@Length(max = 512, message = "accept允许上传类型不能超过512个字符")
	private String accept;
	/**
	 * 分片上传大小
	 */
	@TableField(value = "CHUNK_SIZE")
	private Integer chunkSize;

	/**
	 * 层级100系统默认层级-用于复制到其它企业
	 */
	@TableField(value = "LEVAL")
	private Integer leval;


	public FileConfig() {
		super();
	}

	/**
	 * 主键-数据库字段:CONFIG_ID
	 */
	public String getConfigId() {
		return configId;
	}

	/**
	 * 主键-数据库字段:CONFIG_ID
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}

	/**
	 * 文件类型-数据库字段:FILE_TYPE
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 文件类型-数据库字段:FILE_TYPE
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 文件存储方式-数据库字段:SAVE_TYPE
	 */
	public String getSaveType() {
		return saveType;
	}

	/**
	 * 文件存储方式-数据库字段:SAVE_TYPE
	 */
	public void setSaveType(String saveType) {
		this.saveType = saveType;
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
	 * 允许上传类型-数据库字段:ACCEPT
	 */
	public String getAccept() {
		return accept;
	}

	/**
	 * 允许上传类型-数据库字段:ACCEPT
	 */
	public void setAccept(String accept) {
		this.accept = accept;
	}

	/**
	 * 分片上传大小-数据库字段:CHUNK_SIZE
	 */
	public Integer getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(Integer chunkSize) {
		this.chunkSize = chunkSize;
	}

	public Integer getLeval() {
		return leval;
	}

	public void setLeval(Integer leval) {
		this.leval = leval;
	}


}
