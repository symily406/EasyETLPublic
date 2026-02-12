package com.easy.etl.machine.service.impl;
import java.util.List;

import com.easy.etl.machine.entity.MachineCpu;
import com.easy.etl.machine.mapper.MachineCpuMapper;
import com.easy.etl.machine.service.IMachineCpuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * CPU信息-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class MachineCpuServiceImpl extends BaseServiceImpl<MachineCpuMapper, MachineCpu> implements IMachineCpuService {

	/**
	     * 保存或修改Entity
	 * @param machineCpu
	 */
	@Override
	public boolean saveOrUpdateMachineCpu(MachineCpu machineCpu) {
		return saveOrUpdate(machineCpu);
	}

}
