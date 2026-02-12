package com.easy.system.file.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.kit.sql.SqlKit;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.file.dto.UserResourcesDTO;
import com.easy.system.file.entity.UserResources;
import com.easy.system.file.mapper.UserResourcesMapper;
import com.easy.system.file.service.IUserResourcesService;
import com.easy.system.file.vo.UserResourcesVo;

/**
 * 用户资源-服务类
 * @author
 * @time
 */
@Service
public class UserResourcesServiceImpl extends BaseServiceImpl<UserResourcesMapper, UserResources> implements IUserResourcesService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateUserResources(UserResources userResources) {
		return saveOrUpdate(userResources);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<UserResourcesVo> queryUserResourcesPage(IPage<UserResourcesVo> page, UserResourcesDTO param) {
		return page.setRecords(baseMapper.queryUserResourcesPage(page, param));
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<UserResourcesVo> queryUserResourcesAll(UserResourcesDTO param) {
		return baseMapper.queryUserResourcesAll(param);
	}

	/**
	  * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public UserResourcesVo queryUserResourcesByCondition(UserResourcesDTO param) {
		return baseMapper.queryUserResourcesByCondition(param);
	}

	/**
	 * 删除用户资源
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteResourcesById(String id) {
		return this.deleteByCondition("DELETE FROM TF_USER_RESOURCES WHERE INSTR(FULL_PATH,'"+SqlKit.filter(id)+"')>0");
	}
}
