package com.easy.system.company.service;


import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.company.entity.CompanyInfo;

/**
 * 企业简介-服务类
 * @author
 * @time
 */
public interface  ICompanyInfoService extends IBaseService<CompanyInfo> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param companyInfo
	 */
	public boolean saveOrUpdateCompanyInfo(CompanyInfo companyInfo);

	//================代码生成器生成代码结束=================
}
