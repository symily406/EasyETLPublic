package com.easy.system.dict.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.dict.dto.DictValuesDTO;
import com.easy.system.dict.entity.DictValues;
import com.easy.system.dict.mapper.DictValuesMapper;
import com.easy.system.dict.service.IDictValuesService;
import com.easy.system.dict.vo.DictValuesVo;

/**
 * 字典值-服务类
 * @author
 * @time
 */
@Service
public class DictValuesServiceImpl extends BaseServiceImpl<DictValuesMapper, DictValues> implements IDictValuesService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateDictValues(DictValues dictValues) {
		return saveOrUpdate(dictValues);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<DictValuesVo> queryDictValuesPage(IPage<DictValuesVo> page, DictValuesDTO param) {
		return page.setRecords(baseMapper.queryDictValuesPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<DictValuesVo> queryDictValuesAll(DictValuesDTO param) {
		return baseMapper.queryDictValuesAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public DictValuesVo queryDictValuesByCondition(DictValuesDTO param) {
		return baseMapper.queryDictValuesByCondition(param);
	}
}
