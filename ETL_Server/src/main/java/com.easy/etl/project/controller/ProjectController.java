package com.easy.etl.project.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.project.dto.ProjectDTO;
import com.easy.etl.project.entity.Project;
import com.easy.etl.project.entity.ProjectWorkSpaceLayerRel;
import com.easy.etl.project.service.IProjectService;
import com.easy.etl.project.service.IProjectWorkSpaceLayerRelService;
import com.easy.etl.project.vo.ProjectVo;
import com.easy.etl.projectDbSource.vo.ProjectDbSourceVo;
import com.easy.etl.projectMember.dto.ProjectMemberDTO;
import com.easy.etl.projectMember.entity.ProjectMember;
import com.easy.etl.projectMember.service.IProjectMemberService;
import com.easy.etl.projectMember.vo.ProjectMemberWorkSpaceVo;
import com.easy.etl.projectWorkSpace.service.IProjectWorkSpaceService;
import com.easy.etl.projectWorkSpaceLayer.service.IProjectWorkSpaceLayerService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *  项目管理-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/project")
public class ProjectController extends BaseController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectMemberService projectMemberService;

	@Autowired
	private IProjectWorkSpaceLayerRelService projectWorkSpaceLayerRelService;



	/**
	 * 保存或修改
	 * @param project
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目管理-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:etl:project:add","admin:etl:project:edit"})
	public ApiResult saveOrUpdate(@Validated Project project,String workSpaces) {
		if (StringUtils.isEmpty(workSpaces)){
			return  ApiResult.fail("请为项目配置工作空间");
		}
		if(projectService.saveOrUpdateProject(project,workSpaces)) {
			return ApiResult.ok(project);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目工作空间分层关联-根据项目返回")
	@GetMapping(value = "/queryAllByProjectId")
	@SaCheckPermission(value  = {"admin:etl:project:add","admin:etl:project:edit"})
	public ApiResult<List<ProjectWorkSpaceLayerRel>> queryAllByProjectId(@NotNull(message = "项目不能为空") String projectId) {
		List<ProjectWorkSpaceLayerRel> list = projectWorkSpaceLayerRelService.list(new LambdaQueryWrapper<ProjectWorkSpaceLayerRel>()
				.eq(ProjectWorkSpaceLayerRel::getProjectId,projectId)
				.eq(ProjectWorkSpaceLayerRel::getEnable,1)
		);
		return ApiResult.ok(list);
	}

	/**
	 * 项目首页数据
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目首页数据-分页查询")
	@GetMapping(value = "/queryIndexPage")
	public ApiResult<IPage<ProjectVo>> queryIndexPage(Query query, ProjectDTO param) {
		param.setProjectStaffId(param.getUserId());
		IPage<ProjectVo> result = projectService.queryProjectPage(Condition.getPage(query),param);
		if (result!=null&&result.getRecords()!=null&&!result.getRecords().isEmpty()){
			ProjectMemberDTO projectMemberDTO=new ProjectMemberDTO();
			projectMemberDTO.setStaffId(projectMemberDTO.getStaffId());
			List<ProjectMemberWorkSpaceVo> projectMemberWorkSpaceVos=projectMemberService.queryProjectMemberWorkSpace(projectMemberDTO);
			for (ProjectVo vo:result.getRecords()) {
				List<ProjectMemberWorkSpaceVo> list= Optional.of(projectMemberWorkSpaceVos.stream().filter(v->v.getProjectId().equals(vo.getProjectId())).collect(Collectors.toList())).orElse(new ArrayList<>());
				vo.setMemberWorkSpaceVos(list);
				vo.setTotalTask(list.stream().map(ProjectMemberWorkSpaceVo::getTaskNum).reduce(BigDecimal.ZERO, BigDecimal::add));
				vo.setFailTask(list.stream().map(ProjectMemberWorkSpaceVo::getFailTaskNum).reduce(BigDecimal.ZERO, BigDecimal::add));
			}
		}
		return ApiResult.ok(result);
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目管理-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:etl:project:queryPage"})
	public ApiResult<IPage<ProjectVo>> queryPage(Query query, ProjectDTO param) {
		page = projectService.queryProjectPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "查询全部项目")
	@GetMapping(value = "/queryAll")
	public ApiResult<List<ProjectVo>> queryAll(ProjectDTO param) {
		param.setProjectStaffId(param.getUserId());
		List<ProjectVo> list = projectService.queryProjectAll(param);
		return ApiResult.ok(list);
	}

	/**
	 * 根据主键返回
	 *
	 * @param projectId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目管理-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:etl:project:getById" })
	public ApiResult<Project> getById(@NotNull(message = "主键不能为空") String projectId) {
		return ApiResult.ok(projectService.getById(projectId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param projectId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "项目管理-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:etl:project:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String projectId) {
		projectService.removeById(projectId);
		return ApiResult.ok();
	}
}
