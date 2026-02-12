package com.easy.system.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.role.dto.RoleDTO;
import com.easy.system.role.entity.Role;
import com.easy.system.role.vo.RoleVo;

/**
 * 角色-接口
 *
 * @author
 * @time
 */
@Mapper
public interface RoleMapper extends MapperDao<Role> {


	/**
	 * 用户权限编码
	 *
	 * @param staffId
	 * @return
	 */
	List<RoleVo> queryStaffRole(String staffId);

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<RoleVo> queryRolePage(IPage<RoleVo> page, @Param("param") RoleDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<RoleVo> queryRoleAll(@Param("param") RoleDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	RoleVo queryRoleByCondition(@Param("param") RoleDTO param);
	// =============代码生成器生成代码结束==============
}
