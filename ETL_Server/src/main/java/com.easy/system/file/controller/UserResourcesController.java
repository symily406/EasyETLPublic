package com.easy.system.file.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.StrKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.IdGenerator.IdBulider;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import com.easy.system.file.dto.UserResourcesDTO;
import com.easy.system.file.entity.FileInfo;
import com.easy.system.file.entity.UserResources;
import com.easy.system.file.service.IFileInfoService;
import com.easy.system.file.service.IUserResourcesService;
import com.easy.system.file.vo.UserResourcesVo;
import com.easy.etl.core.controller.BaseController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;

/**
 * 用户资源-控制器
 *
 * @author
 * @time
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/system/file/userResources")
public class UserResourcesController extends BaseController {

	@Autowired
	private IUserResourcesService userResourcesService;

	@Autowired
	private IFileInfoService fileInfoService;

	/**
	 * 保存或修改
	 *
	 * @param userResources
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户资源-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	@SaCheckPermission(value  ={ "admin:system:file:userResources:edit","admin:system:file:userResources:createFoler"  },mode = SaMode.OR)
	public ApiResult saveOrUpdate(@Validated UserResources userResources) {
		String id = IdBulider.id();
		if (StringUtils.isNoneBlank(userResources.getId())) {
			id = userResources.getId();
		}
		userResources.setId(id);
		String fullPath = userResources.getParentId().concat("|").concat(userResources.getFullPath()).concat("|")
				.concat(id);
		userResources.setFullPath(StrKit.clearDuplicateValue(fullPath, "|"));
		if (userResourcesService.saveOrUpdateUserResources(userResources)) {
			return ApiResult.ok(userResources);
		}
		return ApiResult.fail("更新失败");
	}

	/**
	 * 用户资源-文件重命名
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户资源-文件重命名")
	@PostMapping(value = "/fileRename")
	@SaCheckPermission(value  = {"admin:system:file:userResources:edit" })
	public ApiResult fileRename(@NotNull(message = "文件名不能为空") String mainName
			, @NotNull(message = "文件id不能为空") String id
			,@NotNull(message = "文件fileId不能为空") String fileId) {
		FileInfo fileInfo=fileInfoService.getById(fileId);
		if (fileInfo==null) {
			return ApiResult.fail("更新失败");
		}

		UserResources userResources = new UserResources();
		userResources.setId(id);
		userResources.setName(mainName.concat(".").concat(fileInfo.getSuffix()));
		userResourcesService.updateById(userResources);

		UserResourcesDTO dto=new UserResourcesDTO();
		dto.setId(id);
		UserResourcesVo re = userResourcesService.queryUserResourcesByCondition(dto);
		if (re != null) {
			return ApiResult.ok(re);
		}

		return ApiResult.fail("更新失败");
	}

	/**
	 * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户资源-分页查询")
	@GetMapping(value = "/queryPage")
	@SaCheckPermission(value  = { "admin:system:file:userResources:queryPage" })
	public ApiResult<Map<String, Object>> queryPage(Query query, UserResourcesDTO param,String fileTypes) {
		Map<String, Object> re = new HashMap<String, Object>();
		UserResources folder = userResourcesService.getById(param.getParentId());
		if (folder != null) {
			List<UserResources> list = userResourcesService.list(new QueryWrapper<UserResources>().lambda()
					.in(UserResources::getId, StringUtils.split(folder.getFullPath(), "|"))
					.select(UserResources::getId, UserResources::getName, UserResources::getFullPath)
					.orderByAsc(UserResources::getLeval));
			re.put("folderPath", list);

		}
		if (StrKit.notBlank(fileTypes)) {
			param.setQueryFileTypes(Arrays.asList(StringUtils.split(fileTypes,",")));
		}
		page = userResourcesService.queryUserResourcesPage(Condition.getPage(query), param);
		re.put("data", page);
		return ApiResult.ok(re);
	}


	/**
	 * 根据主键删除
	 *
	 * @param id
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "用户资源-根据主键删除")
	@GetMapping(value = "/deleteById")
	@SaCheckPermission(value  ={ "admin:system:file:userResources:deleteById" })
	public ApiResult deleteById(@NotNull(message = "主键不能为空") String id) {
		userResourcesService.deleteResourcesById(id);
		return ApiResult.ok();
	}

}
