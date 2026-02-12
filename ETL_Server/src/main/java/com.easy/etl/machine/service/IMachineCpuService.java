package com.easy.etl.machine.service;


import java.util.List;

import com.easy.etl.machine.entity.MachineCpu;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * CPU信息-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IMachineCpuService extends IBaseService<MachineCpu> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param machineCpu
	 */
	public boolean saveOrUpdateMachineCpu(MachineCpu machineCpu);
}
