package com.easy.system.menu.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.menu.dto.MenuRoleDTO;
import com.easy.system.menu.entity.MenuRole;
import com.easy.system.menu.vo.MenuRoleVo;

/**
 * 菜单权限-接口
 * @author
 * @time
 */
@Mapper
public interface MenuRoleMapper extends MapperDao<MenuRole> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<MenuRoleVo> queryMenuRolePage(IPage<MenuRoleVo> page,@Param("param")MenuRoleDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<MenuRoleVo> queryMenuRoleAll(@Param("param")MenuRoleDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	MenuRoleVo queryMenuRoleByCondition(@Param("param")MenuRoleDTO  param);
	//=============代码生成器生成代码结束==============
}
