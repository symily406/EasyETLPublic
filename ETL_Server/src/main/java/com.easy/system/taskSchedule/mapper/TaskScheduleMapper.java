package com.easy.system.taskSchedule.mapper;


import java.util.List;

import com.easy.system.taskSchedule.dto.TaskScheduleDTO;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.vo.TaskScheduleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务调度-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface TaskScheduleMapper extends MapperDao<TaskSchedule> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<TaskScheduleVo> queryTaskSchedulePage(IPage<TaskScheduleVo> page, @Param("param") TaskScheduleDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<TaskScheduleVo> queryTaskScheduleAll(@Param("param")TaskScheduleDTO  param);

}
