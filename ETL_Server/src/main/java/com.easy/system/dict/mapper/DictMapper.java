package com.easy.system.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.dict.dto.DictDTO;
import com.easy.system.dict.entity.Dict;
import com.easy.system.dict.vo.DictVo;

/**
 * 字典主表-接口
 *
 * @author
 * @time
 */
@Mapper
public interface DictMapper extends MapperDao<Dict> {

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<DictVo> queryDictPage(IPage<DictVo> page, @Param("param") DictDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<DictVo> queryDictAll(@Param("param") DictDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	DictVo queryDictByCondition(@Param("param") DictDTO param);
	// =============代码生成器生成代码结束==============
}
