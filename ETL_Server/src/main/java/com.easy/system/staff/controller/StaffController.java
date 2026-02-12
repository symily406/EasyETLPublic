package com.easy.system.staff.controller;


import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.system.staff.dto.StaffDTO;
import com.easy.system.staff.dto.StaffDutyDTO;
import com.easy.system.staff.dto.StaffRoleDTO;
import com.easy.system.staff.entity.Staff;
import com.easy.system.staff.entity.StaffInfo;
import com.easy.system.staff.service.IStaffDutyService;
import com.easy.system.staff.service.IStaffRoleService;
import com.easy.system.staff.service.IStaffService;
import com.easy.system.staff.vo.PersonalCenter;
import com.easy.system.staff.vo.StaffRoleVo;
import com.easy.system.staff.vo.StaffVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;



/**
 *  用户表-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/staff")
public class StaffController extends BaseController {

	@Autowired
	private IStaffService staffService;

	@Autowired
	private IStaffRoleService staffRoleService;
	@Autowired
	private IStaffDutyService staffDutyService;


	/**
	 *个人中心用户信息
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "个人中心用户信息")
	@GetMapping(value = "/queryPersonalCenterStaff")
	public ApiResult<PersonalCenter> queryPersonalCenterStaff(StaffDTO param,StaffRoleDTO staffRoleDTO,StaffDutyDTO staffDutyDTO) {
		PersonalCenter personalCenter=new PersonalCenter();
		param.setStaffId(StpUtil.getExtra("staffId").toString());
		StaffVo staffVo=staffService.queryStaffById(param);
		personalCenter.setStaffVo(staffVo);
		staffRoleDTO.setStaffId(StpUtil.getExtra("staffId").toString());
		personalCenter.setStaffRoleVos(staffRoleService.queryStaffRoleAll(staffRoleDTO));
		staffDutyDTO.setStaffId(StpUtil.getExtra("staffId").toString());
		personalCenter.setStaffDutyVos(staffDutyService.queryStaffDutyAll(staffDutyDTO));
		return ApiResult.ok(personalCenter);
	}


	/**
	    * 用户角色
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户角色")
	@GetMapping(value = "/queryRoles")
	public ApiResult<List<StaffRoleVo>> queryRoles(StaffRoleDTO param) {
		List<StaffRoleVo> list = staffRoleService.queryStaffRoleAll(param);
		return ApiResult.ok(list);
	}


	/**
	 * 用户锁定解锁
	 * @param staff
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户锁定解锁")
	@GetMapping(value = "/modifyUserLock")
	public ApiResult modifyUserLock(@NotEmpty(message = "用户不能为空") String staffId,int isLock) {
		Staff staff=new Staff();
		staff.setStaffId(staffId);
		staff.setIsLock(isLock);
		staffService.saveOrUpdate(staff);
		return ApiResult.ok();
	}


	/**
	 * 保存或修改
	 * @param staff
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户表-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  ={"admin:system:staff:add","admin:system:staff:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Staff staff,@Validated StaffInfo staffInfo,String confirmPassword,@NotEmpty(message = "角色不能为空")String roles,@NotEmpty(message = "岗位不能为空") String dutyIds) {
		if (StringUtils.isBlank(staff.getStaffId())) {
			if (StringUtils.isBlank(staff.getUserName())) {
				return ApiResult.fail("用户名不能为空");
			}
			if (StringUtils.isBlank(staff.getPassword())) {
				return ApiResult.fail("密码不能为空");
			}
			if (StringUtils.isBlank(confirmPassword)) {
				return ApiResult.fail("确认密码不能为空");
			}
			if (!StringUtils.equals(staff.getPassword(), confirmPassword)) {
				return ApiResult.fail("两次输入的密码不一至");
			}
			long count=staffService.count(new QueryWrapper<Staff>().lambda().eq(Staff::getUserName, staff.getUserName()));

			if (count>0) {
				return ApiResult.fail("用户名已存在");
			}
			staff.setSalt(IdUtil.fastSimpleUUID());
			staff.setPassword(SecureUtil.md5(staff.getPassword().concat(staff.getSalt())));
		}else {
			staff.setUserName(null);
		}
		if(staffService.saveOrUpdateStaff(staff,staffInfo,Arrays.asList(StringUtils.split(roles,",")),Arrays.asList(StringUtils.split(dutyIds,",")))) {
			return ApiResult.ok(staff);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 修改密码
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户表-修改密码")
	@PostMapping(value = "/updatePassowrd")
	public ApiResult updatePassowrd(@NotEmpty(message = "用户不能为空") String staffId,@NotEmpty(message = "密码不能为空") String password,@NotEmpty(message = "确认密码不能为空") String confirmPassword) {

		if (!StringUtils.equals(password, confirmPassword)) {
			return ApiResult.fail("两次输入的密码不一至");
		}
		Staff staff=new Staff();
		staff.setStaffId(staffId);
		staff.setSalt(IdUtil.fastSimpleUUID());
		staff.setPassword(SecureUtil.md5(password.concat(staff.getSalt())));
		if(staffService.updatePassword(staff)) {
			return ApiResult.ok(staff);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@DebugAnnotation(desc = "用户表-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:staff:queryPage","admin:system:flowable:workflow:process:designer","admin:system:flowable:work:todo:todoList"},mode =SaMode.OR)
	public ApiResult<IPage<StaffVo>> queryPage(Query query,StaffDTO param) {
		page = staffService.queryStaffPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 查询全部用户
	 */
	@DebugAnnotation(desc = "查询全部用户")
	@GetMapping(value = "/queryAll")
	@SaCheckPermission(value  ={"admin:system:staff:queryAll"},mode =SaMode.OR)
	public ApiResult<List<StaffVo>> queryPage(StaffDTO param) {
		List<StaffVo> list = staffService.queryStaffAll(param);
		return ApiResult.ok(list);
	}



	/**
	 * 根据主键返回
	 *
	 * @param staffId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户表-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:staff:getById" })
	public ApiResult<StaffVo> getById(StaffDTO param) {
		if (StringUtils.isBlank(param.getStaffId())) {
			return ApiResult.fail("用户主键不能为空");
		}
		return ApiResult.ok(staffService.queryStaffById(param));
	}

	/**
	 * 根据主键删除
	 *
	 * @param staffId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户表-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:staff:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String staffId) {
		staffService.removeById(staffId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param staffIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户表-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  ={ "admin:system:staff:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String staffIds) {
		staffService.removeBatchByIds(Arrays.asList(StringUtils.split(staffIds, ",")));
		return ApiResult.ok();
	}
}
