package com.easy.system.file.entity;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;

/**
 * 文件上传分片-实体类
 *
 * @table TM_FILE_CHUNK
 * @author
 * @time
 */
@TableName(value = "TM_FILE_CHUNK", autoResultMap = true)
public class FileChunk extends BaseEntity {


	private static final long serialVersionUID = 1492831266398250588L;


	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;

	@TableField(value = "PARENT_ID")
	private String parentId;


	@TableField(value = "IDENTIFIER")
	private String identifier;

	@TableField(value = "MD5")
	private String md5;

	@TableField(value = "CHUNK_NUMBER")
	private Integer chunkNumber;

	@TableField(value = "TOTAL_CHUNKS")
	private Integer totalChunks;

	@TableField(value = "CHUNK_SIZE")
	private Integer chunkSize;

	@TableField(value = "CURRENT_CHUNK_SIZE")
	private Integer currentChunkSize;

	@TableField(value = "TOTAL_SIZE")
	private Integer totalSize;

	@TableField(value = "FILE_NAME")
	private String fileName;


	@TableField(value = "SAVE_PATH")
	private String savePath;

	@TableField(value = "ENABLE")
	private Integer enable;

	@TableField(value = "SHOW_ORDER")
	private Integer showOrder;


	public FileChunk() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdentifier() {
		return identifier;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public String getMd5() {
		return md5;
	}


	public void setMd5(String md5) {
		this.md5 = md5;
	}


	public Integer getChunkNumber() {
		return chunkNumber;
	}

	public void setChunkNumber(Integer chunkNumber) {
		this.chunkNumber = chunkNumber;
	}


	public Integer getTotalChunks() {
		return totalChunks;
	}

	public void setTotalChunks(Integer totalChunks) {
		this.totalChunks = totalChunks;
	}


	public Integer getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(Integer chunkSize) {
		this.chunkSize = chunkSize;
	}


	public Integer getCurrentChunkSize() {
		return currentChunkSize;
	}

	public void setCurrentChunkSize(Integer currentChunkSize) {
		this.currentChunkSize = currentChunkSize;
	}


	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



}
