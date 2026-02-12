package com.easy.system.indexPage.controller;


import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

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
import com.easy.system.indexPage.dto.IndexPageDTO;
import com.easy.system.indexPage.entity.IndexPage;
import com.easy.system.indexPage.service.IIndexPageService;
import com.easy.system.indexPage.vo.IndexPageVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  系统首页-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/indexPage")
public class IndexPageController extends BaseController {

	@Autowired
	private IIndexPageService indexPageService;


	/**
	 * 保存或修改
	 * @param systemIndexPage
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "系统首页-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:indexPage:add","admin:system:indexPage:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated IndexPage systemIndexPage) {
		if(indexPageService.saveOrUpdateindexPage(systemIndexPage)) {
			return ApiResult.ok(systemIndexPage);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 返回系统首页
	 * @param query
	 * @param param
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "系统首页-列表查询")
	@GetMapping(value = "/queryList")
	public ApiResult<List<IndexPageVo>> queryList(Query query,IndexPageDTO param) {
		List<IndexPageVo> list = indexPageService.queryindexPageAll(param);
		return ApiResult.ok(list);
	}


	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "系统首页-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:indexPage:queryPage"})
	public ApiResult<IPage<IndexPageVo>> queryPage(Query query,IndexPageDTO param) {
		page = indexPageService.queryindexPagePage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param menuId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "系统首页-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:indexPage:getById" })
	public ApiResult<IndexPage> getById(@NotNull(message = "主键不能为空") String menuId) {
		return ApiResult.ok(indexPageService.getById(menuId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param menuId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "系统首页-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  ={ "admin:system:indexPage:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String menuId) {
		indexPageService.removeById(menuId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param menuIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "系统首页-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:indexPage:batchDeleteById" })
	public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String menuIds) {
		indexPageService.removeBatchByIds(Arrays.asList(StringUtils.split(menuIds, ",")));
		return ApiResult.ok();
	}
}
