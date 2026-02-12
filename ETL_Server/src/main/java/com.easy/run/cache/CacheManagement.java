package com.easy.run.cache;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.db.Entity;
import com.easy.etl.cache.CacheCore;
import com.easy.etl.core.constant.GlobalCache;
import com.easy.etl.core.init.Config;
import com.easy.etl.conf.entity.Conf;
import com.easy.etl.conf.vo.ConfVo;
import com.easy.system.file.dto.FileConfigDTO;
import com.easy.system.file.service.IFileConfigService;
import com.easy.system.file.service.impl.FileConfigServiceImpl;
import com.easy.system.menu.service.IMenuService;
import com.easy.system.menu.vo.StaffMenuVo;
import com.spring.boot.auth.IAuth;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import com.spring.boot.ext.plugin.context.Application;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 缓存管理
 *
 * @author plink002
 */
public class CacheManagement {
    @Resource
    private IMenuService menuService;

    @Resource
    private IAuth auth;
    @Resource
    private IFileConfigService fileConfigService;

    /**
     * 添加验证码缓存
     *
     * @param key
     * @param value
     */
    public static void setCaptchaCache(String key, String value) {
        CacheKit.put(GlobalCache.captchaCacheName, GlobalCache.captchaCachePrefix.concat("_").concat(key), value.toUpperCase());
    }

    /**
     * 获取验证码缓存
     *
     * @param key
     * @return
     */
    public static String getCaptcha(String key) {
        String captchaValue = CacheKit.get(GlobalCache.captchaCacheName, GlobalCache.captchaCachePrefix.concat("_").concat(key), String.class);
        CacheKit.evict(GlobalCache.captchaCacheName, GlobalCache.captchaCachePrefix.concat("_").concat(key));
        return captchaValue;
    }

    /**
     * 添加用户菜单缓存
     */
    public static void setStaffMenuCache(String staffId, List<StaffMenuVo> list) {
        String key = GlobalCache.staffMenuCachePrefix.concat("_").concat(staffId);
        CacheKit.put(GlobalCache.staffMenuCacheName, key, list);
    }

    /**
     * 删除用户菜单缓存
     */
    public static void clearStaffMenuCache() {
        CacheKit.clear(GlobalCache.staffMenuCacheName);
    }

    /**
     * 获取用户菜单缓存
     *
     * @return
     */
    public static List<StaffMenuVo> getStaffMenuCache(String staffId) {
        String key = GlobalCache.staffMenuCachePrefix.concat("_").concat(staffId);
        return CacheKit.get(GlobalCache.staffMenuCacheName, key, List.class);
    }

    /**
     * 用户角色编码缓存
     *
     * @param staffId
     * @param value
     */
    public static void setStaffRoleCode(String staffId, Object value) {
        String key = GlobalCache.staffRoleCodeCachePrefix.concat("_").concat(staffId);
        CacheKit.put(GlobalCache.staffRoleCodeCacheName, key, value);
    }

    /**
     * 用户角色编码缓存
     *
     * @param staffId
     */
    public static List<String> getStaffRoleCode(String staffId) {
        String key = GlobalCache.staffRoleCodeCachePrefix.concat("_").concat(staffId);
        return CacheKit.get(GlobalCache.staffRoleCodeCacheName, key, List.class);
    }

    /**
     * 用户角色编码缓存
     */
    public static void clearStaffRoleCode() {
        CacheKit.clear(GlobalCache.staffRoleCodeCacheName);
    }

    /**
     * 用户权限缓存
     *
     * @param staffId
     */
    public static void setStaffPermissions(String staffId, List<String> roles) {
        String key = GlobalCache.staffRoleCachePrefix.concat("_").concat(staffId);
        CacheKit.put(GlobalCache.staffRoleCacheName, key, roles);
    }

    /**
     * 获取用户权限缓存
     *
     * @param staffId
     */
    public static List<String> getStaffPermissions(String staffId) {
        String key = GlobalCache.staffRoleCachePrefix.concat("_").concat(staffId);
        return CacheKit.get(GlobalCache.staffRoleCacheName, key, List.class);
    }

    /**
     * 获取用户权限缓存
     */
    public static void clearStaffPermissions() {
        CacheKit.clear(GlobalCache.staffRoleCacheName);
    }

    /**
     * 文件上传配置
     */
    public static void setStaffFileConfig(String companyId, Object fileAccept) {
        String key = GlobalCache.staffFileConfigCachePrefix.concat("_").concat(companyId);
        CacheKit.put(GlobalCache.staffFileConfigCacheName, key, fileAccept);
    }

    /**
     * 文件上传配置
     */
    public static String getStaffFileConfig(String companyId) {
        String key = GlobalCache.staffFileConfigCachePrefix.concat("_").concat(companyId);
        return CacheKit.get(GlobalCache.staffFileConfigCacheName, key, String.class);
    }

    /**
     * 文件上传配置
     */
    public static void clearStaffFileConfig() {
        CacheKit.clear(GlobalCache.staffFileConfigCacheName);
    }

    /**
     * 清空用户缓存
     */
    public static void clearMenuRolePermissions() {
        String staffId = StpUtil.getExtra("staffId").toString();
        String companyId = StpUtil.getExtra("companyId").toString();

        CacheKit.evict(GlobalCache.staffRoleCacheName, GlobalCache.staffRoleCachePrefix.concat("_").concat(staffId));
        CacheKit.evict(GlobalCache.staffRoleCodeCacheName, GlobalCache.staffRoleCodeCachePrefix.concat("_").concat(staffId));
        CacheKit.evict(GlobalCache.staffMenuCacheName, GlobalCache.staffMenuCachePrefix.concat("_").concat(staffId));
        CacheKit.evict(GlobalCache.staffFileConfigCacheName, GlobalCache.staffFileConfigCachePrefix.concat("_").concat(companyId));

        IAuth auth = (IAuth) Application.getBean("auth");
        // 用户权限
        auth.queryStaffPermissions(staffId, null);
        //用户角色
        auth.queryStaffRoleCode(staffId, null);
        //允许上传文件类型
        FileConfigDTO dto = new FileConfigDTO();
        dto.setCompanyId(companyId);
        IFileConfigService fileConfigService = Application.getBean(FileConfigServiceImpl.class);
        fileConfigService.queryFileAccept(dto);
    }

