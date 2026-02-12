package com.easy.etl.dbSourceType.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.dbSourceType.dto.DbSourceTypeDTO;
import com.easy.etl.dbSourceType.entity.DbSourceType;
import com.easy.etl.dbSourceType.service.IDbSourceTypeService;
import com.easy.etl.dbSourceType.vo.DbSourceTypeVo;
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
import java.util.List;


/**
 *  数据源类型-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/dbSourceType")
public class DbSourceTypeController extends BaseController {

	@Autowired
	private IDbSourceTypeService dbSourceTypeService;


	/**
	 * 保存或修改
	 * @param dbSourceType
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源类型-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:etl:sourceType:dbSourceType:add","admin:etl:sourceType:dbSourceType:edit"})
	public ApiResult saveOrUpdate(@Validated DbSourceType dbSourceType) {
		if(dbSourceTypeService.saveOrUpdateDbSourceType(dbSourceType)) {
			return ApiResult.ok(dbSourceType);
		}
		return ApiResult.fail("更新失败");
	}


	/**
	 * 数据源类型-全部
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源类型-全部")
	@GetMapping(value = "/queryAll")
	@SaCheckPermission(value  ={"admin:etl:sourceType:dbSourceType:queryAll"})
	public ApiResult<List<DbSourceTypeVo>> queryAll(DbSourceTypeDTO param) {
		List<DbSourceTypeVo> list = dbSourceTypeService.queryDbSourceTypeAll(param);
		return ApiResult.ok(list);
	}

	/**
	 * 根据主键返回
	 * @param sourceTypeId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "数据源类型-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:etl:sourceType:dbSourceType:getById" })
	public ApiResult<DbSourceType> getById(@NotNull(message = "主键不能为空") String sourceTypeId) {
		return ApiResult.ok(dbSourceTypeService.getById(sourceTypeId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param sourceTypeId
	 * @return
	 */
   	@SaCheckLogin
	@DebugAnnotation(desc = "数据源类型-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:etl:sourceType:dbSourceType:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String sourceTypeId) {
		dbSourceTypeService.removeById(sourceTypeId);
		return ApiResult.ok();
	}


}
