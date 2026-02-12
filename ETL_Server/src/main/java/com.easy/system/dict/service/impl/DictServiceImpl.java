package com.easy.system.dict.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.sql.SqlKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.dict.dto.DictDTO;
import com.easy.system.dict.entity.Dict;
import com.easy.system.dict.mapper.DictMapper;
import com.easy.system.dict.service.IDictService;
import com.easy.system.dict.vo.DictVo;

/**
 * 字典主表-服务类
 * @author
 * @time
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {


	/**
	 * 根据dictCode查询字典
	 * @param dictCode
	 * @return
	 */
	@Override
	public DictVo queryDictByCode(String dictCode) {
		DictVo dictVo=this.findFirst("SELECT * FROM TD_DICT D WHERE D.DICT_CODE='"+SqlKit.filter(dictCode)+"'",null, DictVo.class);
		return dictVo;
	}

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateDict(Dict dict) {
		return saveOrUpdate(dict);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<DictVo> queryDictPage(IPage<DictVo> page, DictDTO param) {
		return page.setRecords(baseMapper.queryDictPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<DictVo> queryDictAll(DictDTO param) {
		return baseMapper.queryDictAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public DictVo queryDictByCondition(DictDTO param) {
		return baseMapper.queryDictByCondition(param);
	}
}
