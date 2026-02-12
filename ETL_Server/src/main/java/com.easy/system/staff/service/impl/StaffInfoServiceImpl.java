package com.easy.system.staff.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.staff.dto.StaffInfoDTO;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.mapper.StaffInfoMapper;
import com.easy.system.staff.service.IStaffInfoService;
import com.easy.system.staff.vo.StaffInfoVo;

/**
 * 用户信息-服务类
 * @author
 * @time
 */
@Service
public class StaffInfoServiceImpl extends BaseServiceImpl<StaffInfoMapper, StaffInfo> implements IStaffInfoService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateStaffInfo(StaffInfo staffInfo) {
		return saveOrUpdate(staffInfo);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<StaffInfoVo> queryStaffInfoPage(IPage<StaffInfoVo> page, StaffInfoDTO param) {
		return page.setRecords(baseMapper.queryStaffInfoPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<StaffInfoVo> queryStaffInfoAll(StaffInfoDTO param) {
		return baseMapper.queryStaffInfoAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public StaffInfoVo queryStaffInfoByCondition(StaffInfoDTO param) {
		return baseMapper.queryStaffInfoByCondition(param);
	}
}
