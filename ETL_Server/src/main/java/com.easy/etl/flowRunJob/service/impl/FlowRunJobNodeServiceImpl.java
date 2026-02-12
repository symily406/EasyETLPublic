package com.easy.etl.flowRunJob.service.impl;

import com.easy.etl.flowRunJob.dto.FlowRunJobNodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.mapper.FlowRunJobNodeMapper;
import com.easy.etl.flowRunJob.service.IFlowRunJobNodeService;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeVo;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工作流运行Node-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class FlowRunJobNodeServiceImpl extends BaseServiceImpl<FlowRunJobNodeMapper, FlowRunJobNode> implements IFlowRunJobNodeService {

	/**
	     * 保存或修改Entity
	 * @param flowrunJobNode
	 */
	@Override
	public boolean saveOrUpdateFlowRunJobNode(FlowRunJobNode flowrunJobNode) {
		return saveOrUpdate(flowrunJobNode);
	}



	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<FlowRunJobNodeVo> queryFlowRunJobNodeAll(FlowRunJobNodeDTO param) {
		return baseMapper.queryFlowRunJobNodeAll(param);
	}


}
