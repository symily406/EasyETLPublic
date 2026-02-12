package com.easy.etl.project.mapper;


import java.util.List;

import com.easy.etl.project.entity.ProjectWorkSpaceLayerRel;
import com.easy.etl.project.vo.ProjectWorkSpaceLayerRelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目工作空间分层关联-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectWorkSpaceLayerRelMapper extends MapperDao<ProjectWorkSpaceLayerRel> {
	public List<ProjectWorkSpaceLayerRelVo> queryProjectWorkSpaceLayer(String projectId);
}
