package com.easy.system.file.controller;

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
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.run.cache.CacheManagement;
import com.easy.etl.core.init.Config;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.entity.FileConfig;
import com.easy.system.file.service.IFileConfigService;
import com.easy.system.file.vo.FileConfigVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;

/**
 * 文件上传配置-控制器
 *
 * @author
 * @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/file/fileConfig")
public class FileConfigController extends BaseController {

	@Autowired
	private IFileConfigService fileConfigService;

	/**
	 * 查询单位文件配置
	 * @param fileConfig
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = " 查询单位文件配置")
	@GetMapping(value = "/queryByCompanyId")
	@SaCheckPermission(value  ={ "admin:system:file:chunk:upload"})
	public ApiResult queryByCompanyId(FileConfigDTO param,String fileTypes) {
		if (StrKit.notBlank(fileTypes)) {
			param.setQueryFileTypes(Arrays.asList(StringUtils.split(fileTypes,",")));
		}
		List<FileConfigVo> list=fileConfigService.queryFileConfigAll(param);
		return ApiResult.ok(list);
	}


	/**
	 * 保存或修改
	 *
	 * @param fileConfig
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件上传配置-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = { "admin:system:fileConfig:add", "admin:system:fileConfig:edit" },mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated FileConfig fileConfig) {
		if (fileConfigService.saveOrUpdateFileConfig(fileConfig)) {
			CacheManagement.clearStaffFileConfig();
			return ApiResult.ok(fileConfig);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件上传配置-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={ "admin:system:fileConfig:queryPage" })
	public ApiResult<IPage<FileConfigVo>> queryPage(Query query, FileConfigDTO param) {
		page = fileConfigService.queryFileConfigPage(Condition.getPage(query), param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param configId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件上传配置-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  = { "admin:system:fileConfig:getById" })
	public ApiResult<FileConfig> getById(@NotEmpty(message = "主键不能为空") String configId) {
		return ApiResult.ok(fileConfigService.getById(configId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param configId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件上传配置-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  ={ "admin:system:fileConfig:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String configId) {
		CacheManagement.clearStaffFileConfig();
		fileConfigService.removeById(configId);
		return ApiResult.ok();
	}

	/**
	 * 根据主键批量删除
	 *
	 * @param configIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件上传配置-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:fileConfig:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String configIds) {
		fileConfigService.removeBatchByIds(Arrays.asList(StringUtils.split(configIds, ",")));
		CacheManagement.clearStaffFileConfig();
		return ApiResult.ok();
	}
}
