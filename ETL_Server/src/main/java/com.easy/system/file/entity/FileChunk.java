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

	/**
	 *
	 */
	private static final long serialVersionUID = 1492831266398250588L;

	/**
	 * 主键
	 */
	@TableId(value = "ID", type = IdType.ASSIGN_ID)
	private String id;

	@TableField(value = "PARENT_ID")
	private String parentId;

	/**
	 * 每个文件的唯一标示
	 */
	@TableField(value = "IDENTIFIER")
	@Length(max = 32, message = "identifier每个文件的唯一标示不能超过32个字符")
	private String identifier;
	/**
	 * 文件MD5
	 */
	@TableField(value = "MD5")
	@Length(max = 32, message = "md5文件MD5不能超过32个字符")
	private String md5;
	/**
	 * 分片数
	 */
	@TableField(value = "CHUNK_NUMBER")
	@NotEmpty(message = "chunkNumber分片数不能为空")
	private Integer chunkNumber;
	/**
	 * 分片总数
	 */
	@TableField(value = "TOTAL_CHUNKS")
	@NotEmpty(message = "totalChunks分片总数不能为空")
	private Integer totalChunks;
	/**
	 * 分块大小
	 */
	@TableField(value = "CHUNK_SIZE")
	@NotEmpty(message = "chunkSize分块大小不能为空")
	private Integer chunkSize;
	/**
	 * 当前块的大小
	 */
	@TableField(value = "CURRENT_CHUNK_SIZE")
	@NotEmpty(message = "currentChunkSize当前块的大小不能为空")
	private Integer currentChunkSize;
	/**
	 * 文件总大小
	 */
	@TableField(value = "TOTAL_SIZE")
	@NotEmpty(message = "totalSize文件总大小不能为空")
	private Integer totalSize;
	/**
	 * 文件名
	 */
	@TableField(value = "FILE_NAME")
	@Length(max = 512, message = "fileName文件名不能超过512个字符")
	private String fileName;

	/**
	 * 分片保存路径
	 */
	@TableField(value = "SAVE_PATH")
	@Length(max = 1024, message = "savePath分片保存路径不能超过1024个字符")
	private String savePath;
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


	public FileChunk() {
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
	 * 每个文件的唯一标示-数据库字段:IDENTIFIER
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * 每个文件的唯一标示-数据库字段:IDENTIFIER
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * 文件MD5-数据库字段:MD5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * 文件MD5-数据库字段:MD5
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}

	/**
	 * 分片数-数据库字段:CHUNK_NUMBER
	 */
	public Integer getChunkNumber() {
		return chunkNumber;
	}

	public void setChunkNumber(Integer chunkNumber) {
		this.chunkNumber = chunkNumber;
	}

	/**
	 * 分片总数-数据库字段:TOTAL_CHUNKS
	 */
	public Integer getTotalChunks() {
		return totalChunks;
	}

	public void setTotalChunks(Integer totalChunks) {
		this.totalChunks = totalChunks;
	}

	/**
	 * 分块大小-数据库字段:CHUNK_SIZE
	 */
	public Integer getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(Integer chunkSize) {
		this.chunkSize = chunkSize;
	}

	/**
	 * 当前块的大小-数据库字段:CURRENT_CHUNK_SIZE
	 */
	public Integer getCurrentChunkSize() {
		return currentChunkSize;
	}

	public void setCurrentChunkSize(Integer currentChunkSize) {
		this.currentChunkSize = currentChunkSize;
	}

	/**
	 * 文件总大小-数据库字段:TOTAL_SIZE
	 */
	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
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
