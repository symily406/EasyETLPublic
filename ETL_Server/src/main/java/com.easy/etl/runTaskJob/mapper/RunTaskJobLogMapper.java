package com.easy.etl.runTaskJob.mapper;


import java.util.List;

import com.easy.etl.runTaskJob.dto.RunTaskJobLogDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.vo.RunTaskJobLogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务运行日志-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface RunTaskJobLogMapper extends MapperDao<RunTaskJobLog> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<RunTaskJobLogVo> queryRunTaskJobLogPage(IPage<RunTaskJobLogVo> page, @Param("param") RunTaskJobLogDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<RunTaskJobLogVo> queryRunTaskJobLogAll(@Param("param")RunTaskJobLogDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	RunTaskJobLogVo queryRunTaskJobLogByCondition(@Param("param")RunTaskJobLogDTO  param);
	//=============代码生成器生成代码结束==============
}
