package com.easy.system.company.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.company.entity.CompanyInfo;
import com.easy.system.company.mapper.CompanyInfoMapper;
import com.easy.system.company.service.ICompanyInfoService;

/**
 * 企业简介-服务类
 * @author
 * @time
 */
@Service
public class CompanyInfoServiceImpl extends BaseServiceImpl<CompanyInfoMapper, CompanyInfo> implements ICompanyInfoService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateCompanyInfo(CompanyInfo companyInfo) {
		return saveOrUpdate(companyInfo);
	}
}
