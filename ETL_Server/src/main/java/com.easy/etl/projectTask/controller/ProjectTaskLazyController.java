package com.easy.etl.projectTask.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTaskLazy;
import com.easy.etl.projectTask.service.IProjectTaskLazyService;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
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
import java.util.List;


/**
 *  项目任务依懒-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectTaskLazy")
public class ProjectTaskLazyController extends BaseController {

	@Autowired
	private IProjectTaskLazyService projectTaskLazyService;

	/**
	 * 保存或修改
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目任务依懒-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	public ApiResult saveOrUpdate(String lazy,String taskId) {
		if (StringUtils.isEmpty(taskId)){
			return ApiResult.fail("任务编号不能为空");
		}
		if(StringUtils.isNotBlank(lazy)){
			List<ProjectTaskLazy> layzs= JsonKit.parseList(lazy,ProjectTaskLazy.class);
			if(projectTaskLazyService.saveOrUpdateProjectTaskLazy(layzs,taskId)) {
				return ApiResult.ok("添加成功");
			}
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "项目任务依懒-分页查询")
	@GetMapping(value = "/queryPage")
	public ApiResult<IPage<ProjectTaskLazyVo>> queryPage(Query query, ProjectTaskLazyDTO param) {
		page = projectTaskLazyService.queryProjectTaskLazyPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}



	/**
	 * 根据主键删除
	 *
	 * @param taskLazyId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "项目任务依懒-根据主键删除")
	@GetMapping(value = "/deleteById")
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String taskLazyId) {
		projectTaskLazyService.removeById(taskLazyId);
		return ApiResult.ok();
	}
}
