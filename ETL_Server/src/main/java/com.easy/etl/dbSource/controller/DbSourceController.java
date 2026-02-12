package com.easy.etl.dbSource.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.common.db.em.DbEnum;
import com.easy.etl.common.db.kit.*;
import com.easy.etl.common.db.model.CatalogInfo;
import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.common.db.model.SchemaInfo;
import com.easy.etl.common.db.model.TableInfo;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.datax.model.Connection;
import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.service.IDbSourceService;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.easy.run.cache.CacheManagement;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


/**
 * 数据源-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/dbSource")
public class DbSourceController extends BaseController {

    @Autowired
    private IDbSourceService dbSourceService;


    /**
     * 获取数据源中的表
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-获取数据源中的表")
    @GetMapping(value = "/queryDbSourcesColumns")
    public ApiResult<List<ColumnInfo>> queryDbSourcesColumns(@NotNull(message = "数据源不能为空") String dbSourceId, String tableName) {
        DbSource dbSource = dbSourceService.getById(dbSourceId);
        if (dbSource == null) {
            return ApiResult.fail("数据源错误");
        }
        if (StringUtils.isEmpty(tableName)) {
            return ApiResult.fail("请选择表");
        }
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        info.setDbScheam(dbSource.getDbSchema());
        info.setDbCatalog(dbSource.getDbCatalog());
        try {
            List<ColumnInfo> columns = DbFactory.getTableColumns(info, dbSource.getDbCatalog(), dbSource.getDbSchema(), tableName);
            if (columns != null && !columns.isEmpty()) {
                switch (DbEnum.getByDriverClassName(dbSource.getJdbcDrive())) {
                    case HIVE:
                    case SPARK:
                        break;
                    default:
                        List<String> primaryKeys = DbFactory.getPrimaryKeys(info, dbSource.getDbCatalog(), dbSource.getDbSchema(), tableName);
                        outerLoop:
                        for (ColumnInfo col : columns) {
                            for (String key : primaryKeys) {
                                if (StringUtils.equals(col.getColumnName(), key)) {
                                    col.setIsPrimaryKey(1);
                                    break outerLoop;
                                }
                            }
                        }
                }
            }
            return ApiResult.ok(columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.fail("数据源连接失败");
    }

    /**
     * 获取数据源中的表
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-获取数据源中的表")
    @GetMapping(value = "/queryDbSourcesTables")
    public ApiResult<List<TableInfo>> queryDbSourcesTables(@NotNull(message = "数据源不能为空") String dbSourceId) {
        DbSource dbSource = dbSourceService.getById(dbSourceId);
        if (dbSource == null) {
            return ApiResult.fail("数据源错误");
        }
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        info.setDbScheam(dbSource.getDbSchema());
        info.setDbCatalog(dbSource.getDbCatalog());
        try {
            List<TableInfo> tableInfos = DbFactory.getDbSourcesTables(info, dbSource.getDbCatalog(), dbSource.getDbSchema());
            return ApiResult.ok(tableInfos);
        } catch (Exception e) {
            DbFactory.removeDataSource(info);
            e.printStackTrace();
        }
        return ApiResult.fail("数据源连接失败");
    }

    /**
     * 获取数据源中catalog
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-获取数据源中Catalog")
    @PostMapping(value = "/queryDbSourcesDbCatalog")
    public ApiResult<List<CatalogInfo>> queryDbSourcesDbCatalog(DbSourceDTO dbSourceDTO) {
        if (dbSourceDTO.getIsAccount() == 1 && StringUtils.isBlank(dbSourceDTO.getUserName())) {
            return ApiResult.fail("数据源连接失败");
        }
        if (dbSourceDTO.getIsPassword() == 1 && StringUtils.isBlank(dbSourceDTO.getPassword())) {
            return ApiResult.fail("数据源连接失败");
        }
        if (StringUtils.isNotBlank(dbSourceDTO.getJdbcUrl())
                && StringUtils.isNotBlank(dbSourceDTO.getJdbcDrive())
        ) {
            try {
                List<CatalogInfo> catalogs = DbFactory.getDbCatalog(dbSourceDTO.getJdbcUrl(), dbSourceDTO.getJdbcDrive(), dbSourceDTO.getUserName(), dbSourceDTO.getPassword());
                return ApiResult.ok(catalogs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ApiResult.fail("数据源连接失败");
    }

    /**
     * 获取数据源中Schema
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-获取数据源中Schema")
    @PostMapping(value = "/queryDbSourcesDbSchema")
    public ApiResult<List<SchemaInfo>> queryDbSourcesDbSchema(DbSourceDTO dbSourceDTO) throws Exception {
        if (dbSourceDTO.getIsAccount() == 1 && StringUtils.isBlank(dbSourceDTO.getUserName())) {
            return ApiResult.fail("数据源连接失败");
        }
        if (dbSourceDTO.getIsPassword() == 1 && StringUtils.isBlank(dbSourceDTO.getPassword())) {
            return ApiResult.fail("数据源连接失败");
        }

        if (StringUtils.isNotBlank(dbSourceDTO.getJdbcUrl())
                && StringUtils.isNotBlank(dbSourceDTO.getJdbcDrive())
        ) {
            try {
                List<SchemaInfo> schemas = DbFactory.getDbSchemas(dbSourceDTO.getJdbcUrl(), dbSourceDTO.getJdbcDrive(), dbSourceDTO.getUserName(), dbSourceDTO.getPassword(), dbSourceDTO.getDbCatalog());
                return ApiResult.ok(schemas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ApiResult.fail("数据源连接失败");
    }


    /**
     * 数据源-测试据源
     *
     * @param dbSource
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-测试据源")
    @PostMapping(value = "/testConnect")
    @SaCheckPermission(value = {"admin:etl:dbSource:test:connect"})
    public ApiResult testConnect(@Validated DbSource dbSource) {
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        info.setDbScheam(dbSource.getDbSchema());
        info.setDbCatalog(dbSource.getDbCatalog());
        DbConnectInfo connInfo = DbFactory.isConnection(info);
        DbFactory.destroyDataSource(info);
        return ApiResult.ok(connInfo);
    }

    /**
     * 数据源-验证数据源连接
     *
     * @param dbSourceId
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-验证数据源连接")
    @GetMapping(value = "/validateConnect")
    public ApiResult validateConnect(@NotNull(message = "数据源ID不能为空") String dbSourceId) {
        DbSource dbSource = dbSourceService.getById(dbSourceId);
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        info.setDbScheam(dbSource.getDbSchema());
        info.setDbCatalog(dbSource.getDbCatalog());
        DbConnectInfo connInfo = DbFactory.isConnection(info);
        if (!connInfo.isConnect()) {
            DbFactory.destroyDataSource(info);
            return ApiResult.fail("数据源:" + dbSource.getDbSourceName() + "连接失败!");
        }
        return ApiResult.ok(connInfo);
    }

    /**
     * 数据源-重连
     *
     * @param dbSourceId
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-重连")
    @GetMapping(value = "/reconnect")
    public ApiResult reconnect(@NotNull(message = "数据源ID不能为空") String dbSourceId) {
        DbSource dbSource = dbSourceService.getById(dbSourceId);
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        try {
            DataSource ds = DbFactory.getDataSource(info);
            DbConnectInfo connInfo = DbFactory.isConnection(ds);
            info.setDbScheam(dbSource.getDbSchema());
            info.setDbCatalog(dbSource.getDbCatalog());
            if (!connInfo.isConnect()) {
                DbFactory.destroyDataSource(info);
                return ApiResult.fail("数据源:" + dbSource.getDbSourceName() + "连接失败!");
            }
            return ApiResult.fail("数据源连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.fail("数据源连接失败");
    }

    /**
     * 表路径-Hive有效
     *
     * @param dbSourceId
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "表路径-Hive有效")
    @GetMapping(value = "/hiveTablePath")
    public ApiResult tablePath(@NotNull(message = "数据源ID不能为空") String dbSourceId, @NotNull(message = "表名不能为空") String tableName) {
        DbSource dbSource = dbSourceService.getById(dbSourceId);
        if (ObjectUtils.isEmpty(dbSource)) {
            return ApiResult.fail("数据源不存在");
        }
        String tablePath = "";
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        info.setDbScheam(dbSource.getDbSchema());
        info.setDbDialect(dbSource.getDbDialect());
        switch (DbEnum.getByDriverClassName(dbSource.getJdbcDrive())) {
            case HIVE:
            case SPARK:
                tablePath = HiveKit.hiveTablePath(info, tableName);
                break;
            default:
        }
        return ApiResult.ok(tablePath);
    }

    /**
     * 线程池测式
     */
