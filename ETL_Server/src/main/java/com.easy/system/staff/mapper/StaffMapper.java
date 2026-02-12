package com.easy.system.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.staff.dto.StaffDTO;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.vo.StaffVo;

/**
 * 用户表-接口
 *
 * @author
 * @time
 */
@Mapper
public interface StaffMapper extends MapperDao<Staff> {

	/**
	 * 用户登陆
	 * @param userName 用户名
	 * @return
	 */
	StaffVo userLogin(String userName);

	/**
	 * 用户刷新
	 * @return
	 */
	StaffVo userInfoRefresh(String staffId);

	/**
	 * 根据主键查询单个用户
	 *
	 * @param param
	 * @return
	 */
	StaffVo queryStaffById(@Param("param") StaffDTO param);

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<StaffVo> queryStaffPage(IPage<StaffVo> page, @Param("param") StaffDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<StaffVo> queryStaffAll(@Param("param") StaffDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	StaffVo queryStaffByCondition(@Param("param") StaffDTO param);
	// =============代码生成器生成代码结束==============
}
