package com.easy.etl.batchTask.batchProjectLayerTaskWrite.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.etl.common.db.kit.DbFactory;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.service.IBatchProjectLayerTaskWriteService;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.common.db.kit.DbInfo;
import com.spring.boot.ext.api.ApiResult;
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
 *  批处理分层任务写入-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/batchTask/batchProjectLayerTaskWrite")
public class BatchProjectLayerTaskWriteController extends BaseController {

	@Autowired
	private IBatchProjectLayerTaskWriteService batchProjectLayerTaskWriteService;
	@Autowired
	private IDbSourceService dbSourceService;
	@Autowired
	private IProjectTaskService projectTaskService;

	/**
	 * 保存或修改
	 * @param batchProjectLayerTaskWrite
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层任务写入-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	public ApiResult saveOrUpdate(@Validated BatchProjectLayerTaskWrite batchProjectLayerTaskWrite,int taskStep) throws Exception {
		ProjectTask projectTask=projectTaskService.getById(batchProjectLayerTaskWrite.getTaskId());
		if (projectTask==null){
			return ApiResult.fail("任务不存在");
		}
		DbSource dbSource=dbSourceService.getById(batchProjectLayerTaskWrite.getDbSourceId());
		if(dbSource==null){
			return  ApiResult.fail("数据源错误");
		}
		DbInfo info=new DbInfo();
		BeanUtil.copyProperties(dbSource,info,true);
		List<ColumnInfo> columns= DbFactory.getTableColumns(info,dbSource.getDbCatalog(),dbSource.getDbSchema(),batchProjectLayerTaskWrite.getTableName());
		if(batchProjectLayerTaskWriteService.saveOrUpdateBatchProjectLayerTaskWrite(batchProjectLayerTaskWrite,columns,taskStep)) {
			return ApiResult.ok(batchProjectLayerTaskWrite);
		}
		return ApiResult.fail("更新失败");
	}


	/**
	 * 根据主键返回
	 *
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层任务写入-根据主键返回")
	@GetMapping(value = "/getByTaskId")
	public ApiResult<BatchProjectLayerTaskWrite> getByTaskId(@NotNull(message = "任务ID不能为空") String taskId) {
		BatchProjectLayerTaskWrite taskWrite=batchProjectLayerTaskWriteService.getOne(new QueryWrapper<BatchProjectLayerTaskWrite>().lambda().eq(BatchProjectLayerTaskWrite::getTaskId,taskId));
		return ApiResult.ok(taskWrite);
	}

}
