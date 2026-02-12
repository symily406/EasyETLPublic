package com.easy.etl.dbSource.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.mapper.DbSourceMapper;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class DbSourceServiceImpl extends BaseServiceImpl<DbSourceMapper, DbSource> implements IDbSourceService {

	/**
	     * 保存或修改Entity
	 */
	@Override
	public boolean saveOrUpdateDbSource(DbSource dbSource) {
		return saveOrUpdate(dbSource);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<DbSourceVo> queryDbSourcePage(IPage<DbSourceVo> page, DbSourceDTO param) {
		return page.setRecords(baseMapper.queryDbSourcePage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<DbSourceVo> queryDbSourceAll(DbSourceDTO param) {
		return baseMapper.queryDbSourceAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public DbSourceVo queryDbSourceByCondition(DbSourceDTO param) {
		return baseMapper.queryDbSourceByCondition(param);
	}
}
