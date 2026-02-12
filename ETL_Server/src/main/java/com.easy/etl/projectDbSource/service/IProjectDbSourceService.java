package com.easy.etl.projectDbSource.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectDbSource.dto.ProjectDbSourceDTO;
import com.easy.etl.projectDbSource.entity.ProjectDbSource;
import com.easy.etl.projectDbSource.vo.ProjectDbSourceVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 引用的数据源-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IProjectDbSourceService extends IBaseService<ProjectDbSource> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param dbJson
	 */
	public boolean saveOrUpdateProjectDbSource(String dbJson);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ProjectDbSourceVo> queryProjectDbSourcePage(IPage<ProjectDbSourceVo> page, ProjectDbSourceDTO param);

	List<ProjectDbSourceVo> queryProjectDbSourceAll( ProjectDbSourceDTO param);

	//================代码生成器生成代码结束=================
}