//	private void  execute(){
//		ExecuteResultHandler handler = new ExecuteResultHandler() {
//			@Override
//			public void onProcessComplete(int exitValue) {
//				if (exitValue == 0) {
//					System.out.println("Command executed successfully.");
//				} else {
//					System.out.println("Command execution failed with exit value: " + exitValue);
//				}
//			}
//
//			@Override
//			public void onProcessFailed(ExecuteException e) {
//				System.out.println("执行失败"+e);
//			}
//		};
//		final CommandLine cmd=builderCommandLine();
//		executor = DefaultExecutor.builder().get();
//		future = taskExecutor.getThreadPoolExecutor().submit(new Callable<Integer>() {
//			@Override
//			public Integer call() {
//				try {
//					executor.execute(cmd,handler);
//				}catch (Exception e){
//					System.out.println("====66666666611111111执行失败"+e);
//				}
//				return 1;
//			}
//		});
//	}

//	private CommandLine builderCommandLine(){
//		CommandLine commandLine = CommandLine.parse("java");
//		commandLine.addArgument("-server");
//		commandLine.addArgument("-Xms2g");
//		commandLine.addArgument("-Xmx2g");
//		commandLine.addArgument("-Dfile.encoding=UTF-8");
//		commandLine.addArgument("-Dlogback.statusListenerClass=ch.qos.logback.core.status.NopStatusListener");
//		commandLine.addArgument("-Djava.security.egd=file:///dev/urandom");
//		commandLine.addArgument("-Ddatax.home=/export/servers/datax/datax");
//		commandLine.addArgument("-Dlogback.configurationFile=/export/servers/datax/datax/conf/logback.xml");
//		commandLine.addArgument("-classpath");
//		commandLine.addArgument("/export/servers/datax/datax/lib/*:.");
//		commandLine.addArgument("com.alibaba.datax.core.Engine");
//		commandLine.addArgument("-jobid");
//		commandLine.addArgument("123456");
//		commandLine.addArgument("-job");
//		commandLine.addArgument("/export/servers/datax/datax/datax_sync.txt");
//		return commandLine;
//	}

    /**
     * 保存或修改
     *
     * @param dbSource
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-保存或修改")
    @PostMapping(value = "/saveOrUpdate")
    @SaCheckPermission(value = {"admin:etl:sourceType:dbSource:add", "admin:etl:sourceType:dbSource:edit"})
    public ApiResult saveOrUpdate(@Validated DbSource dbSource) {
        DbInfo info = new DbInfo();
        BeanUtil.copyProperties(dbSource, info, true);
        DbConnectInfo connInfo = DbFactory.isConnection(info);
        if (!connInfo.isConnect()) {
            return ApiResult.fail(connInfo.getMessage());
        }
        dbSource.setIsConn(1);
        if (dbSourceService.saveOrUpdateDbSource(dbSource)) {
            return ApiResult.ok(dbSource);
        }
        return ApiResult.fail("更新失败");
    }

    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-分页查询")
    @GetMapping(value = "/queryPage")
    @SaCheckPermission(value = {"admin:etl:sourceType:dbSource:queryPage"})
    public ApiResult<IPage<DbSourceVo>> queryPage(Query query, DbSourceDTO param) {
        page = dbSourceService.queryDbSourcePage(Condition.getPage(query), param);
        return ApiResult.ok(page);
    }

    /**
     * 根据主键返回
     *
     * @param dbSourceId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-根据主键返回")
    @GetMapping(value = "/getById")
    @SaCheckPermission(value = {"admin:etl:sourceType:dbSource:getById"})
    public ApiResult<DbSource> getById(@NotNull(message = "主键不能为空") String dbSourceId) {
        return ApiResult.ok(dbSourceService.getById(dbSourceId));
    }

    /**
     * 根据主键删除
     *
     * @param dbSourceId
     * @return
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "数据源-根据主键删除")
    @GetMapping(value = "/deleteById")
    @SaCheckPermission(value = {"admin:etl:sourceType:dbSource:deleteById"})
    public ApiResult deleteById(@NotNull(message = "主键不能为空") String dbSourceId) {
        dbSourceService.removeById(dbSourceId);
        return ApiResult.ok();
    }

}
