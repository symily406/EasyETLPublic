package com.easy.system.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.staff.dto.StaffRoleDTO;
import com.easy.system.staff.entity.StaffRole;
import com.easy.system.staff.vo.StaffRoleVo;

/**
 * 角色用户关联-接口
 *
 * @author
 * @time
 */
@Mapper
public interface StaffRoleMapper extends MapperDao<StaffRole> {


	/**
	 * 用户权限
	 * @param staffId
	 * @return
	 */
	List<String> queryStaffRole(String staffId);

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<StaffRoleVo> queryStaffRolePage(IPage<StaffRoleVo> page, @Param("param") StaffRoleDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<StaffRoleVo> queryStaffRoleAll(@Param("param") StaffRoleDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	StaffRoleVo queryStaffRoleByCondition(@Param("param") StaffRoleDTO param);
	// =============代码生成器生成代码结束==============
}
