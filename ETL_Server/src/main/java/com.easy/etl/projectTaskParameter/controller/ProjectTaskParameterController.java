package com.easy.etl.projectTaskParameter.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 *  任务参数-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskParameter")
public class ProjectTaskParameterController extends BaseController {

	@Autowired
	private IProjectTaskParameterService projectTaskParameterService;
	@Autowired
	private IProjectTaskService projectTaskService;


	@SaCheckLogin
	@DebugAnnotation(desc = "项目参数")
	@GetMapping(value = "/queryAll")
	public ApiResult<List<ProjectTaskParameter>> queryAll(ProjectTaskDTO param) {
		List<ProjectTaskParameter> list =projectTaskParameterService.queryParametersByTaskId(param);
		return ApiResult.ok(list);
	}
}
