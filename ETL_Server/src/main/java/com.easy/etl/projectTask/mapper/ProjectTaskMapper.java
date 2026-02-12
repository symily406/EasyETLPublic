package com.easy.etl.projectTask.mapper;


import java.util.List;

import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目任务-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskMapper extends MapperDao<ProjectTask> {

    //=============代码生成器生成代码开始==============

    /**
     * 分页
     *
     * @param param
     * @return
     */
    List<ProjectTaskVo> queryProjectTaskPage(IPage<ProjectTaskVo> page, @Param("param") ProjectTaskDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<ProjectTaskVo> queryProjectTaskAll(@Param("param") ProjectTaskDTO param);

    //=============代码生成器生成代码结束==============

    ProjectTaskVo queryProjectTaskById(String taskId);
}
