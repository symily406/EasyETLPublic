package com.easy.etl.projectWorkSpaceLayer.controller;

import javax.validation.constraints.NotNull;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectWorkSpaceLayer.dto.ProjectWorkSpaceLayerDTO;
import com.easy.etl.projectWorkSpaceLayer.entity.ProjectWorkSpaceLayer;
import com.easy.etl.projectWorkSpaceLayer.service.IProjectWorkSpaceLayerService;
import com.easy.etl.projectWorkSpaceLayer.vo.ProjectWorkSpaceLayerVo;
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

import java.util.List;


/**
 *  项目工作空间分层-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectWorkSpaceLayer")
public class ProjectWorkSpaceLayerController extends BaseController {

	@Autowired
	private IProjectWorkSpaceLayerService projectWorkSpaceLayerService;


	/**
	 * 保存或修改
	 * @param projectWorkSpaceLayer
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目工作空间分层-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:etl:projectWorkSpaceLayer:add","admin:etl:projectWorkSpaceLayer:edit"})
	public ApiResult saveOrUpdate(@Validated ProjectWorkSpaceLayer projectWorkSpaceLayer) {
		if(projectWorkSpaceLayerService.saveOrUpdateProjectWorkSpaceLayer(projectWorkSpaceLayer)) {
			return ApiResult.ok(projectWorkSpaceLayer);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目工作空间分层-分页查询")
	@GetMapping(value = "/queryAll")
	public ApiResult<List<ProjectWorkSpaceLayerVo>> queryAll(ProjectWorkSpaceLayerDTO param) {
		List<ProjectWorkSpaceLayerVo> list = projectWorkSpaceLayerService.queryProjectWorkSpaceLayerAll(param);
		return ApiResult.ok(list);
	}

	/**
	 * 根据主键返回
	 *
	 * @param workSpaceLayerId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目工作空间分层-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:etl:projectWorkSpaceLayer:getById" })
	public ApiResult<ProjectWorkSpaceLayer> getById(@NotNull(message = "主键不能为空") String workSpaceLayerId) {
		return ApiResult.ok(projectWorkSpaceLayerService.getById(workSpaceLayerId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param workSpaceLayerId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "项目工作空间分层-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:etl:projectWorkSpaceLayer:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String workSpaceLayerId) {
		projectWorkSpaceLayerService.removeById(workSpaceLayerId);
		return ApiResult.ok();
	}
}
