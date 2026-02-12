package com.easy.etl.actuator.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.Db;
import com.easy.etl.common.db.kit.DbFactory;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.common.db.model.SqlTable;
import com.easy.etl.common.db.model.TableInfo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.common.db.kit.DbInfo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping(value = "/admin/etl/sqlActuator")
public class SqlActuatorController extends BaseController {
    @Autowired
    private IDbSourceService dbSourceService;
    /**
     * 执行sql
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "执行sql")
    @PostMapping(value = "/createTable")
    public ApiResult executeSql(@NotNull(message = "数据源不能为空")  String dbSourceId, @NotNull(message = "要执行的sql不能为空") String sql) {
        boolean succed=true;
        String errorMessage="";
        DbSource dbSource=dbSourceService.getById(dbSourceId);
        if(dbSource==null){
            return  ApiResult.fail("数据源错误");
        }
        DbInfo info=new DbInfo();
        BeanUtil.copyProperties(dbSource,info,true);
        TableInfo tableInfo=new TableInfo();
        tableInfo.setCatalog(dbSource.getDbCatalog());
        tableInfo.setSchema(dbSource.getDbSchema());
        try {
            if (StringUtils.isEmpty(sql)){
                errorMessage="sql不能为空";
                succed=false;
            }
            if (succed){
                String tableName= DbKit.parseCreateSqlTablName(sql);
                Db.use(DbFactory.getDataSource(info),dbSource.getJdbcDrive()).execute(sql);
                tableInfo.setName(tableName);
            }
        }catch (Exception e){
            succed=false;
            errorMessage=e.getMessage();
        }
        if (succed){
            return ApiResult.ok(tableInfo);
        }
        return ApiResult.fail(errorMessage);
    }
    @SaCheckLogin
    @DebugAnnotation(desc = "sql格式化")
    @PostMapping(value = "/sqlFormat")
    public ApiResult sqlFormat(@NotNull(message = "要格式化的sql不能为空") String sql, @NotNull(message = "数据源不能为空")  String dbSourceId) {
        if(StringUtils.isEmpty(dbSourceId)){
            return  ApiResult.fail("请选择数源");
        }
        DbSource dbSource=dbSourceService.getById(dbSourceId);
        if(dbSource==null){
            return  ApiResult.fail("数据源错误");
        }
        DbInfo info=new DbInfo();
        BeanUtil.copyProperties(dbSource,info,true);

        SqlTable sqlTable=DbKit.parseSqlTables(sql,info.getJdbcDrive());
        if(sqlTable.isSucced()==false){
            return ApiResult.fail("sql语法错误,"+sqlTable.getMessage());
        }
        String retSql=DbKit.format(sql,info.getJdbcDrive());
        return ApiResult.ok(retSql);
    }
}
