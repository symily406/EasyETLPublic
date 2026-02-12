package com.easy.etl.projectMember.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectMember.entity.ProjectMember;
import com.easy.etl.projectMember.entity.ProjectMemberSpaceLayerRel;
import com.easy.etl.projectMember.service.IProjectMemberService;
import com.easy.etl.projectMember.service.IProjectMemberSpaceLayerRelService;
import com.easy.etl.projectMember.vo.ProjectMemberVo;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import com.easy.system.staff.dto.StaffDTO;
import com.easy.system.staff.vo.StaffVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;

import java.util.Arrays;
import java.util.List;


/**
 *  项目成员-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectMember")
public class ProjectMemberController extends BaseController {

	@Autowired
	private IProjectMemberService projectMemberService;

	@Autowired
	private IProjectMemberSpaceLayerRelService projectMemberSpaceLayerRelService;

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目工作空间")
	@GetMapping(value = "/queryProjectMemberWorkSpace")
	public ApiResult<List<ProjectMemberWorkSpaceVo>> queryProjectMemberWorkSpace(ProjectMemberDTO param) {
		param.setStaffId(param.getUserId());
		List<ProjectMemberWorkSpaceVo> projectMemberWorkSpaceVos=projectMemberService.queryProjectMemberWorkSpace(param);
		return ApiResult.ok(projectMemberWorkSpaceVos);
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目成员-工作空间")
	@GetMapping(value = "/queryProjectWorkSpace")
	public ApiResult<List<ProjectMemberSpaceLayerRel>> queryProjectWorkSpace(@NotNull(message = "主键不能为空") String memberId) {
		List<ProjectMemberSpaceLayerRel> list=projectMemberSpaceLayerRelService.list(
				new LambdaQueryWrapper<ProjectMemberSpaceLayerRel>()
						.eq(ProjectMemberSpaceLayerRel::getMemberId,memberId)
						.eq(ProjectMemberSpaceLayerRel::getEnable,1)

		);
		return ApiResult.ok(list);
	}

	/**
	 * 保存或修改
	 * @param projectMember
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目成员-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:etl:projectMember:add"})
	public ApiResult saveOrUpdate(@Validated ProjectMember projectMember,@NotNull(message = "请选择工作空间") String memberArrs) {
		if(projectMemberService.saveOrUpdateProjectMember(projectMember,memberArrs)) {
			return ApiResult.ok(projectMember);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目成员-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:etl:projectMember:queryPage"})
	public ApiResult<IPage<ProjectMemberVo>> queryPage(Query query, ProjectMemberDTO param) {
		page = projectMemberService.queryProjectMemberPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 项目成员-启用或禁用
	 *
	 * @param memberId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "项目成员-启用或禁用")
	@GetMapping(value = "/setEnableById")
	@SaCheckPermission(value  = { "admin:etl:projectMember:setEnable" })
	public ApiResult setEnableById(@NotNull(message = "主键不能为空") String memberId,int enable) {
		projectMemberService.update(null,new UpdateWrapper<ProjectMember>().lambda().set(ProjectMember::getEnable,enable).eq(ProjectMember::getMemberId,memberId));
		return ApiResult.ok();
	}

}
