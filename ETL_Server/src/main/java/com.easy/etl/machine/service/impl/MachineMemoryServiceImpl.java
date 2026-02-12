package com.easy.etl.machine.service.impl;
import java.util.List;

import com.easy.etl.machine.entity.MachineMemory;
import com.easy.etl.machine.mapper.MachineMemoryMapper;
import com.easy.etl.machine.service.IMachineMemoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 服务器内存-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class MachineMemoryServiceImpl extends BaseServiceImpl<MachineMemoryMapper, MachineMemory> implements IMachineMemoryService {

	/**
	 * 保存或修改Entity
	 * @param machineMemory
	 */
	@Override
	public boolean saveOrUpdateMachineMemory(MachineMemory machineMemory) {
		return saveOrUpdate(machineMemory);
	}
}
