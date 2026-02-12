package com.easy.etl.flowRunJob.service;


import java.util.List;

import com.easy.etl.flowRunJob.dto.FlowRunJobPnodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.vo.FlowRunJobPnodeVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 工作流运行依懒node-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IFlowRunJobPnodeService extends IBaseService<FlowRunJobPnode> {

	/**
	   * 保存或修改Entity
	 * @param flowRunJobPnode
	 */
	public boolean saveOrUpdateFlowRunJobPnode(FlowRunJobPnode flowRunJobPnode);


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<FlowRunJobPnodeVo> queryFlowRunJobPnodeAll(FlowRunJobPnodeDTO param);

}
