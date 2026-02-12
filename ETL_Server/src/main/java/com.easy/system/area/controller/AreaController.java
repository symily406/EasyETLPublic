package com.easy.system.area.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.easy.system.area.dto.AreaDTO;
import com.easy.system.area.service.IAreaService;
import com.easy.system.area.vo.AreaVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;



/**
 *  地区表-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/area")
public class AreaController extends BaseController {

	@Autowired
	private IAreaService areaService;

	/**
	    * 返回地区
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "地区表-返回地区")
	@GetMapping(value = "/query")
	public ApiResult<List<AreaVo>> query(AreaDTO param) {
		List<AreaVo>  list= areaService.queryAreaAll(param);
		return ApiResult.ok(list);
	}
}
