package com.easy.etl.batchTask.batchProjectLayerTaskReadField.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.service.IBatchProjectLayerTaskReadFieldService;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import com.spring.boot.ext.api.ApiResult;
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
import java.util.jar.Pack200;


/**
 * 批处理分层任务来源字段-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/batchTask/batchProjectLayerTaskReadField")
public class BatchProjectLayerTaskReadFieldController extends BaseController {

    @Autowired
    private IBatchProjectLayerTaskReadFieldService batchProjectLayerTaskReadFieldService;

    /**
     * 根据主键删除
     *
     * @param readFieldId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务来源字段-根据主键删除")
    @GetMapping(value = "/deleteById")
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String readFieldId) {
        batchProjectLayerTaskReadFieldService.deleteById(readFieldId);
        return ApiResult.ok();
    }

    /**
     * 保存或修改
     *
     * @param
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务来源字段-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    public ApiResult saveOrUpdate(@Validated BatchProjectLayerTaskReadField batchProjectLayerTaskReadField) {
        if (StrUtil.indexOfIgnoreCase(batchProjectLayerTaskReadField.getFieldFormat(),"as")>-1){
            return ApiResult.fail("格式化内容错误,不能包含\"as\"字符串");
        }
        BatchProjectLayerTaskReadFieldDTO param =null;
        if (StringUtils.isEmpty(batchProjectLayerTaskReadField.getReadFieldId())&&batchProjectLayerTaskReadField.getIsConstant()==1){
            param = new BatchProjectLayerTaskReadFieldDTO();
            param.setReadId(batchProjectLayerTaskReadField.getReadId());
            param.setFieldName(batchProjectLayerTaskReadField.getFieldName().toLowerCase());
            BatchProjectLayerTaskReadFieldVo vo=batchProjectLayerTaskReadFieldService.queryBatchProjectLayerTaskReadFieldByCondition(param);
            if (vo!=null){
                return ApiResult.fail("相同名称的常量已存在");
            }
        }

        if (batchProjectLayerTaskReadFieldService.saveOrUpdateBatchProjectLayerTaskReadField(batchProjectLayerTaskReadField)) {
            param = new BatchProjectLayerTaskReadFieldDTO();
            param.setReadFieldId(batchProjectLayerTaskReadField.getReadFieldId());
            return ApiResult.ok(batchProjectLayerTaskReadFieldService.queryBatchProjectLayerTaskReadFieldByCondition(param));
        }
        return ApiResult.fail("更新失败");
    }

    /**
     * 根据主键返回
     *
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "批处理分层任务来源字段-根据主键返回")
    @GetMapping(value = "/getByTaskId")
    public ApiResult<List<BatchProjectLayerTaskReadFieldVo>> getByTaskId(@NotNull(message = "主键不能为空") String taskId) {
        BatchProjectLayerTaskReadFieldDTO param = new BatchProjectLayerTaskReadFieldDTO();
        param.setTaskId(taskId);
        return ApiResult.ok(batchProjectLayerTaskReadFieldService.queryBatchProjectLayerTaskReadFieldAll(param));
    }

}
