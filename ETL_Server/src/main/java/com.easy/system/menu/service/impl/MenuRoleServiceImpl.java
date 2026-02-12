package com.easy.system.menu.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.sql.SqlKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.etl.core.init.Config;
import com.easy.system.company.entity.CompanyFunction;
import com.easy.system.company.mapper.CompanyFunctionMapper;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.entity.MenuRole;
import com.easy.system.menu.mapper.MenuRoleMapper;
import com.easy.system.menu.service.IMenuRoleService;
import com.easy.system.menu.vo.MenuRoleVo;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.mapper.RoleFunctionMapper;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.mapper.StaffRoleMapper;
import com.easy.etl.core.dto.UserInfoDTO;

/**
 * 菜单权限-服务类
 * @author
 * @time
 */
@Service
public class MenuRoleServiceImpl extends BaseServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

	@Autowired
	private CompanyFunctionMapper companyFunctionMapper;
	@Autowired
	private StaffRoleMapper staffRoleMapper;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;


	private boolean initCompanyAndStaffRole(MenuRole menuRole,UserInfoDTO userInfoDTO) {
		//增加企业菜单
		CompanyFunction companyFunction=companyFunctionMapper.selectOne(new QueryWrapper<CompanyFunction>()
				.lambda()
				.eq(CompanyFunction::getCompanyId, userInfoDTO.getCompanyId())
				.eq(CompanyFunction::getfId, menuRole.getRoleId())
				);
		if (companyFunction==null) {
			int leval=2;
			//企业菜单
			companyFunction=new CompanyFunction();
			companyFunction.setCompanyId(userInfoDTO.getCompanyId());
			companyFunction.setfId(menuRole.getRoleId());
			companyFunction.setLeval(leval);
			companyFunctionMapper.insert(companyFunction);

			List<StaffRole> staffRoles=staffRoleMapper.selectList(new QueryWrapper<StaffRole>()
					.lambda()
					.eq(StaffRole::getStaffId, userInfoDTO.getUserId())
					);
			for (StaffRole staffRole : staffRoles) {
				RoleFunction roleFunction=roleFunctionMapper.selectOne(new QueryWrapper<RoleFunction>()
						.lambda()
						.eq(RoleFunction::getrId, staffRole.getRoleId())
						.eq(RoleFunction::getfId, menuRole.getRoleId())
						);
				if (roleFunction==null) {
					roleFunction=new RoleFunction();
					roleFunction.setrId(staffRole.getRoleId());
					roleFunction.setfId(menuRole.getRoleId());
					roleFunction.setLeval(leval);
					roleFunctionMapper.insert(roleFunction);
				}
			}

		}
		return true;
	}

	@Override
	public boolean saveOrUpdateMenuRoleBatch(List<MenuRole> roles, UserInfoDTO userInfoDTO) {
		for (MenuRole menuRole : roles) {
			saveOrUpdateMenuRole(menuRole,userInfoDTO);
		}
		return true;
	}

	/**
	 * 保存或修改Entity
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveOrUpdateMenuRole(MenuRole menuRole,UserInfoDTO userInfoDTO) {
		 return saveOrUpdate(menuRole)&&initCompanyAndStaffRole(menuRole,userInfoDTO);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<MenuRoleVo> queryMenuRolePage(IPage<MenuRoleVo> page, MenuRoleDTO param) {
		return page.setRecords(baseMapper.queryMenuRolePage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<MenuRoleVo> queryMenuRoleAll(MenuRoleDTO param) {
		return baseMapper.queryMenuRoleAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public MenuRoleVo queryMenuRoleByCondition(MenuRoleDTO param) {
		return baseMapper.queryMenuRoleByCondition(param);
	}

	/**
	 * 批量删除权限
	 * @param roleIds
	 * @return
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean beatchRemoveMenuRole(String roleIds) {
		String roleIdIn=SqlKit.sqlIn(roleIds, ",");
		roleFunctionMapper.deleteByCondition("delete from TR_ROLE_FUNCTION where F_ID in("+roleIdIn+")", null);
		companyFunctionMapper.deleteByCondition("delete from TD_COMPANY_FUNCTION where F_ID in("+roleIdIn+")", null);
		return baseMapper.deleteByCondition("delete from TD_MENU_ROLE where ROLE_ID in("+roleIdIn+")", null);
	}
}
