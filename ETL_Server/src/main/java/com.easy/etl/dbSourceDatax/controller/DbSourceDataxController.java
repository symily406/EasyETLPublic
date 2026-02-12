package com.easy.etl.dbSourceDatax.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.dbSourceDatax.dto.DbSourceDataxDTO;
import com.easy.etl.dbSourceDatax.entity.DbSourceDatax;
import com.easy.etl.dbSourceDatax.service.IDbSourceDataxService;
import com.easy.etl.dbSourceDatax.vo.DbSourceDataxVo;
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
import java.util.Arrays;


/**
 *  数据源datax模板-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/dbSourceDatax")
public class DbSourceDataxController extends BaseController {

	@Autowired
	private IDbSourceDataxService dbSourceDataxService;


	/**
	 * 保存或修改
	 * @param dbSourceDatax
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源datax模板-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:etl:dbSourceDatax:add","admin:etl:dbSourceDatax:edit"})
	public ApiResult saveOrUpdate(@Validated DbSourceDatax dbSourceDatax) {
		if(dbSourceDataxService.saveOrUpdateDbSourceDatax(dbSourceDatax)) {
			return ApiResult.ok(dbSourceDatax);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源datax模板-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:etl:dbSourceDatax:queryPage"})
	public ApiResult<IPage<DbSourceDataxVo>> queryPage(Query query, DbSourceDataxDTO param) {
		page = dbSourceDataxService.queryDbSourceDataxPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param templateId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源datax模板-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:etl:dbSourceDatax:getById" })
	public ApiResult<DbSourceDatax> getById(@NotNull(message = "主键不能为空") String templateId) {
		return ApiResult.ok(dbSourceDataxService.getById(templateId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param templateId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "数据源datax模板-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:etl:dbSourceDatax:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String templateId) {
		dbSourceDataxService.removeById(templateId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param templateIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源datax模板-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:etl:dbSourceDatax:batchDeleteById" })
	public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String templateIds) {
		dbSourceDataxService.removeBatchByIds(Arrays.asList(StringUtils.split(templateIds, ",")));
		return ApiResult.ok();
	}
}
