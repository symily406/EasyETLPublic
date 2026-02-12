package com.easy.system.dict.controller;


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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.system.dict.dto.DictValuesDTO;
import com.easy.system.dict.entity.DictValues;
import com.easy.system.dict.service.IDictValuesService;
import com.easy.system.dict.vo.DictValuesVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  字典值-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/dict/dictValues")
public class DictValuesController extends BaseController {

	@Autowired
	private IDictValuesService dictValuesService;

	/**
	    * 字典值-根据字典编码返回字典
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典值-根据字典编码返回字典")
	@GetMapping(value = "/queryDictValues")
	public ApiResult<List<DictValuesVo>> queryDictValues(DictValuesDTO param) {
		if (StringUtils.isBlank(param.getDictCode())) {
			return ApiResult.fail("归属字典编码不能为空");
		}
		List<DictValuesVo> list = dictValuesService.queryDictValuesAll(param);
		return ApiResult.ok(list);
	}


	/**
	 * 保存或修改
	 * @param dictValues
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典值-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:dictValues:add","admin:system:dictValues:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated DictValues dictValues) {
		if(dictValuesService.saveOrUpdateDictValues(dictValues)) {
			return ApiResult.ok(dictValues);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典值-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:dictValues:queryPage"})
	public ApiResult<IPage<DictValuesVo>> queryPage(Query query,DictValuesDTO param) {
		if (StringUtils.isBlank(param.getDictCode())) {
			return ApiResult.fail("归属字典编码不能为空");
		}
		page = dictValuesService.queryDictValuesPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param id
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典值-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  ={ "admin:system:dictValues:getById" })
	public ApiResult<DictValues> getById(@NotEmpty(message = "主键不能为空") String id) {
		return ApiResult.ok(dictValuesService.getById(id));
	}

	/**
	 * 根据主键删除
	 *
	 * @param id
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典值-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  ={ "admin:system:dictValues:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String id) {
		dictValuesService.removeById(id);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param ids
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典值-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:dictValues:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String ids) {
		dictValuesService.removeBatchByIds(Arrays.asList(StringUtils.split(ids, ",")));
		return ApiResult.ok();
	}
}
