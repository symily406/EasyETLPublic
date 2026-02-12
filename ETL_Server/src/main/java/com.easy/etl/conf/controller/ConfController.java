package com.easy.etl.conf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.conf.dto.ConfDTO;
import com.easy.etl.conf.entity.*;
import com.easy.etl.conf.service.IConfService;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.run.cache.CacheManagement;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
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
 * 系统配置主表-控制器
 *
 * @author 邵勇
 * @time 2025-10-08
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/conf")
public class ConfController extends BaseController {

    @Autowired
    private IConfService confService;

    @SaCheckLogin
    @DebugAnnotation(desc = "系统配置主表-设为默认")
    @GetMapping(value = "/setDefault")
    @SaCheckPermission(value = {"admin:etl:conf:setDefault"})
    public ApiResult setDefault(@NotNull(message = "主键不能为空") String confId) {
        return ApiResult.ok(confService.setDefault(confId));
    }

    /**
     * 保存或修改
     *
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "系统配置主表-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    @SaCheckPermission(value = {"admin:etl:conf:add", "admin:etl:conf:edit"})
    public ApiResult saveOrUpdate(ConfDTO dto) {
        Conf conf = JsonKit.parseObject(dto.getConf(), Conf.class);
        ConfBase confBase = JsonKit.parseObject(dto.getConfBase(), ConfBase.class);
        ConfYarn confYarn = JsonKit.parseObject(dto.getConfYarn(), ConfYarn.class);
        ConfDatax confDatax = JsonKit.parseObject(dto.getConfDatax(), ConfDatax.class);
        ConfSql confSql = JsonKit.parseObject(dto.getConfSql(), ConfSql.class);
        if (confService.saveOrUpdateConf(conf, confBase, confYarn, confDatax, confSql)) {
            CacheManagement.clearConf();
            return ApiResult.ok(conf);
        }
        return ApiResult.fail("更新失败");
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "系统配置主表-分页查询")
    @GetMapping(value = "/queryPage")
    @SaCheckPermission(value = {"admin:etl:conf:queryPage"})
    public ApiResult<IPage<ConfVo>> queryPage(Query query, ConfDTO param) {
        page = confService.queryConfPage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 根据主键返回
     *
     * @param confId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "系统配置主表-根据主键返回")
    @GetMapping(value = "/getById")
    @SaCheckPermission(value = {"admin:etl:conf:getById"})
    public ApiResult<ConfVo> getById(@NotNull(message = "主键不能为空") String confId) {
        return ApiResult.ok(confService.queryConfById(confId));
    }

    /**
     * 根据主键删除
     *
     * @param confId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "系统配置主表-根据主键删除")
    @GetMapping(value = "/deleteById")
    @SaCheckPermission(value = {"admin:etl:conf:deleteById"})
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String confId) {
        confService.removeById(confId);
        return ApiResult.ok();
    }

}
