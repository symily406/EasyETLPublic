package com.easy.etl.projectTaskFlow.mapper;


import java.util.List;

import com.easy.etl.projectTaskFlow.dto.ProjectTaskFlowDTO;
import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlow;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目工作流-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskFlowMapper extends MapperDao<ProjectTaskFlow> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<ProjectTaskFlowVo> queryProjectTaskFlowPage(IPage<ProjectTaskFlowVo> page, @Param("param") ProjectTaskFlowDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskFlowVo> queryProjectTaskFlowAll(@Param("param")ProjectTaskFlowDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	ProjectTaskFlowVo queryProjectTaskFlowByCondition(@Param("param")ProjectTaskFlowDTO  param);
	//=============代码生成器生成代码结束==============
}
