package com.easy.etl.dbSourceType.mapper;


import java.util.List;

import com.easy.etl.dbSourceType.dto.DbSourceTypeDTO;
import com.easy.etl.dbSourceType.entity.DbSourceType;
import com.easy.etl.dbSourceType.vo.DbSourceTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 数据源类型-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface DbSourceTypeMapper extends MapperDao<DbSourceType> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<DbSourceTypeVo> queryDbSourceTypePage(IPage<DbSourceTypeVo> page, @Param("param") DbSourceTypeDTO param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<DbSourceTypeVo> queryDbSourceTypeAll(@Param("param")DbSourceTypeDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	DbSourceTypeVo queryDbSourceTypeByCondition(@Param("param")DbSourceTypeDTO  param);
	//=============代码生成器生成代码结束==============
}
