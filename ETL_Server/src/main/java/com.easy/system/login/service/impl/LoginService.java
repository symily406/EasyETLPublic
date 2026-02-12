package com.easy.system.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easy.system.login.dto.LoginDTO;
import com.easy.system.login.service.ILoginService;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.mapper.StaffMapper;


/**
 * 用户登陆-服务类
 * @author
 * @time
 */
@Service
public class LoginService implements ILoginService {
	@Autowired
	private  StaffMapper staffMapper;

	/**
	 * 用户登陆
	 */
	@Override
	public Staff login(LoginDTO loginDTO) {
//		Staff staff=staffMapper.selectOne(queryWrapper)
		return null;
	}



}
