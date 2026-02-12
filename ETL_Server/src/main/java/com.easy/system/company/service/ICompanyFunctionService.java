package com.easy.system.company.service;


import java.util.List;

import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.company.dto.CompanyFunctionDTO;
import com.easy.system.company.entity.CompanyFunction;
import com.easy.system.company.vo.CompanyFunctionVo;

/**
 * 企业功能-服务类
 * @author
 * @time
 */
public interface  ICompanyFunctionService extends IBaseService<CompanyFunction> {

	//================代码生成器生成代码开始=================


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<CompanyFunctionVo> queryCompanyFunctionAll(CompanyFunctionDTO param);


	//================代码生成器生成代码结束=================
}
