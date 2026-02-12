package com.easy.etl.batchTask.batchProjectLayerTaskWriteField.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity.BatchProjectLayerTaskWriteField;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.service.IBatchProjectLayerTaskWriteFieldService;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.vo.BatchProjectLayerTaskWriteFieldVo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 *  批处理分层任务写入字段-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/batchTask/batchProjectLayerTaskWriteField")
public class BatchProjectLayerTaskWriteFieldController extends BaseController {

	@Autowired
	private IBatchProjectLayerTaskWriteFieldService batchProjectLayerTaskWriteFieldService;



	/**
	 * 根据主键返回
	 *
	 * @return
	 */
	@SaCheckLogin
	@DebugAnnotation(desc = "批处理分层任务写入字段-根据主键返回")
	@GetMapping(value = "/getByTaskId")
	public ApiResult<List<BatchProjectLayerTaskWriteFieldVo>> getById(@NotNull(message = "主键不能为空") String taskId) {
		List<BatchProjectLayerTaskWriteField> list = batchProjectLayerTaskWriteFieldService.list(new QueryWrapper<BatchProjectLayerTaskWriteField>().lambda()
				.eq(BatchProjectLayerTaskWriteField::getTaskId, taskId)
				.orderByAsc(BatchProjectLayerTaskWriteField::getShowOrder));
		List<BatchProjectLayerTaskWriteFieldVo> finalList=list.stream().map(v->{
			BatchProjectLayerTaskWriteFieldVo vo=new BatchProjectLayerTaskWriteFieldVo();
			BeanUtil.copyProperties(v,vo);
			return 	vo;
		}).collect(Collectors.toList());
		return ApiResult.ok(finalList);
	}
}
