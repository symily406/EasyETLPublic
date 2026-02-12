package com.easy.etl.dbSource.mapper;


import java.util.List;

import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.vo.DbSourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 数据源-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface DbSourceMapper extends MapperDao<DbSource> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<DbSourceVo> queryDbSourcePage(IPage<DbSourceVo> page, @Param("param") DbSourceDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<DbSourceVo> queryDbSourceAll(@Param("param")DbSourceDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	DbSourceVo queryDbSourceByCondition(@Param("param")DbSourceDTO  param);
	//=============代码生成器生成代码结束==============
}
