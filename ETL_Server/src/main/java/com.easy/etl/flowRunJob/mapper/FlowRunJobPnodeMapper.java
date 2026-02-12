package com.easy.etl.flowRunJob.mapper;


import java.util.List;

import com.easy.etl.flowRunJob.dto.FlowRunJobPnodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.vo.FlowRunJobPnodeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 工作流运行依懒node-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface FlowRunJobPnodeMapper extends MapperDao<FlowRunJobPnode> {


	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<FlowRunJobPnodeVo> queryFlowRunJobPnodeAll(@Param("param")FlowRunJobPnodeDTO  param);

	List<FlowRunJobPnodeVo> queryEnableFlowRunJobNode(Integer toBeAllocated,Integer nextNodeStatus0,Integer succed);

	/**
	 * 查询失败或成功且未配置下级节点的任务
	 * @param status
	 * @param nextNodeStatus0
	 * @return
	 */
	List<FlowRunJobPnodeVo> queryFaileAndSuccedFlowRunJobNode(List<Integer> status,Integer nextNodeStatus0);
}
