package com.easy.system.role.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.company.dto.CompanyDTO;
import com.easy.system.org.dto.OrgDTO;
import com.easy.system.role.dto.RoleDTO;
import com.easy.system.role.entity.Role;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.vo.RoleTreeVo;
import com.easy.system.role.vo.RoleVo;

/**
 * 角色-服务类
 *
 * @author
 * @time
 */
public interface IRoleService extends IBaseService<Role> {


	//================代码生成器生成代码开始=================
	/**
	 * 保存或修改Entity
	 *
	 * @param role
	 */
	public boolean saveOrUpdateRole(Role role, List<RoleFunction> roleFunctions);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<RoleVo> queryRolePage(IPage<RoleVo> page, RoleDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<RoleVo> queryOrgRoles(RoleDTO param);

	/**
	 * 权限树
	 *
	 * @param param
	 * @return
	 */
	List<RoleTreeVo> queryRoleTree(CompanyDTO companyDTO, OrgDTO orgDTO, RoleDTO roleDTO);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	public RoleVo queryRoleByCondition(RoleDTO param);
	// ================代码生成器生成代码结束=================

	/**
	 * 删除角色
	 *
	 * @param roleId
	 * @return
	 */
	public boolean deleteRoleById(String roleId);
}
