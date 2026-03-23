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


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "CONFIG_ID", type = IdType.ASSIGN_ID)
	private String configId;


	@TableField(value = "FILE_TYPE")
	private String fileType;

	@TableField(value = "SAVE_TYPE")
	private String saveType;

	@TableField(value = "NAME")
	private String name;

	@TableField(value = "ACCEPT")
	private String accept;

	@TableField(value = "CHUNK_SIZE")
	private Integer chunkSize;


	@TableField(value = "LEVAL")
	private Integer leval;


	public FileConfig() {
		super();
	}


	public String getConfigId() {
		return configId;
	}


	public void setConfigId(String configId) {
		this.configId = configId;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getSaveType() {
		return saveType;
	}


	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAccept() {
		return accept;
	}


	public void setAccept(String accept) {
		this.accept = accept;
	}


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
