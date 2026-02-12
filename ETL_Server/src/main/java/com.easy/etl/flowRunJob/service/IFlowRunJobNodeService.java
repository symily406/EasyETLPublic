package com.easy.etl.flowRunJob.service;


import com.easy.etl.flowRunJob.dto.FlowRunJobNodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

import java.util.List;

/**
 * 工作流运行Node-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IFlowRunJobNodeService extends IBaseService<FlowRunJobNode> {

	/**
	   * 保存或修改Entity
	 * @param flowrunJobNode
	 */
	public boolean saveOrUpdateFlowRunJobNode(FlowRunJobNode flowrunJobNode);
	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<FlowRunJobNodeVo> queryFlowRunJobNodeAll(FlowRunJobNodeDTO param);

}
