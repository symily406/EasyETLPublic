package com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.service.IBatchProjectLayerTaskFieldMapService;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *  批处理分层字段映射-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/batchTask/batchProjectLayerTaskFieldMap")
public class BatchProjectLayerTaskFieldMapController extends BaseController {

	@Autowired
	private IBatchProjectLayerTaskFieldMapService batchProjectLayerTaskFieldMapService;
	@Autowired
	private IProjectTaskService projectTaskService;

	/**
	 * 保存或修改
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层字段映射-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	public ApiResult saveOrUpdate(@NotNull(message = "任务ID不能为空") String taskId,@NotNull(message = "任务ID不能为空") String taskFields,int taskStep) {
		ProjectTask projectTask=projectTaskService.getById(taskId);
		if (projectTask==null){
			return ApiResult.fail("任务不存在");
		}
		List<BatchProjectLayerTaskFieldMap> list= JsonKit.parseList(taskFields,BatchProjectLayerTaskFieldMap.class);
		if (list==null&&!list.isEmpty()){
			return ApiResult.fail("请配置同步字段映射");
		}
		if(batchProjectLayerTaskFieldMapService.saveOrUpdateBatchProjectLayerTaskFieldMap(taskId,list,taskStep)) {
			return ApiResult.ok();
		}
		return ApiResult.fail("更新失败");
	}

	/**
	    * 分页查询
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层字段映射-分页查询")
	@GetMapping(value = "/queryAll")
	public ApiResult<List<BatchProjectLayerTaskFieldMapVo>> queryAll(BatchProjectLayerTaskFieldMapDTO param) {
		List<BatchProjectLayerTaskFieldMapVo> list = batchProjectLayerTaskFieldMapService.queryBatchProjectLayerTaskFieldMapAll(param);
		return ApiResult.ok(list);
	}

}
