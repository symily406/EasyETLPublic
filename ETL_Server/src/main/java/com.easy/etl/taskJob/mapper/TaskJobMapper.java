package com.easy.etl.taskJob.mapper;


import java.util.List;

import com.easy.etl.taskJob.dto.TaskJobDTO;
import com.easy.etl.taskJob.entity.TaskJob;
import com.easy.etl.taskJob.vo.TaskJobVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 待运行任务-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface TaskJobMapper extends MapperDao<TaskJob> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<TaskJobVo> queryTaskJobPage(IPage<TaskJobVo> page, @Param("param") TaskJobDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<TaskJobVo> queryTaskJobAll(@Param("param")TaskJobDTO  param);

	//=============代码生成器生成代码结束==============

	List<TaskJobVo> queryJobDisableNum(@Param("param")TaskJobDTO  param);
}
