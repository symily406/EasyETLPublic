package com.easy.etl.projectTask.mapper;


import com.easy.etl.projectTask.dto.ProjectTaskFolderDTO;
import com.easy.etl.projectTask.entity.ProjectTaskFolder;
import com.easy.etl.projectTask.vo.ProjectTaskFolderVo;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目任务目录-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskFolderMapper extends MapperDao<ProjectTaskFolder> {

   //=============代码生成器生成代码开始==============

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskFolderVo> queryProjectTaskFolderAll(@Param("param") ProjectTaskFolderDTO param);


	//=============代码生成器生成代码结束==============
}
