package com.easy.system.staff.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.staff.dto.StaffRoleDTO;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.vo.StaffRoleVo;

/**
 * 角色用户关联-服务类
 * @author
 * @time
 */
public interface  IStaffRoleService extends IBaseService<StaffRole> {



	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param staffRole
	 */
	public boolean saveOrUpdateStaffRole(StaffRole staffRole);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<StaffRoleVo> queryStaffRolePage(IPage<StaffRoleVo> page,StaffRoleDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<StaffRoleVo> queryStaffRoleAll(StaffRoleDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public StaffRoleVo queryStaffRoleByCondition(StaffRoleDTO param);
	//================代码生成器生成代码结束=================
}
