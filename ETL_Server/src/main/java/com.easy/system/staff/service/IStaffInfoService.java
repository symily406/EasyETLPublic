package com.easy.system.staff.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.staff.dto.StaffInfoDTO;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.vo.StaffInfoVo;

/**
 * 用户信息-服务类
 * @author
 * @time
 */
public interface  IStaffInfoService extends IBaseService<StaffInfo> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param staffInfo
	 */
	public boolean saveOrUpdateStaffInfo(StaffInfo staffInfo);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<StaffInfoVo> queryStaffInfoPage(IPage<StaffInfoVo> page,StaffInfoDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<StaffInfoVo> queryStaffInfoAll(StaffInfoDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public StaffInfoVo queryStaffInfoByCondition(StaffInfoDTO param);
	//================代码生成器生成代码结束=================
}
