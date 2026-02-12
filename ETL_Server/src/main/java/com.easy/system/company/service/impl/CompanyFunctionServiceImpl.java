package com.easy.system.company.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.company.dto.CompanyFunctionDTO;
import com.easy.system.company.entity.CompanyFunction;
import com.easy.system.company.mapper.CompanyFunctionMapper;
import com.easy.system.company.service.ICompanyFunctionService;
import com.easy.system.company.vo.CompanyFunctionVo;

/**
 * 企业功能-服务类
 * @author
 * @time
 */
@Service
public class CompanyFunctionServiceImpl extends BaseServiceImpl<CompanyFunctionMapper, CompanyFunction> implements ICompanyFunctionService {


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<CompanyFunctionVo> queryCompanyFunctionAll(CompanyFunctionDTO param) {
		return baseMapper.queryCompanyFunctionAll(param);
	}


}
