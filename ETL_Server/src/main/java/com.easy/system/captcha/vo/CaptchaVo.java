package com.easy.system.captcha.vo;

import java.io.Serializable;

/**
 * 验证码
 *
 * @author
 *
 */
public class CaptchaVo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4616835497065764316L;
	/**
	 * 键
	 */
	private String code;
	/**
	 * 值
	 */
	private String image;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
