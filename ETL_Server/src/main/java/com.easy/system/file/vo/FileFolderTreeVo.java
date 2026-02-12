package com.easy.system.file.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
     * 文件夹树
 * @author
 *
 */
public class FileFolderTreeVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6611787134282607592L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	private String label;

	/**
	 * 配置ID
	 */
	private String configId;

	/**
	 * 文件夹ID
	 */
	private String folderId;

	/**
	 * 上级文件夹
	 */
	private String parentFolderId;

	/**
	 * 文件类型
	 */
	private String fileType;

	/**
	 * 允许上传类型
	 */
	private String accept;

	/**
	 * 分片上传大小
	 */
	private Integer chunkSize;

	/**
	 * 是否文件夹
	 */
	private Integer isFolder;

	/**
	 * 下级分类
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<FileFolderTreeVo> children;

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

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public List<FileFolderTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<FileFolderTreeVo> children) {
		this.children = children;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	public String getLabel() {
		return getName();
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(Integer isFolder) {
		this.isFolder = isFolder;
	}

	public String getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

}
