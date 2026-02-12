package com.easy.system.role.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import com.easy.system.role.dto.RoleFunctionDTO;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.mapper.RoleFunctionMapper;
import com.easy.system.role.service.IRoleFunctionService;
import com.easy.system.role.vo.RoleFunctionVo;

/**
 * 角色功能关联表-服务类
 * @author
 * @time
 */
@Service
public class RoleFunctionServiceImpl extends BaseServiceImpl<RoleFunctionMapper, RoleFunction> implements IRoleFunctionService {

	/**
	     * 保存或修改Entity
	 * @param company
	 */
	@Override
	public boolean saveOrUpdateRoleFunction(RoleFunction roleFunction) {
		return saveOrUpdate(roleFunction);
	}

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	@Override
	public List<RoleFunctionVo> queryRoleFunctionAll(RoleFunctionDTO param) {
		return baseMapper.queryRoleFunctionAll(param);
	}

}
