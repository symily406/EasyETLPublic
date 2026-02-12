package com.easy.system.staff.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.staff.dto.StaffDutyDTO;
import com.easy.system.staff.entity.StaffDuty;
import com.easy.system.staff.vo.StaffDutyVo;

/**
 * 用户岗位-服务类
 * @author
 * @time
 */
public interface  IStaffDutyService extends IBaseService<StaffDuty> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param staffDuty
	 */
	public boolean saveOrUpdateStaffDuty(StaffDuty staffDuty);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<StaffDutyVo> queryStaffDutyPage(IPage<StaffDutyVo> page,StaffDutyDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<StaffDutyVo> queryStaffDutyAll(StaffDutyDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public StaffDutyVo queryStaffDutyByCondition(StaffDutyDTO param);
	//================代码生成器生成代码结束=================
}
