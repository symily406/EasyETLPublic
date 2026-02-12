package com.easy.etl.projectWorkSpaceLayer.mapper;


import java.util.List;

import com.easy.etl.projectWorkSpaceLayer.dto.ProjectWorkSpaceLayerDTO;
import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目工作空间分层-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectWorkSpaceLayerMapper extends MapperDao<ProjectWorkSpaceLayer> {


	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectWorkSpaceLayerVo> queryProjectWorkSpaceLayerAll(@Param("param") ProjectWorkSpaceLayerDTO param);

}
