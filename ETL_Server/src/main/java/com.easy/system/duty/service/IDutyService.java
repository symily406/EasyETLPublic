package com.easy.system.duty.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.duty.dto.DutyDTO;
import com.easy.system.duty.entity.Duty;
import com.easy.system.duty.vo.DutyVo;

/**
 * 岗位管理-服务类
 * @author
 * @time
 */
public interface  IDutyService extends IBaseService<Duty> {

	/**
	 * 查询用户岗位，只返回一个最高级的岗位
	 * @param staffId
	 * @return
	 */
	public Duty queryDutyByStaffId(String staffId);


	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param duty
	 */
	public boolean saveOrUpdateDuty(Duty duty);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<DutyVo> queryDutyPage(IPage<DutyVo> page,DutyDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<DutyVo> queryDutyAll(DutyDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public DutyVo queryDutyByCondition(DutyDTO param);
	//================代码生成器生成代码结束=================
}
