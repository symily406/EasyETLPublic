package com.easy.etl.projectDbSource.mapper;


import java.util.List;

import com.easy.etl.project.vo.ProjectVo;
import com.easy.etl.projectDbSource.dto.ProjectDbSourceDTO;
import com.easy.etl.projectDbSource.entity.ProjectDbSource;
import com.easy.etl.projectDbSource.vo.ProjectDbSourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 引用的数据源-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectDbSourceMapper extends MapperDao<ProjectDbSource> {

	//=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<ProjectDbSourceVo> queryProjectDbSourcePage(IPage<ProjectDbSourceVo> page, @Param("param") ProjectDbSourceDTO param);

	List<ProjectDbSourceVo> queryProjectDbSourceAll( @Param("param") ProjectDbSourceDTO param);

	List<ProjectDbSourceVo> queryProjectDbSourceCount(List<ProjectVo> list);
	//=============代码生成器生成代码结束==============
}
