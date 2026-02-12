package com.easy.etl.dbSource.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

import java.util.List;

/**
 * 数据源-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IDbSourceService extends IBaseService<DbSource> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param dbSource
	 */
	public boolean saveOrUpdateDbSource(DbSource dbSource);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<DbSourceVo> queryDbSourcePage(IPage<DbSourceVo> page, DbSourceDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<DbSourceVo> queryDbSourceAll(DbSourceDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public DbSourceVo queryDbSourceByCondition(DbSourceDTO param);
	//================代码生成器生成代码结束=================
}
