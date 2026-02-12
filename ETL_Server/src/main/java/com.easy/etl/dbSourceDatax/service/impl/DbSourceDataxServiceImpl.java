package com.easy.etl.dbSourceDatax.service.impl;
import java.util.List;

import com.easy.etl.dbSourceDatax.dto.DbSourceDataxDTO;
import com.easy.etl.dbSourceDatax.entity.DbSourceDatax;
import com.easy.etl.dbSourceDatax.mapper.DbSourceDataxMapper;
import com.easy.etl.dbSourceDatax.service.IDbSourceDataxService;
import com.easy.etl.dbSourceDatax.vo.DbSourceDataxVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 数据源datax模板-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class DbSourceDataxServiceImpl extends BaseServiceImpl<DbSourceDataxMapper, DbSourceDatax> implements IDbSourceDataxService {

	/**
	     * 保存或修改Entity
	 * @param dbSourceDatax
	 */
	@Override
	public boolean saveOrUpdateDbSourceDatax(DbSourceDatax dbSourceDatax) {
		return saveOrUpdate(dbSourceDatax);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<DbSourceDataxVo> queryDbSourceDataxPage(IPage<DbSourceDataxVo> page, DbSourceDataxDTO param) {
		return page.setRecords(baseMapper.queryDbSourceDataxPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<DbSourceDataxVo> queryDbSourceDataxAll(DbSourceDataxDTO param) {
		return baseMapper.queryDbSourceDataxAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public DbSourceDataxVo queryDbSourceDataxByCondition(DbSourceDataxDTO param) {
		return baseMapper.queryDbSourceDataxByCondition(param);
	}
}
