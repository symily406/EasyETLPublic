package com.easy.system.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.company.dto.CompanyFunctionDTO;
import com.easy.system.company.entity.CompanyFunction;
import com.easy.system.company.vo.CompanyFunctionVo;

/**
 * 企业功能-接口
 *
 * @author
 * @time
 */
@Mapper
public interface CompanyFunctionMapper extends MapperDao<CompanyFunction> {

	// =============代码生成器生成代码开始==============
	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<CompanyFunctionVo> queryCompanyFunctionAll(@Param("param") CompanyFunctionDTO param);
	// =============代码生成器生成代码结束==============
}
