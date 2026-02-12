package com.easy.system.staff.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.staff.dto.StaffDTO;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.vo.StaffVo;

/**
 * 用户表-服务类
 *
 * @author
 * @time
 */
/**
 * @param staffId
 */
public interface IStaffService extends IBaseService<Staff> {



	/**
	 * 修改登陆编码、登陆次数
	 * @param staffId
	 */
	public void updateLogin(String staffId,String loginCode,int loginNum);


	/**
	   * 修改密码
	 *
	 */
	public boolean updatePassword(Staff staff);


	/**
	 * 用户登陆
	 * @param userName 用户名
	 * @return
	 */
	StaffVo userLogin(String userName);

	/**
	 * 用户刷新
	 * @param staffId 用户ID
	 * @return
	 */
	StaffVo userInfoRefresh(String staffId);

	/**
	    * 根据主键查询单个用户
	 * @param param
	 * @return
	 */
	public StaffVo queryStaffById(StaffDTO param);

	// ================代码生成器生成代码开始=================
	/**
	 * 保存或修改Entity
	 *
	 */
	public boolean saveOrUpdateStaff(Staff staff,StaffInfo staffInfo,List<String> roles,List<String> dutyIds);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<StaffVo> queryStaffPage(IPage<StaffVo> page, StaffDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<StaffVo> queryStaffAll(StaffDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	public StaffVo queryStaffByCondition(StaffDTO param);
	// ================代码生成器生成代码结束=================
}
