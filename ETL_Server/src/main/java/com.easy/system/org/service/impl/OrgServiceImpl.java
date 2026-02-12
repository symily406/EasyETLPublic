package com.easy.system.org.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.sun.java.swing.plaf.windows.WindowsTreeUI.CollapsedIcon;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.company.mapper.CompanyMapper;
import com.easy.system.company.vo.CompanyVo;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.org.entity.Org;
import com.easy.system.org.mapper.OrgMapper;
import com.easy.system.org.service.IOrgService;
import com.easy.system.org.vo.OrgDropDownVo;
import com.easy.system.org.vo.OrgTreeVo;
import com.easy.system.org.vo.OrgVo;

/**
 * 机构-服务类
 * @author
 * @time
 */
@Service
public class OrgServiceImpl extends BaseServiceImpl<OrgMapper, Org> implements IOrgService {

	@Autowired
	private CompanyMapper companyMapper;


	/**
	  *   部门下拉树
	 * @param param
	 * @return
	 */
	@Override
	public List<OrgDropDownVo> queryOrgDropDown(OrgDTO param) {
		List<OrgVo> list= baseMapper.queryOrgAll(param);
		return recursiveOrgChildren(list,list.stream().filter(v->v.getPOrgId().equals("0")).collect(Collectors.toList()));
	}

	private List<OrgDropDownVo> recursiveOrgChildren(List<OrgVo> list,List<OrgVo> childrenList){
		List<OrgDropDownVo> childrens=new ArrayList<>();
		if (childrenList!=null&&!childrenList.isEmpty()) {
			for (OrgVo orgVo : childrenList) {
				OrgDropDownVo vo=new OrgDropDownVo();
				vo.setId(orgVo.getOrgId());
				vo.setLabel(orgVo.getOrgName());
				vo.setChildren(recursiveOrgChildren(list, list.stream().filter(v->v.getPOrgId().equals(orgVo.getOrgId())).collect(Collectors.toList())));
				childrens.add(vo);
			}
		}
		return childrens;
	}



	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateOrg(Org org) {
		String orgId=org.getOrgId();
		if (StringUtils.isBlank(orgId)) {
			orgId=idBulider();
		}
		org.setOrgId(orgId);
		String orgPath=org.getOrgPath().concat("|").concat(org.getPOrgId()).concat("|").concat(org.getOrgId());
		org.setOrgPath(StrKit.clearDuplicateValue(orgPath,"|"));
		return saveOrUpdate(org);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<OrgVo> queryOrgPage(IPage<OrgVo> page, OrgDTO param) {
		return page.setRecords(baseMapper.queryOrgPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<OrgTreeVo> queryOrgAll(CompanyDTO companyDTO,OrgDTO param) {
		List<CompanyVo> list=companyMapper.queryCompanyAll(companyDTO);
		List<OrgVo> orgLists=baseMapper.queryOrgAll(param);

		List<OrgTreeVo> re=new ArrayList<>();
		list.stream().forEach(c->{
			OrgTreeVo vo=new OrgTreeVo();
			vo.setId(c.getCompanyId());
			vo.setPid("0");
			vo.setName(c.getCompanyName());
			vo.setFullName(c.getCompanyName());
			vo.setCompanyId(c.getCompanyId());
			vo.setAddTime(c.getAddTime());
			vo.setShowOrder(c.getShowOrder());
			vo.setEnable(c.getEnable());
			vo.setAllowDelete(c.getAllowDelete());
			vo.setAllowEdit(c.getAllowEdit());
			vo.setIsOrg(1);

			vo.setChildren(recursiveOrgTree(orgLists,orgLists.stream().filter(v->StringUtils.equals(v.getCompanyId(), c.getCompanyId())).collect(Collectors.toList()),null));
			re.add(vo);
		});
		return re;
	}

	/**
	 * 递归生成部门树
	 * @return
	 */
	private List<OrgTreeVo> recursiveOrgTree(List<OrgVo> orgs,List<OrgVo> childerOrgs,String pid) {
		if (childerOrgs==null||childerOrgs.isEmpty()) {
			return null;
		}
		List<OrgTreeVo> orgTreeVos=new ArrayList<>();
		childerOrgs.stream().forEach(org->{
			OrgTreeVo vo=new OrgTreeVo();
			vo.setId(org.getOrgId());
			vo.setPid(org.getPOrgId());
			vo.setName(org.getOrgName());
			vo.setFullName(org.getOrgFullName());
			vo.setFullPath(org.getOrgPath());
			vo.setCompanyId(org.getCompanyId());
			vo.setOrgCode(org.getOrgCode());
			vo.setAddTime(org.getAddTime());
			vo.setAllowEdit(org.getAllowEdit());
			vo.setAllowDelete(org.getAllowDelete());
			vo.setEnable(org.getEnable());
			vo.setShowOrder(org.getShowOrder());
			vo.setIsOrg(2);
			vo.setChildren(recursiveOrgTree(orgs,orgs.stream().filter(v->v.getPOrgId().equals(org.getOrgId())).collect(Collectors.toList()),org.getOrgId()));
			orgTreeVos.add(vo);
		});
		return orgTreeVos;
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public OrgVo queryOrgByCondition(OrgDTO param) {
		return baseMapper.queryOrgByCondition(param);
	}


}
