package com.easy.etl.fieldTypeDict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.core.init.Config;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.entity.BatchProjectLayerTaskRead;
import com.easy.etl.batchTask.batchProjectLayerTaskRead.service.IBatchProjectLayerTaskReadService;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.fieldTypeDict.dto.DbFieldTypeDictDTO;
import com.easy.etl.fieldTypeDict.service.IDbFieldTypeDictService;
import com.easy.etl.fieldTypeDict.vo.CreateTableVo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 *  数据库字段类型映射-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/fieldTypeDict")
public class DbFieldTypeDictController extends BaseController {

	@Autowired
	private IDbFieldTypeDictService dbFieldTypeDictService;

	@Autowired
	private IBatchProjectLayerTaskReadService batchProjectLayerTaskReadService;

	@Autowired
	private IDbSourceService dbSourceService;
	/**
	    * 生成建表语句
	 */
	@DebugAnnotation(desc = "生成建表语句")
	@GetMapping(value = "/createTableSql")
	public ApiResult createTableSql(DbFieldTypeDictDTO param) {
		BatchProjectLayerTaskRead read=batchProjectLayerTaskReadService.getOne(new QueryWrapper<BatchProjectLayerTaskRead>().lambda().eq(BatchProjectLayerTaskRead::getTaskId,param.getTaskId()),false);
		if (read==null){
			return ApiResult.fail("任务不存在");
		}
		DbSource dbSource=dbSourceService.getById(param.getDbSourceId());
		if (dbSource==null){
			return ApiResult.fail("数据源不存在");
		}
		List<ColumnInfo> list=dbFieldTypeDictService.queryReadToWriteColumn(param);
		String tableName=Config.tablePrefix+"_"+param.getSourceTableName().toLowerCase()+"_"+Config.tableSuffix;
		StringBuilder fullTableName=new StringBuilder();
		if (StringUtils.isNotBlank(dbSource.getDbCatalog())){
			fullTableName.append(dbSource.getDbCatalog()).append(".");
		}
		if (StringUtils.isNotBlank(dbSource.getDbSchema())){
			fullTableName.append(dbSource.getDbSchema()).append(".");
		}
		fullTableName.append(tableName);
		CreateTableVo createTableVo=new CreateTableVo();
		createTableVo.setTableName(tableName);
		createTableVo.setFullTableName(fullTableName.toString());
		createTableVo.setSql(DbKit.createSql(fullTableName.toString(),list,dbSource.getJdbcDrive()));
		return ApiResult.ok(createTableVo);
	}
}
