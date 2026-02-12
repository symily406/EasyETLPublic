package com.easy.system.login.service;

import com.easy.system.login.dto.LoginDTO;
import com.easy.system.staff.entity.Staff;

/**
 * 用户登陆-服务类
 *
 * @author
 * @time
 */
public interface ILoginService {
	/**
	 * 用户登陆
	 * @param loginDTO
	 * @return
	 */
	public Staff login(LoginDTO loginDTO);
}
