package com.easy.system.file.controller;


import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.system.file.dto.FileInfoDTO;
import com.easy.system.file.entity.FileInfo;
import com.easy.system.file.service.IFileInfoService;
import com.easy.system.file.vo.FileInfoVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;


/**
 *  附件-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/file/fileInfo")
public class FileInfoController extends BaseController {

	@Autowired
	private IFileInfoService fileInfoService;



	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "附件-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:file:fileInfo:queryPage"})
	public ApiResult<IPage<FileInfoVo>> queryPage(Query query,FileInfoDTO param) {
		page = fileInfoService.queryFileInfoPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param fileId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "附件-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  ={ "admin:system:file:fileInfo:getById" })
	public ApiResult<FileInfo> getById(@NotNull(message = "主键不能为空") String fileId) {
		return ApiResult.ok(fileInfoService.getById(fileId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param fileId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "附件-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:file:fileInfo:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String fileId) {
		fileInfoService.removeById(fileId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param fileIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "附件-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:file:fileInfo:batchDeleteById" })
	public ApiResult batchDeleteById(@NotNull(message = "批量删除主键不能为空") String fileIds) {
		fileInfoService.removeBatchByIds(Arrays.asList(StringUtils.split(fileIds, ",")));
		return ApiResult.ok();
	}
}
