package com.easy.system.dict.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.dict.dto.DictDTO;
import com.easy.system.dict.entity.Dict;
import com.easy.system.dict.vo.DictVo;

/**
 * 字典主表-服务类
 * @author
 * @time
 */
public interface  IDictService extends IBaseService<Dict> {



	/**
	 * 根据dictCode查询字典
	 * @param dictCode
	 * @return
	 */
	public DictVo queryDictByCode(String dictCode);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param dict
	 */
	public boolean saveOrUpdateDict(Dict dict);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<DictVo> queryDictPage(IPage<DictVo> page,DictDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<DictVo> queryDictAll(DictDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public DictVo queryDictByCondition(DictDTO param);
	//================代码生成器生成代码结束=================
}
