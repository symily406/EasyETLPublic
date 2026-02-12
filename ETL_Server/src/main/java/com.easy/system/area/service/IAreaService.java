package com.easy.system.area.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.area.dto.AreaDTO;
import com.easy.system.area.entity.Area;
import com.easy.system.area.vo.AreaVo;

/**
 * 地区表-服务类
 * @author
 * @time
 */
public interface  IAreaService extends IBaseService<Area> {

	//================代码生成器生成代码开始=================


	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<AreaVo> queryAreaAll(AreaDTO param);

	//================代码生成器生成代码结束=================
}
