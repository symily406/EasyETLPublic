package com.easy.etl.projectTaskFlow.mapper;


import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowSourceTargetNode;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowSourceTargetNodeVo;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目工作流节点关系-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskFlowSourceTargetNodeMapper extends MapperDao<ProjectTaskFlowSourceTargetNode> {

    List<ProjectTaskFlowSourceTargetNodeVo> queryProjectTaskFlowSourceTargetNodes(@Param("taskId") String taskId);
}
