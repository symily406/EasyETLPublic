package com.easy.system.file.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sun.scenario.effect.impl.prism.PrImage;
import com.easy.system.file.entity.FileConfig;

/**
*  文件上传配置-数据传输对象实体类
 * @author
 * @time
 */
public class FileConfigDTO extends FileConfig{

	/**
	 *
	 */
	private static final long serialVersionUID = -8465697295544830929L;


	/**
	 * 归属企业
	 */
	private String companyId;
	/**
	 * 归属机构
	 */
	private String orgId;

	private List<String> queryFileTypes;

	public String getCompanyId() {
		if (StringUtils.isBlank(this.companyId)) {
			this.companyId=getUserCompanyId();
		}
		return this.companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrgId() {
		if (StringUtils.isBlank(this.orgId)) {
			this.orgId=getUserOrgId();
		}
		return this.orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<String> getQueryFileTypes() {
		return queryFileTypes;
	}
	public void setQueryFileTypes(List<String> queryFileTypes) {
		this.queryFileTypes = queryFileTypes;
	}


}
