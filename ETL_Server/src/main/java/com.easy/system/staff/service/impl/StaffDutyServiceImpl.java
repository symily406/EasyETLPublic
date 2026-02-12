package com.easy.system.staff.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.staff.dto.StaffDutyDTO;
import com.easy.system.staff.entity.StaffDuty;
import com.easy.system.staff.mapper.StaffDutyMapper;
import com.easy.system.staff.service.IStaffDutyService;
import com.easy.system.staff.vo.StaffDutyVo;

/**
 * 用户岗位-服务类
 * @author
 * @time
 */
@Service
public class StaffDutyServiceImpl extends BaseServiceImpl<StaffDutyMapper, StaffDuty> implements IStaffDutyService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateStaffDuty(StaffDuty staffDuty) {
		return saveOrUpdate(staffDuty);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<StaffDutyVo> queryStaffDutyPage(IPage<StaffDutyVo> page, StaffDutyDTO param) {
		return page.setRecords(baseMapper.queryStaffDutyPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<StaffDutyVo> queryStaffDutyAll(StaffDutyDTO param) {
		return baseMapper.queryStaffDutyAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public StaffDutyVo queryStaffDutyByCondition(StaffDutyDTO param) {
		return baseMapper.queryStaffDutyByCondition(param);
	}
}
