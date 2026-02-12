package com.easy.system.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.spring.boot.auth.IAuth;
import com.easy.run.cache.CacheManagement;
import com.easy.system.role.mapper.RoleMapper;
import com.easy.system.role.vo.RoleVo;
import com.easy.system.staff.mapper.StaffRoleMapper;

@Service("auth")
public class AuthImpl implements IAuth {

	@Autowired
	private StaffRoleMapper staffRoleMapper;
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<String> queryStaffPermissions(String staffId,String loginType) {
		List<String> roles=CacheManagement.getStaffPermissions(staffId);
		if (!ObjectUtils.isEmpty(roles)) {
			return roles;
		}
		roles=staffRoleMapper.queryStaffRole(staffId);
		CacheManagement.setStaffPermissions(staffId, roles);
		return roles;
	}

	@Override
	public List<String> queryStaffRoleCode(String staffId,String loginType) {
		List<String> roleCodes=CacheManagement.getStaffRoleCode(staffId);
		if (CollectionUtils.isEmpty(roleCodes)) {
			List<RoleVo> list=roleMapper.queryStaffRole(staffId);
			roleCodes=new ArrayList<String>();
			for (RoleVo roleVo : list) {
				roleCodes.add(roleVo.getCode());
			}
			CacheManagement.setStaffRoleCode(staffId, roleCodes);
		}
		return roleCodes;
	}

}
