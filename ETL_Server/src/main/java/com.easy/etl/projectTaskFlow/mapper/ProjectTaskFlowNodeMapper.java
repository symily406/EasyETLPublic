package com.easy.etl.projectTaskFlow.mapper;


import java.util.List;

import com.easy.etl.projectTaskFlow.entity.ProjectTaskFlowNode;
import com.easy.etl.projectTaskFlow.vo.ProjectTaskFlowNodeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目工作流节点-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskFlowNodeMapper extends MapperDao<ProjectTaskFlowNode> {
    public List<ProjectTaskFlowNodeVo> queryProjectTaskFlowNode(String taskId);
}
