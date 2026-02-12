package com.easy.system.indexPage.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.indexPage.dto.IndexPageDTO;
import com.easy.system.indexPage.entity.IndexPage;
import com.easy.system.indexPage.vo.IndexPageVo;

/**
 * 系统首页-接口
 * @author
 * @time
 */
@Mapper
public interface IndexPageMapper extends MapperDao<IndexPage> {

             //=============代码生成器生成代码开始==============
	/**
	  *   分页
	 * @param param
	 * @return
	 */
	List<IndexPageVo> queryindexPagePage(IPage<IndexPageVo> page,@Param("param")IndexPageDTO  param);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<IndexPageVo> queryindexPageAll(@Param("param")IndexPageDTO  param);

	/**
	  *  根据条件查询单个
	 * @param param
	 * @return
	 */
	IndexPageVo queryindexPageByCondition(@Param("param")IndexPageDTO  param);
	//=============代码生成器生成代码结束==============
}