    /**
     * 清空用户缓存
     */
    public static void clearStaffCache() {
        String staffId = StpUtil.getExtra("staffId").toString();
        String companyId = StpUtil.getExtra("companyId").toString();
        CacheKit.evict(GlobalCache.staffRoleCacheName, GlobalCache.staffRoleCachePrefix.concat("_").concat(staffId));
        CacheKit.evict(GlobalCache.staffRoleCodeCacheName, GlobalCache.staffRoleCodeCachePrefix.concat("_").concat(staffId));
        CacheKit.evict(GlobalCache.staffMenuCacheName, GlobalCache.staffMenuCachePrefix.concat("_").concat(staffId));
        CacheKit.evict(GlobalCache.staffFileConfigCacheName, GlobalCache.staffFileConfigCachePrefix.concat("_").concat(companyId));
        CacheKit.evict(GlobalCache.loginCodeCacheName, GlobalCache.loginCodeCachePrefix.concat("_").concat(staffId));
    }


    /**
     * 设置用户登陆编码缓存
     */
    public static void setLoginCode() {
        String staffId = StpUtil.getExtra("staffId").toString();
        String loginCode = StpUtil.getExtra("loginCode").toString();
        CacheKit.put(GlobalCache.loginCodeCacheName, GlobalCache.loginCodeCachePrefix.concat("_").concat(staffId), loginCode);
    }

    /**
     * 获取用户登陆编码缓存
     */
    public static String getLoginCode() {
        String staffId = StpUtil.getExtra("staffId").toString();
        return CacheKit.get(GlobalCache.loginCodeCacheName, GlobalCache.loginCodeCachePrefix.concat("_").concat(staffId), String.class);
    }

    /**
     * 清空用户登陆编码缓存
     */
    public static void clearLoginCode() {
        String staffId = StpUtil.getExtra("staffId").toString();
        String loginCode = StpUtil.getExtra("loginCode").toString();
        CacheKit.evict(GlobalCache.loginCodeCacheName, GlobalCache.loginCodeCachePrefix.concat("_").concat(staffId));
    }

    /**
     * 设置sql执行结果
     *
     * @param jobId
     * @param result
     */
    public static void setSqlResult(String jobId, String result) {
        String key = CacheCore.getSqlResultKey(jobId);
        String name = String.format("%s#%d", GlobalCache.sqlResultCacheName, GlobalCache.sqlResultExpirationTime);
        CacheKit.put(name, key, result);
    }

    /**
     * 获取sql执行结果
     *
     * @param jobId
     */
    public static String getSqlResult(String jobId) {
        String key = CacheCore.getSqlResultKey(jobId);
        String name = String.format("%s#%d", GlobalCache.sqlResultCacheName, GlobalCache.sqlResultExpirationTime);
        String result = CacheKit.get(name, key, String.class);
        return result;
    }

    /**
     * 设置数据库表字段缓存
     *
     * @param result
     */
    public static void setDbColumns(String dbSourceId, String scheam, String tableName, String result) {
        String key = String.format("%s_%s_%s_%s", GlobalCache.dbTableColumnCachePrefix, dbSourceId, scheam, tableName);
        CacheKit.put(GlobalCache.dbTableColumnCacheName, key, result);
    }

    /**
     * 获取数据库表字段缓存
     */
    public static String getDbColumns(String dbSourceId, String scheam, String tableName) {
        String key = String.format("%s_%s_%s_%s", GlobalCache.dbTableColumnCachePrefix, dbSourceId, scheam, tableName);
        String result = CacheKit.get(GlobalCache.dbTableColumnCacheName, key, String.class);
        return result;
    }

    /**
     * 删除指定缓存
     */
    public static void removeSqlResultById(String jobId) {
        String key = GlobalCache.sqlResultCachePrefix.concat("_").concat(jobId).concat("_").concat(DateFormatUtils.format(new Date(), "yyyyMMdd"));
        CacheKit.evict(GlobalCache.sqlResultCacheName, key);
    }

    /**
     * 清空所有缓存
     */
    public static void clearAllSqlResult() {
        CacheKit.clear(GlobalCache.sqlResultCacheName);
    }

    /**
     * 系统配置
     */
    public static void setConf(ConfVo conf) {
        CacheKit.put(GlobalCache.confCacheName, GlobalCache.confCachePrefix, JsonKit.toJsonString(conf));
    }

    public static ConfVo getConf() {
        String conf = CacheKit.get(GlobalCache.confCacheName, GlobalCache.confCachePrefix, String.class);
        if (StringUtils.isEmpty(conf)) {
            return null;
        }
        return JsonKit.parseObject(conf, ConfVo.class);
    }

    public static void clearConf() {
        CacheKit.clear(GlobalCache.confCacheName);
    }

    public static void setTaskStopCache(String jobId) {
        String key = CacheCore.getTaskStopKey(jobId);
        String name = CacheCore.getTaskStopName();
        CacheKit.put(name, key, jobId);
    }

    public static void getTaskStopCache(String jobId) {
        String key = CacheCore.getTaskStopKey(jobId);
        String name = CacheCore.getTaskStopName();
        CacheKit.put(name, key, jobId);
    }
}
