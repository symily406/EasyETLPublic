package com.easy.system.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.role.dto.RoleFunctionDTO;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.vo.RoleFunctionVo;

/**
 * 角色功能关联表-接口
 *
 * @author
 * @time
 */
@Mapper
public interface RoleFunctionMapper extends MapperDao<RoleFunction> {

	// =============代码生成器生成代码开始==============

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<RoleFunctionVo> queryRoleFunctionAll(@Param("param") RoleFunctionDTO param);

	// =============代码生成器生成代码结束==============
}
