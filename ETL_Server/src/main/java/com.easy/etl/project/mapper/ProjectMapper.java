package com.easy.etl.project.mapper;


import java.util.List;

import com.easy.etl.project.dto.ProjectDTO;
import com.easy.etl.project.entity.Project;
import com.easy.etl.project.vo.ProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.session.ResultHandler;

/**
 * 项目管理-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectMapper extends MapperDao<Project> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<ProjectVo> queryProjectPage(IPage<ProjectVo> page, @Param("param") ProjectDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectVo> queryProjectAll(@Param("param")ProjectDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	ProjectVo queryProjectByCondition(@Param("param")ProjectDTO  param);
	//=============代码生成器生成代码结束==============
}
