package com.easy.system.dict.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.dict.dto.DictValuesDTO;
import com.easy.system.dict.entity.DictValues;
import com.easy.system.dict.vo.DictValuesVo;

/**
 * 字典值-服务类
 * @author
 * @time
 */
public interface  IDictValuesService extends IBaseService<DictValues> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param dictValues
	 */
	public boolean saveOrUpdateDictValues(DictValues dictValues);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<DictValuesVo> queryDictValuesPage(IPage<DictValuesVo> page,DictValuesDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<DictValuesVo> queryDictValuesAll(DictValuesDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public DictValuesVo queryDictValuesByCondition(DictValuesDTO param);
	//================代码生成器生成代码结束=================
}
