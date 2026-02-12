package com.easy.system.file.controller;


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
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.dto.FileFolderDTO;
import com.easy.system.file.entity.FileFolder;
import com.easy.system.file.service.IFileFolderService;
import com.easy.system.file.vo.FileFolderVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;



/**
 *  文件夹-控制器
 *  @author
 *  @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/file/fileFolder")
public class FileFolderController extends BaseController {

	@Autowired
	private IFileFolderService fileFolderService;


	/**
	    * 文件夹树
	 * @param fileFolder
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件夹树")
	@GetMapping(value = "/fileFolderTree")
	@SaCheckPermission(value  = {"admin:system:fileFolder:fileFolderTree"})
	public ApiResult fileFolderTree(FileConfigDTO fileConfigDTO, FileFolderDTO fileFolderDTO) {
		return ApiResult.ok(fileFolderService.folderTreeVos(fileConfigDTO, fileFolderDTO));
	}

	/**
	 * 保存或修改
	 * @param fileFolder
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件夹-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  = {"admin:system:fileFolder:add","admin:system:fileFolder:edit"},mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated FileFolder fileFolder) {
		if(fileFolderService.saveOrUpdateFileFolder(fileFolder)) {
			return ApiResult.ok(fileFolder);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件夹-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  ={"admin:system:fileFolder:queryPage"})
	public ApiResult<IPage<FileFolderVo>> queryPage(Query query,FileFolderDTO param) {
		page = fileFolderService.queryFileFolderPage(Condition.getPage(query),param);
		return ApiResult.ok(page);
	}

	/**
	 * 根据主键返回
	 *
	 * @param folderId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件夹-根据主键返回")
	@GetMapping(value = "/getById")
	@SaCheckPermission(value  ={ "admin:system:fileFolder:getById" })
	public ApiResult<FileFolder> getById(@NotEmpty(message = "主键不能为空") String folderId) {
		return ApiResult.ok(fileFolderService.getById(folderId));
	}

	/**
	 * 根据主键删除
	 *
	 * @param folderId
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件夹-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  = { "admin:system:fileFolder:deleteById" })
	public ApiResult deleteById(@NotEmpty(message = "主键不能为空") String folderId) {
		fileFolderService.deleteFolder(folderId);
		return ApiResult.ok();
	}


	/**
	 * 根据主键批量删除
	 * @param folderIds
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "文件夹-根据主键批量删除")
	@GetMapping(value = "/batchDeleteById")
	@SaCheckPermission(value  = { "admin:system:fileFolder:batchDeleteById" })
	public ApiResult batchDeleteById(@NotEmpty(message = "批量删除主键不能为空") String folderIds) {
		fileFolderService.removeBatchByIds(Arrays.asList(StringUtils.split(folderIds, ",")));
		return ApiResult.ok();
	}
}
