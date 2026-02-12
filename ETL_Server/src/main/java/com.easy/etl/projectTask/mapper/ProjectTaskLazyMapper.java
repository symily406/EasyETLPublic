package com.easy.etl.projectTask.mapper;


import java.util.List;

import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目任务依懒-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskLazyMapper extends MapperDao<ProjectTaskLazy> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<ProjectTaskLazyVo> queryProjectTaskLazyPage(IPage<ProjectTaskLazyVo> page, @Param("param") ProjectTaskLazyDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskLazyVo> queryProjectTaskLazyAll(@Param("param")ProjectTaskLazyDTO  param);


	//=============代码生成器生成代码结束==============
}
