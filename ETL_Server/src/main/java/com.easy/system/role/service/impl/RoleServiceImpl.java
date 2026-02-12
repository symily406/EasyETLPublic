package com.easy.system.role.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.sql.SqlKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.run.cache.CacheManagement;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.company.mapper.CompanyMapper;
import com.easy.system.company.vo.CompanyVo;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.org.mapper.OrgMapper;
import com.easy.system.org.vo.OrgVo;
import com.easy.system.role.dto.RoleDTO;
import com.easy.system.role.entity.Role;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.mapper.RoleFunctionMapper;
import com.easy.system.role.mapper.RoleMapper;
import com.easy.system.role.service.IRoleService;
import com.easy.system.role.vo.RoleTreeVo;
import com.easy.system.role.vo.RoleVo;

/**
 * 角色-服务类
 * @author
 * @time
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	@Autowired
	private RoleMapper roleMapper;


	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Transactional
	@Override
	public boolean saveOrUpdateRole(Role role,List<RoleFunction> roleFunctions) {
		boolean succed=true;
		try {
			String roleId=role.getrId();
			if (StringUtils.isBlank(roleId)) {
				roleId=idBulider();
				role.setrId(roleId);
				roleMapper.insert( role);
			}else {
				roleMapper.updateById(role);
			}


			roleFunctionMapper.deleteByCondition("DELETE FROM TR_ROLE_FUNCTION WHERE R_ID='"+SqlKit.filter(roleId)+"'", null);
			for (RoleFunction fun : roleFunctions) {
				fun.setrId(roleId);
				roleFunctionMapper.insert(fun);
			}
		} catch (Exception e) {
			succed=false;
			e.printStackTrace();
		}
		return succed;
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<RoleVo> queryRolePage(IPage<RoleVo> page, RoleDTO param) {
		return page.setRecords(baseMapper.queryRolePage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<RoleVo> queryOrgRoles(RoleDTO param) {
		return baseMapper.queryRoleAll(param);
	}

	/**
	 * 权限树
	 * @return
	 */
	@Override
	public List<RoleTreeVo> queryRoleTree(CompanyDTO companyDTO,OrgDTO orgDTO,RoleDTO roleDTO) {
		List<CompanyVo> companyVos=companyMapper.queryCompanyAll(companyDTO);
		orgDTO.setOrgId(null);
		List<OrgVo> orgVos=orgMapper.queryOrgAll(orgDTO);

		List<RoleVo> roleVos=baseMapper.queryRoleAll(roleDTO);


		List<RoleTreeVo> re=new ArrayList<>();
		for (CompanyVo companyVo : companyVos) {
			RoleTreeVo vo=new RoleTreeVo();
			vo.setId(companyVo.getCompanyId());
			vo.setCompanyId(companyVo.getCompanyId());
			vo.setName(companyVo.getCompanyName());
			vo.setAddTime(companyVo.getAddTime());
			vo.setEnable(companyVo.getEnable());
			vo.setAllowEdit(companyVo.getAllowEdit());
			vo.setAllowDelete(companyVo.getAllowDelete());
			vo.setLeval(1);
			vo.setChildren(recursiveRoleTree(orgVos,orgVos.stream().filter(v->StringUtils.equals(v.getCompanyId(), companyVo.getCompanyId())).collect(Collectors.toList()),roleVos));
			re.add(vo);
		}
		return re;
	}

	/**
	 * 递归生成菜单树
	 * @return
	 */
	private List<RoleTreeVo> recursiveRoleTree(List<OrgVo> orgs,List<OrgVo> childerOrgs,List<RoleVo> roleVos) {
		if (childerOrgs==null||childerOrgs.isEmpty()) {
			return null;
		}
		List<RoleTreeVo> orgTreeVos=new ArrayList<>();
		childerOrgs.stream().forEach(org->{
			RoleTreeVo vo=new RoleTreeVo();
			vo.setId(org.getOrgId());
			vo.setName(org.getOrgName());
			vo.setCompanyId(org.getCompanyId());
			vo.setOrgId(org.getOrgId());
			vo.setAddTime(org.getAddTime());
			vo.setAllowEdit(org.getAllowEdit());
			vo.setAllowDelete(org.getAllowDelete());
			vo.setEnable(org.getEnable());
			vo.setLeval(2);
			List<OrgVo> children=orgs.stream().filter(v->v.getPOrgId().equals(org.getOrgId())).collect(Collectors.toList());
			if (children==null||children.isEmpty()) {
				List<RoleTreeVo> childrenRoles=new ArrayList<>();
				List<RoleVo> roles=roleVos.stream().filter(r->r.getOrgId().equals(org.getOrgId())).collect(Collectors.toList());
				for (RoleVo role : roles) {
					RoleTreeVo r=new RoleTreeVo();
					r.setId(role.getRId());
					r.setName(role.getName());
					r.setCompanyId(role.getCompanyId());
					r.setOrgId(role.getOrgId());
					r.setAddTime(role.getAddTime());
					r.setAllowEdit(role.getAllowEdit());
					r.setAllowDelete(role.getAllowDelete());
					r.setEnable(role.getEnable());
					r.setCode(role.getCode());
					r.setLeval(3);
					r.setIndexPath(role.getIndexPath());
					childrenRoles.add(r);
				}
				vo.setChildren(childrenRoles);
			}else {
				vo.setChildren(recursiveRoleTree(orgs,children,roleVos));
			}
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
	public RoleVo queryRoleByCondition(RoleDTO param) {
		return baseMapper.queryRoleByCondition(param);
	}

	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@Transactional
	@Override
	public boolean deleteRoleById(String roleId) {
		boolean b1=baseMapper.deleteByCondition("delete from TD_ROLE where R_ID='"+SqlKit.filter(roleId)+"'",null);
		boolean b2=roleFunctionMapper.deleteByCondition("delete from TR_ROLE_FUNCTION where R_ID='"+SqlKit.filter(roleId)+"'", null);
		return b1&&b2;
	}



}
