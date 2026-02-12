package com.easy.etl.batchTask.batchProjectLayerTaskChannel.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.entity.BatchProjectLayerTaskChannel;
import com.easy.etl.batchTask.batchProjectLayerTaskChannel.service.IBatchProjectLayerTaskChannelService;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskService;
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
 * 通道控制-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/batchTask/batchProjectLayerTaskChannel")
public class BatchProjectLayerTaskChannelController extends BaseController {

    @Autowired
    private IBatchProjectLayerTaskChannelService batchProjectLayerTaskChannelService;
    @Autowired
    private IProjectTaskService projectTaskService;

    /**
     * 保存或修改
     *
     * @param batchProjectLayerTaskChannel
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "通道控制-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated BatchProjectLayerTaskChannel batchProjectLayerTaskChannel, Integer taskStep) {
        ProjectTask projectTask = projectTaskService.getById(batchProjectLayerTaskChannel.getTaskId());
        if (projectTask == null) {
            return ApiResult.fail("任务不存在");
        }
        batchProjectLayerTaskChannel.setChannelId(projectTask.getTaskId());
        if (batchProjectLayerTaskChannelService.saveOrUpdateBatchProjectLayerTaskChannel(batchProjectLayerTaskChannel, taskStep)) {
            return ApiResult.ok(batchProjectLayerTaskChannel);
        }
        return ApiResult.fail("更新失败");
    }


    /**
     * 根据主键返回
     *
     * @param channelId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "通道控制-根据主键返回")
    @GetMapping(value = "/getById")
    public ApiResult<BatchProjectLayerTaskChannel> getById(@NotNull(message = "主键不能为空") String taskId) {
        return ApiResult.ok(batchProjectLayerTaskChannelService.getOne(
                        new LambdaUpdateWrapper<BatchProjectLayerTaskChannel>().eq(BatchProjectLayerTaskChannel::getTaskId,taskId)
                )
        );
    }


}
