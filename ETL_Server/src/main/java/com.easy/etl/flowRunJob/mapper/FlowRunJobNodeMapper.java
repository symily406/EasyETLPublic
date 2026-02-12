package com.easy.etl.flowRunJob.mapper;


import com.easy.etl.flowRunJob.dto.FlowRunJobNodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeStatusVo;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeVo;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 工作流运行Node-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface FlowRunJobNodeMapper extends MapperDao<FlowRunJobNode> {

	List<FlowRunJobNodeVo> queryFlowRunTasks(String dateTime,Integer toBeAllocated,Integer size);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<FlowRunJobNodeVo> queryFlowRunJobNodeAll(@Param("param") FlowRunJobNodeDTO param);

	List<FlowRunJobNodeStatusVo> groupNodeStatusByIds(List<String> runJobNodeIds, Integer succedStatus,Integer failedStatus);
}
