package com.easy.system.staff.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.staff.dto.StaffRoleDTO;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.mapper.StaffRoleMapper;
import com.easy.system.staff.service.IStaffRoleService;
import com.easy.system.staff.vo.StaffRoleVo;

/**
 * 角色用户关联-服务类
 * @author
 * @time
 */
@Service
public class StaffRoleServiceImpl extends BaseServiceImpl<StaffRoleMapper, StaffRole> implements IStaffRoleService {


	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateStaffRole(StaffRole staffRole) {
		return saveOrUpdate(staffRole);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<StaffRoleVo> queryStaffRolePage(IPage<StaffRoleVo> page, StaffRoleDTO param) {
		return page.setRecords(baseMapper.queryStaffRolePage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<StaffRoleVo> queryStaffRoleAll(StaffRoleDTO param) {
		return baseMapper.queryStaffRoleAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public StaffRoleVo queryStaffRoleByCondition(StaffRoleDTO param) {
		return baseMapper.queryStaffRoleByCondition(param);
	}
}
