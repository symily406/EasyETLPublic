package com.easy.etl.conf.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.conf.dto.ConfDTO;
import com.easy.etl.conf.entity.*;
import com.easy.etl.conf.vo.ConfVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 系统配置主表-服务类
 * @author 邵勇
 * @time 2025-10-08
 */
public interface  IConfService extends IBaseService<Conf> {

	public boolean setDefault(String confId);
	public ConfVo getConf();

	public ConfVo queryConfById(String confId);

	/**
	   * 保存或修改Entity
	 * @param conf
	 */
	public boolean saveOrUpdateConf(Conf conf, ConfBase confBase, ConfYarn confYarn, ConfDatax confDatax, ConfSql confSql);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ConfVo> queryConfPage(IPage<ConfVo> page, ConfDTO param);

}
