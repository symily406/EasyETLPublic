package com.easy.etl.projectDbSource.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.projectDbSource.dto.ProjectDbSourceDTO;
import com.easy.etl.projectDbSource.entity.ProjectDbSource;
import com.easy.etl.projectDbSource.service.IProjectDbSourceService;
import com.easy.etl.projectDbSource.vo.ProjectDbSourceVo;
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



/**
 *  引用的数据源-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/projectDbSource")
public class ProjectDbSourceController extends BaseController {

	@Autowired
	private IProjectDbSourceService projectDbSourceService;


	/**
	 * 保存或修改
	 * @param dbJson
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "引用的数据源-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:etl:projectDbSource:add"})
	public ApiResult saveOrUpdate(String dbJson) {
		if(StringUtils.isEmpty(dbJson)){
			return ApiResult.fail("请选择一个数据源");
		}

		if(projectDbSourceService.saveOrUpdateProjectDbSource(dbJson)) {
			return ApiResult.ok("添加成功");
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "引用的数据源-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:etl:projectDbSource:queryPage"})
	public ApiResult<IPage<ProjectDbSourceVo>> queryPage(Query query, ProjectDbSourceDTO param) {
		page = projectDbSourceService.queryProjectDbSourcePage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}



	/**
	 * 根据主键删除
	 *
	 * @param quoteId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "引用的数据源-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:etl:projectDbSource:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String quoteId) {
		projectDbSourceService.removeById(quoteId);
		return ApiResult.ok();
	}

}
