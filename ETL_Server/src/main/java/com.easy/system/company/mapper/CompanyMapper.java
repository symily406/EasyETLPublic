package com.easy.system.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.company.entity.Company;
import com.easy.system.company.vo.CompanyVo;

/**
 * 企业-接口
 *
 * @author
 * @time
 */
@Mapper
public interface CompanyMapper extends MapperDao<Company> {

	/**
	 * 企业部门下拉选择
	 *
	 * @param param
	 * @return
	 */
	List<CompanyVo> queryCompanyDropDown(@Param("param") CompanyDTO param);

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<CompanyVo> queryCompanyPage(IPage<CompanyVo> page, @Param("param") CompanyDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<CompanyVo> queryCompanyAll(@Param("param") CompanyDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	CompanyVo queryCompanyByCondition(@Param("param") CompanyDTO param);
	// =============代码生成器生成代码结束==============
}
