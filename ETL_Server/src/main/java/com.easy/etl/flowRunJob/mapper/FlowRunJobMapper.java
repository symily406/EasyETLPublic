package com.easy.etl.flowRunJob.mapper;


import java.util.List;

import com.easy.etl.flowRunJob.dto.FlowRunJobDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.vo.FlowRunJobVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 工作流运行JOB-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface FlowRunJobMapper extends MapperDao<FlowRunJob> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<FlowRunJobVo> queryFlowRunJobPage(IPage<FlowRunJobVo> page, @Param("param") FlowRunJobDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<FlowRunJobVo> queryFlowRunJobAll(@Param("param")FlowRunJobDTO  param);
}
