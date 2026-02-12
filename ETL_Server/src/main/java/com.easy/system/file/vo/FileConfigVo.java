package com.easy.system.file.vo;

import com.easy.system.file.entity.FileConfig;

/**
*  文件上传配置-视图实体类
 * @author
 * @time
 */
public class FileConfigVo extends FileConfig {

	/**
	 *
	 */
	private static final long serialVersionUID = -5521730529035816772L;


	private String fileTypeName;

	private String saveTypeName;

	public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public String getSaveTypeName() {
		return saveTypeName;
	}

	public void setSaveTypeName(String saveTypeName) {
		this.saveTypeName = saveTypeName;
	}


}
