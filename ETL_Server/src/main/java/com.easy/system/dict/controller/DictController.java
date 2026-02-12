package com.easy.system.dict.controller;


import java.util.Arrays;

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
import com.easy.system.dict.dto.DictDTO;
import com.easy.system.dict.entity.Dict;
import com.easy.system.dict.service.IDictService;
import com.easy.system.dict.vo.DictVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  字典主表-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/dict/dict")
public class DictController extends BaseController {

	@Autowired
	private IDictService dictService;



	/**
	 * 保存或修改
	 * @param dict
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典主表-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  =  {"admin:system:dict:add","admin:system:dict:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated Dict dict) {

		if (StringUtils.isBlank(dict.getDictId())) {
			DictVo dictVo=dictService.queryDictByCode(dict.getDictCode());
			if (dictVo!=null) {
				return ApiResult.fail("相同编码的字典已存在");
			}
		}
		if(dictService.saveOrUpdateDict(dict)) {
			return ApiResult.ok(dict);
		}

		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典主表-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  = {"admin:system:dict:queryPage"})
	public ApiResult<IPage<DictVo>> queryPage(Query query,DictDTO param) {
		page = dictService.queryDictPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param dictId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典主表-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:dict:getById" })
	public ApiResult<Dict> getById(@NotEmpty(message = "主键不能为空") String dictId) {
		return ApiResult.ok(dictService.getById(dictId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param dictId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典主表-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:dict:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String dictId) {
		dictService.removeById(dictId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param dictIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "字典主表-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:dict:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String dictIds) {
		dictService.removeBatchByIds(Arrays.asList(StringUtils.split(dictIds, ",")));
		return ApiResult.ok();
	}
}
