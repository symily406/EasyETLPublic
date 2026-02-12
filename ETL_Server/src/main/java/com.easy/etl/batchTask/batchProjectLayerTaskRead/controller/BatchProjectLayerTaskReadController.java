package com.easy.etl.batchTask.batchProjectLayerTaskRead.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.etl.common.db.kit.DbFactory;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.core.init.Config;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.service.IBatchProjectLayerTaskReadService;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.common.db.kit.DbInfo;
import com.easy.etl.machine.model.Sys;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *  批处理分层任务来源-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/batchTask/batchProjectLayerTaskRead")
public class BatchProjectLayerTaskReadController extends BaseController {

	@Autowired
	private IBatchProjectLayerTaskReadService batchProjectLayerTaskReadService;
	@Autowired
	private IProjectTaskService projectTaskService;

	@Autowired
	private IDbSourceService dbSourceService;



	/**
	 * 保存或修改
	 * @param batchProjectLayerTaskRead
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层任务来源-保存或修改")
	@PostMapping(value = "/saveOrUpdate")
	public ApiResult saveOrUpdate(@Validated BatchProjectLayerTaskRead batchProjectLayerTaskRead,int taskStep) throws Exception {
		ProjectTask projectTask=projectTaskService.getById(batchProjectLayerTaskRead.getTaskId());
		if (projectTask==null){
			return ApiResult.fail("任务不存在");
		}
		DbSource dbSource=dbSourceService.getById(batchProjectLayerTaskRead.getDbSourceId());
		if(dbSource==null){
			return  ApiResult.fail("数据源错误");
		}
		DbInfo info=new DbInfo();
		BeanUtil.copyProperties(dbSource,info,true);
		List<ColumnInfo> columns= DbFactory.getTableColumns(info,dbSource.getDbCatalog(),dbSource.getDbSchema(),batchProjectLayerTaskRead.getTableName());
		if (columns!=null&&!columns.isEmpty()){


			List<String> primaryKeys=DbFactory.getPrimaryKeys(info,dbSource.getDbCatalog(),dbSource.getDbSchema(),batchProjectLayerTaskRead.getTableName());
			outerLoop:for (ColumnInfo col:columns) {
				for(String key:primaryKeys){
					if (StringUtils.equals(col.getColumnName(),key)){
						col.setIsPrimaryKey(1);
						break outerLoop;
					}
				}
			}

			ColumnInfo odsColumn=columns.stream().filter(v->StringUtils.equalsIgnoreCase(v.getColumnName(),Config.fieldName)).findFirst().orElse(null);
			if (odsColumn==null){
				odsColumn=new ColumnInfo();
				odsColumn.setColumnName(Config.fieldName);
				odsColumn.setFieldName(Config.fieldName);
				odsColumn.setTypeName(Config.fieldType);
				odsColumn.setColumnSize(Config.columnSize);
				odsColumn.setComment(Config.comment);
				odsColumn.setReadFieldFormat(Config.fieldFormat);
				odsColumn.setShowOrder(999);
				columns.add(odsColumn);
			}
		}
		if(batchProjectLayerTaskReadService.saveOrUpdateBatchProjectLayerTaskRead(batchProjectLayerTaskRead,columns,taskStep)) {
			return ApiResult.ok(batchProjectLayerTaskRead);
		}
		return ApiResult.fail("更新失败");
	}


	/**
	 * 根据主键返回
	 *
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层任务来源-根据主键返回")
	@GetMapping(value = "/getByTaskId")
	public ApiResult<BatchProjectLayerTaskRead> getByTaskId(@NotNull(message = "任务ID不能为空") String taskId) {
		BatchProjectLayerTaskRead taskRead=batchProjectLayerTaskReadService.getOne(new QueryWrapper<BatchProjectLayerTaskRead>().lambda().eq(BatchProjectLayerTaskRead::getTaskId,taskId));
		return ApiResult.ok(taskRead);
	}



}
