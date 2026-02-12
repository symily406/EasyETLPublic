package com.easy.system.login.vo;

import java.io.Serializable;

public class LoginVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7692503894804481157L;

	/**
	 * 用户令牌
	 */
	private String token;

	/**
	 * 刷新用户令牌
	 */
	private String refreshToken;

	/**
	 *允许上传的文件
	 */
	private String fileAccept;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 层级
	 */
	private int leval;



	/**
	 * 首页路径
	 */
	private String routePath;
	/**
	 * 是否外部地址
	 */
	private Integer isExternal;

	/**
	 * 访问地址
	 */
	private String url;

	/**
	 * 系统名称
	 */
	private String platformName;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getFileAccept() {
		return fileAccept;
	}

	public void setFileAccept(String fileAccept) {
		this.fileAccept = fileAccept;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeval() {
		return leval;
	}

	public void setLeval(int leval) {
		this.leval = leval;
	}



	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}


	public Integer getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoutePath() {
		return routePath;
	}

	public void setRoutePath(String routePath) {
		this.routePath = routePath;
	}
}
