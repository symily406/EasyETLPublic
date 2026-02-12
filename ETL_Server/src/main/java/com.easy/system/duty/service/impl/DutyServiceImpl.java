package com.easy.system.duty.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.duty.dto.DutyDTO;
import com.easy.system.duty.entity.Duty;
import com.easy.system.duty.mapper.DutyMapper;
import com.easy.system.duty.service.IDutyService;
import com.easy.system.duty.vo.DutyVo;

/**
 * 岗位管理-服务类
 * @author
 * @time
 */
@Service
public class DutyServiceImpl extends BaseServiceImpl<DutyMapper, Duty> implements IDutyService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateDuty(Duty duty) {
		return saveOrUpdate(duty);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<DutyVo> queryDutyPage(IPage<DutyVo> page, DutyDTO param) {
		return page.setRecords(baseMapper.queryDutyPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<DutyVo> queryDutyAll(DutyDTO param) {
		return baseMapper.queryDutyAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public DutyVo queryDutyByCondition(DutyDTO param) {
		return baseMapper.queryDutyByCondition(param);
	}

	/**
	 * 查询用户岗位，只返回一个最高级的岗位
	 * @param staffId
	 * @return
	 */
	@Override
	public Duty queryDutyByStaffId(String staffId) {
		List<DutyVo> list=baseMapper.queryDutyByStaffId(staffId);
		if(CollectionUtils.isEmpty(list)) {
			return new DutyVo();
		}
		return list.get(0);
	}
}
