package com.easy.etl.dbSourceDatax.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.dbSourceDatax.dto.DbSourceDataxDTO;
import com.easy.etl.dbSourceDatax.entity.DbSourceDatax;
import com.easy.etl.dbSourceDatax.vo.DbSourceDataxVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 数据源datax模板-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IDbSourceDataxService extends IBaseService<DbSourceDatax> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param dbSourceDatax
	 */
	public boolean saveOrUpdateDbSourceDatax(DbSourceDatax dbSourceDatax);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<DbSourceDataxVo> queryDbSourceDataxPage(IPage<DbSourceDataxVo> page, DbSourceDataxDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<DbSourceDataxVo> queryDbSourceDataxAll(DbSourceDataxDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public DbSourceDataxVo queryDbSourceDataxByCondition(DbSourceDataxDTO param);
	//================代码生成器生成代码结束=================
}
