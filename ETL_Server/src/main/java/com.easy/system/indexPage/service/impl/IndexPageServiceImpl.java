package com.easy.system.indexPage.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.indexPage.dto.IndexPageDTO;
import com.easy.system.indexPage.entity.IndexPage;
import com.easy.system.indexPage.mapper.IndexPageMapper;
import com.easy.system.indexPage.service.IIndexPageService;
import com.easy.system.indexPage.vo.IndexPageVo;

/**
 * 系统首页-服务类
 * @author
 * @time
 */
@Service
public class IndexPageServiceImpl extends BaseServiceImpl<IndexPageMapper, IndexPage> implements IIndexPageService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateindexPage(IndexPage systemIndexPage) {
		return saveOrUpdate(systemIndexPage);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<IndexPageVo> queryindexPagePage(IPage<IndexPageVo> page, IndexPageDTO param) {
		return page.setRecords(baseMapper.queryindexPagePage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<IndexPageVo> queryindexPageAll(IndexPageDTO param) {
		return baseMapper.queryindexPageAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public IndexPageVo queryindexPageByCondition(IndexPageDTO param) {
		return baseMapper.queryindexPageByCondition(param);
	}
}
