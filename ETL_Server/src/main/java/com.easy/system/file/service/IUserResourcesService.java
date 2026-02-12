package com.easy.system.file.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.file.dto.UserResourcesDTO;
import com.easy.system.file.entity.UserResources;
import com.easy.system.file.vo.UserResourcesVo;

/**
 * 用户资源-服务类
 * @author
 * @time
 */
public interface  IUserResourcesService extends IBaseService<UserResources> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param userResources
	 */
	public boolean saveOrUpdateUserResources(UserResources userResources);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<UserResourcesVo> queryUserResourcesPage(IPage<UserResourcesVo> page,UserResourcesDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<UserResourcesVo> queryUserResourcesAll(UserResourcesDTO param);

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	public UserResourcesVo queryUserResourcesByCondition(UserResourcesDTO param);

	/**
	 * 删除用户资源
	 * @param id
	 * @return
	 */
	public boolean deleteResourcesById(String id);
	//================代码生成器生成代码结束=================
}
