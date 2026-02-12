package com.easy.system.org.service;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.org.entity.Org;
import com.easy.system.org.vo.OrgDropDownVo;
import com.easy.system.org.vo.OrgTreeVo;
import com.easy.system.org.vo.OrgVo;

/**
 * 机构-服务类
 * @author
 * @time
 */
public interface  IOrgService extends IBaseService<Org> {

	/**
	  *   部门下拉树
	 * @param param
	 * @return
	 */
	List<OrgDropDownVo> queryOrgDropDown(OrgDTO  param);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param org
	 */
	public boolean saveOrUpdateOrg(Org org);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<OrgVo> queryOrgPage(IPage<OrgVo> page,OrgDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<OrgTreeVo> queryOrgAll(CompanyDTO companyDTO,OrgDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public OrgVo queryOrgByCondition(OrgDTO param);
	//================代码生成器生成代码结束=================
}
