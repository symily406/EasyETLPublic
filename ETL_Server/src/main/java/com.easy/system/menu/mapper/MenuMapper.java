package com.easy.system.menu.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.menu.dto.MenuDTO;
import com.easy.system.menu.entity.Menu;
import com.easy.system.menu.vo.StaffMenuVo;
import com.easy.system.menu.vo.MenuVo;

/**
 * 系统菜单-接口
 * @author
 * @time
 */
@Mapper
public interface MenuMapper extends MapperDao<Menu> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<StaffMenuVo> queryMenuPage(IPage<StaffMenuVo> page,@Param("param")MenuDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<MenuVo> queryMenuAll(@Param("param")MenuDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	StaffMenuVo queryMenuByCondition(@Param("param")MenuDTO  param);
	//=============代码生成器生成代码结束==============
}
