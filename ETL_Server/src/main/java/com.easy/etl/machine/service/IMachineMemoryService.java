package com.easy.etl.machine.service;


import java.util.List;

import com.easy.etl.machine.entity.MachineMemory;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 服务器内存-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IMachineMemoryService extends IBaseService<MachineMemory> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param machineMemory
	 */
	public boolean saveOrUpdateMachineMemory(MachineMemory machineMemory);


}
