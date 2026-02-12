package com.easy.system.company.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.company.dto.CompanyFunctionDTO;
import com.easy.system.company.dto.InitCompanyDTO;
import com.easy.system.company.entity.Company;
import com.easy.system.company.entity.CompanyInfo;
import com.easy.system.company.vo.CompanyVo;

/**
 * 企业-服务类
 *
 * @author
 * @time
 */
public interface ICompanyService extends IBaseService<Company> {

	/**
	   * 初始化企业信息
	 * @param param
	 * @return
	 */
	public boolean initCompany(InitCompanyDTO param);

	/**
	 * 企业部门下拉选择
	 *
	 * @param param
	 * @return
	 */
	public List<CompanyVo> queryCompanyDropDown(CompanyDTO param);

	// ================代码生成器生成代码开始=================
	/**
	 * 保存或修改Entity
	 *
	 * @param company
	 */
	public boolean saveOrUpdateCompany(Company company, CompanyInfo info, List<CompanyFunctionDTO> menu);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<CompanyVo> queryCompanyPage(IPage<CompanyVo> page, CompanyDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<CompanyVo> queryCompanyAll(CompanyDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	public CompanyVo queryCompanyByCondition(CompanyDTO param);
	// ================代码生成器生成代码结束=================
}
