package com.easy.system.role.service;


import java.util.List;

import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.role.dto.RoleFunctionDTO;
import com.easy.system.role.entity.RoleFunction;
import com.easy.system.role.vo.RoleFunctionVo;

/**
 * 角色功能关联表-服务类
 * @author
 * @time
 */
public interface  IRoleFunctionService extends IBaseService<RoleFunction> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param roleFunction
	 */
	public boolean saveOrUpdateRoleFunction(RoleFunction roleFunction);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<RoleFunctionVo> queryRoleFunctionAll(RoleFunctionDTO param);

	//================代码生成器生成代码结束=================
}
