package com.easy.system.indexPage.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.indexPage.dto.IndexPageDTO;
import com.easy.system.indexPage.entity.IndexPage;
import com.easy.system.indexPage.vo.IndexPageVo;

/**
 * 系统首页-服务类
 * @author
 * @time
 */
public interface  IIndexPageService extends IBaseService<IndexPage> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param systemIndexPage
	 */
	public boolean saveOrUpdateindexPage(IndexPage systemIndexPage);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<IndexPageVo> queryindexPagePage(IPage<IndexPageVo> page,IndexPageDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<IndexPageVo> queryindexPageAll(IndexPageDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public IndexPageVo queryindexPageByCondition(IndexPageDTO param);
	//================代码生成器生成代码结束=================
}
