package com.easy.system.company.vo;

import com.easy.system.company.entity.Company;

/**
*  企业-视图实体类
 * @author
 * @time
 */
public class CompanyVo extends Company {


	/**
	 *
	 */
	private static final long serialVersionUID = -8025017268467372407L;

	private String content;

	private String provinceText;

	private String cityText;

	private String countyText;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProvinceText() {
		return provinceText;
	}

	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}

	public String getCityText() {
		return cityText;
	}

	public void setCityText(String cityText) {
		this.cityText = cityText;
	}

	public String getCountyText() {
		return countyText;
	}

	public void setCountyText(String countyText) {
		this.countyText = countyText;
	}
}
