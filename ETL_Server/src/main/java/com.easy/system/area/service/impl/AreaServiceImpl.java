package com.easy.system.area.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.area.dto.AreaDTO;
import com.easy.system.area.entity.Area;
import com.easy.system.area.mapper.AreaMapper;
import com.easy.system.area.service.IAreaService;
import com.easy.system.area.vo.AreaVo;

/**
 * 地区表-服务类
 * @author
 * @time
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl<AreaMapper, Area> implements IAreaService {



	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<AreaVo> queryAreaAll(AreaDTO param) {
		return baseMapper.queryAreaAll(param);
	}

}
