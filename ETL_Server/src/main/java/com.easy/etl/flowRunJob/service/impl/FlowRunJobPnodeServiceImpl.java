package com.easy.etl.flowRunJob.service.impl;
import java.util.List;

import com.easy.etl.flowRunJob.dto.FlowRunJobPnodeDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.mapper.FlowRunJobPnodeMapper;
import com.easy.etl.flowRunJob.service.IFlowRunJobPnodeService;
import com.easy.etl.flowRunJob.vo.FlowRunJobPnodeVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 工作流运行依懒node-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class FlowRunJobPnodeServiceImpl extends BaseServiceImpl<FlowRunJobPnodeMapper, FlowRunJobPnode> implements IFlowRunJobPnodeService {

	/**
	     * 保存或修改Entity
	 * @param flowRunJobPnode
	 */
	@Override
	public boolean saveOrUpdateFlowRunJobPnode(FlowRunJobPnode flowRunJobPnode) {
		return saveOrUpdate(flowRunJobPnode);
	}



	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<FlowRunJobPnodeVo> queryFlowRunJobPnodeAll(FlowRunJobPnodeDTO param) {
		return baseMapper.queryFlowRunJobPnodeAll(param);
	}

}
