package com.easy.etl.projectMember.mapper;


import java.util.List;

import com.easy.etl.projectMember.entity.ProjectMemberSpaceLayerRel;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目成员工作空间层级关联表-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectMemberSpaceLayerRelMapper extends MapperDao<ProjectMemberSpaceLayerRel> {

}
