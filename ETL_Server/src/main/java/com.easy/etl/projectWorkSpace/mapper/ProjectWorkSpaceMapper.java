package com.easy.etl.projectWorkSpace.mapper;


import java.util.List;

import com.easy.etl.projectWorkSpace.dto.ProjectWorkSpaceDTO;
import com.easy.etl.projectWorkSpace.entity.ProjectWorkSpace;
import com.easy.etl.projectWorkSpace.vo.ProjectWorkSpaceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目工作空间-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectWorkSpaceMapper extends MapperDao<ProjectWorkSpace> {


	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectWorkSpaceVo> queryProjectWorkSpaceAll(@Param("param")ProjectWorkSpaceDTO  param);

}
