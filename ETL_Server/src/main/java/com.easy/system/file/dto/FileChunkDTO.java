package com.easy.system.file.dto;

import java.util.ArrayList;
import java.util.List;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.file.entity.FileChunk;

import cn.hutool.core.io.FileUtil;

/**
*  文件上传分片-数据传输对象实体类
 * @author
 * @time
 */
public class FileChunkDTO extends FileChunk{

	/**
	 *
	 */
	private static final long serialVersionUID = -2068402225558684822L;

	/**
	 * 分片是否存在
	 */
	private boolean skip;

	/**
	 * 文件后缀
	 */
	private String suffix;


	private String filename;

	 /**
     *已经上传的文件块编号，可以跳过，断点续传
     */
    private List<Integer> uploadedChunks;


	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}



	public List<Integer> getUploadedChunks() {
		return uploadedChunks;
	}

	public void setUploadedChunks(List<Integer> uploadedChunks) {
		this.uploadedChunks = uploadedChunks;
	}

	public String getSuffix() {
		if (StrKit.notBlank(getFilename())) {
			return FileUtil.extName(getFilename()).toLowerCase();
		}
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


}
