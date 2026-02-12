package com.easy.system.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.file.dto.UserResourcesDTO;
import com.easy.system.file.entity.UserResources;
import com.easy.system.file.vo.UserResourcesVo;

/**
 * 用户资源-接口
 *
 * @author
 * @time
 */
@Mapper
public interface UserResourcesMapper extends MapperDao<UserResources> {

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<UserResourcesVo> queryUserResourcesPage(IPage<UserResourcesVo> page, @Param("param") UserResourcesDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<UserResourcesVo> queryUserResourcesAll(@Param("param") UserResourcesDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	UserResourcesVo queryUserResourcesByCondition(@Param("param") UserResourcesDTO param);
	// =============代码生成器生成代码结束==============
}
