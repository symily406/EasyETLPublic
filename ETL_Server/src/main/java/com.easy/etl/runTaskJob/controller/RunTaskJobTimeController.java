package com.easy.etl.runTaskJob.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.runTaskJob.dto.RunTaskJobTimeDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJobTime;
import com.easy.etl.runTaskJob.service.IRunTaskJobTimeService;
import com.easy.etl.runTaskJob.vo.RunTaskJobTimeVo;
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



/**
 *  任务运行时间-控制器
 *  @author 邵勇
 *  @time 2024-01-17
 *
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/runTaskJobTime")
public class RunTaskJobTimeController extends BaseController {

	@Autowired
	private IRunTaskJobTimeService runTaskJobTimeService;





}
