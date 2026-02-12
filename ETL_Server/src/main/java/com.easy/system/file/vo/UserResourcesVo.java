package com.easy.system.file.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.boot.ext.kit.PathKit;
import com.spring.boot.ext.kit.StrKit;
import com.easy.etl.core.init.Config;
import com.easy.system.file.entity.UserResources;

import cn.hutool.core.io.FileUtil;

/**
*  用户资源-视图实体类
 * @author
 * @time
 */
public class UserResourcesVo extends UserResources {

	/**
	 *
	 */
	private static final long serialVersionUID = 4119060595468709630L;

	@JsonIgnore
	private String saveFolder;

	@JsonIgnore
	private String saveName;

	private String fileSize;

	/**
	 * 文件大小
	 */
	private String filePath;

	/**
	 * 文件后缀
	 */
	private String suffix;

	/**
	 * 文件名，无后缀
	 */
	private String mainName;

	private String md5;

	private Integer isSystem;

	/**
	 * 极速秒传
	 */
	private boolean speedPass;

	public String getSaveFolder() {
		return saveFolder;
	}

	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {

		if (StrKit.notBlank(getSourceType())&&Integer.parseInt(getSourceType())>0) {
			if (getIsSystem()==1){
				return PathKit.handle("/".concat(getSaveFolder()).concat("/").concat(getSaveName()));
			}
			return Config.fileHost.concat(PathKit.handle("/".concat(getSaveFolder()).concat("/").concat(getSaveName())));
		}
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getMainName() {
		return FileUtil.mainName(getName());
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	public boolean isSpeedPass() {
		return speedPass;
	}

	public void setSpeedPass(boolean speedPass) {
		this.speedPass = speedPass;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
}
