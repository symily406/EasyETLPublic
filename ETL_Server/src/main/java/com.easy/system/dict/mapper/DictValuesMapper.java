package com.easy.system.dict.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.dict.dto.DictValuesDTO;
import com.easy.system.dict.entity.DictValues;
import com.easy.system.dict.vo.DictValuesVo;

/**
 * 字典值-接口
 * @author
 * @time
 */
@Mapper
public interface DictValuesMapper extends MapperDao<DictValues> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<DictValuesVo> queryDictValuesPage(IPage<DictValuesVo> page,@Param("param")DictValuesDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<DictValuesVo> queryDictValuesAll(@Param("param")DictValuesDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	DictValuesVo queryDictValuesByCondition(@Param("param")DictValuesDTO  param);
	//=============代码生成器生成代码结束==============
}
