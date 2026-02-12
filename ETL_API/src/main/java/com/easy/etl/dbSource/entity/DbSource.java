package com.easy.etl.dbSource.entity;

import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 数据源-实体类
 *
 * @author 邵勇
 * @table TD_DB_SOURCE
 * @time 2025-10-08
 */
@TableName(value = "TD_DB_SOURCE", autoResultMap = true)
public class DbSource extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "DB_SOURCE_ID", type = IdType.ASSIGN_ID)
    private String dbSourceId;

    /**
     * 数据源类型
     */
    @TableField(value = "SOURCE_TYPE_ID")
    @NotEmpty(message = "sourceTypeId数据源类型不能为空")
    @Length(max = 32, message = "sourceTypeId数据源类型不能超过32个字符")
    private String sourceTypeId;
    /**
     * 数据源名称
     */
    @TableField(value = "DB_SOURCE_NAME")
    @NotEmpty(message = "dbSourceName数据源名称不能为空")
    @Length(max = 64, message = "dbSourceName数据源名称不能超过64个字符")
    private String dbSourceName;
    /**
     * JDBC_URL
     */
    @TableField(value = "JDBC_URL")
    @NotEmpty(message = "jdbcUrlJDBC_URL不能为空")
    @Length(max = 512, message = "jdbcUrlJDBC_URL不能超过512个字符")
    private String jdbcUrl;
    /**
     * 驱动
     */
    @TableField(value = "JDBC_DRIVE")
    @Length(max = 64, message = "jdbcDrive驱动不能超过64个字符")
    private String jdbcDrive;
    /**
     * 用户名
     */
    @TableField(value = "USER_NAME")
    @Length(max = 64, message = "userName用户名不能超过64个字符")
    private String userName;
    /**
     * 密码
     */
    @TableField(value = "PASSWORD")
    @Length(max = 1024, message = "password密码不能超过1024个字符")
    private String password;
    /**
     * 加密盐值
     */
    @TableField(value = "SALT")
    @Length(max = 32, message = "salt加密盐值不能超过32个字符")
    private String salt;

    @TableField(value = "DB_CATALOG")
    private String dbCatalog;

    @TableField(value = "DB_SCHEMA")
    private String dbSchema;
    /**
     * 描述
     */
    @TableField(value = "DESCRIPTION")
    @Length(max = 512, message = "description描述不能超过512个字符")
    private String description;
    /**
     * 数据库语言
     */
    @TableField(value = "DB_DIALECT")
    @Length(max = 32, message = "dbDialect数据库语言不能超过32个字符")
    private String dbDialect;
    /**
     * 文件系统
     */
    @TableField(value = "DEFAULT_FS")
    @Length(max = 128, message = "defaultFs文件系统不能超过128个字符")
    private String defaultFs;
    /**
     * 是否有Kerberos认证
     */
    @TableField(value = "HAVE_KERBEROS")
    private Integer haveKerberos;
    /**
     * keytab文件路径
     */
    @TableField(value = "KERBEROS_KEYTAB_FILE_PATH")
    @Length(max = 512, message = "kerberosKeytabFilePathkeytab文件路径不能超过512个字符")
    private String kerberosKeytabFilePath;
    /**
     * Kerberos认证Principal名
     */
    @TableField(value = "KERBEROS_PRINCIPAL")
    @Length(max = 512, message = "kerberosPrincipalKerberos认证Principal名不能超过512个字符")
    private String kerberosPrincipal;
    /**
     * 文件类型
     */
    @TableField(value = "FILE_TYPE")
    @Length(max = 32, message = "fileType文件类型不能超过32个字符")
    private String fileType;
    /**
     * 是否能连接
     */
    @TableField(value = "IS_CONN")
    private Integer isConn;
    /**
     * 引用次数
     */
    @TableField(value = "QUOTE_NUM")
    private Integer quoteNum;


    public DbSource() {
        super();
    }

    /**
     * 主键-数据库字段:DB_SOURCE_ID
     */
    public String getDbSourceId() {
        return dbSourceId;
    }

    /**
     * 主键-数据库字段:DB_SOURCE_ID
     */
    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }


    /**
     * 数据源类型-数据库字段:SOURCE_TYPE_ID
     */
    public String getSourceTypeId() {
        return sourceTypeId;
    }

    /**
     * 数据源类型-数据库字段:SOURCE_TYPE_ID
     */
    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    /**
     * 数据源名称-数据库字段:DB_SOURCE_NAME
     */
    public String getDbSourceName() {
        return dbSourceName;
    }

    /**
     * 数据源名称-数据库字段:DB_SOURCE_NAME
     */
    public void setDbSourceName(String dbSourceName) {
        this.dbSourceName = dbSourceName;
    }

    /**
     * JDBC_URL-数据库字段:JDBC_URL
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * JDBC_URL-数据库字段:JDBC_URL
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * 驱动-数据库字段:JDBC_DRIVE
     */
    public String getJdbcDrive() {
        return jdbcDrive;
    }

    /**
     * 驱动-数据库字段:JDBC_DRIVE
     */
    public void setJdbcDrive(String jdbcDrive) {
        this.jdbcDrive = jdbcDrive;
    }

    /**
     * 用户名-数据库字段:USER_NAME
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名-数据库字段:USER_NAME
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 密码-数据库字段:PASSWORD
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码-数据库字段:PASSWORD
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 加密盐值-数据库字段:SALT
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 加密盐值-数据库字段:SALT
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 描述-数据库字段:DESCRIPTION
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述-数据库字段:DESCRIPTION
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 数据库语言-数据库字段:DB_DIALECT
     */
    public String getDbDialect() {
        return dbDialect;
    }

    /**
     * 数据库语言-数据库字段:DB_DIALECT
     */
    public void setDbDialect(String dbDialect) {
        this.dbDialect = dbDialect;
    }

    /**
     * 文件系统-数据库字段:DEFAULT_FS
     */
    public String getDefaultFs() {
        return defaultFs;
    }

    /**
     * 文件系统-数据库字段:DEFAULT_FS
     */
    public void setDefaultFs(String defaultFs) {
        this.defaultFs = defaultFs;
    }

    /**
     * 是否有Kerberos认证-数据库字段:HAVE_KERBEROS
     */
    public Integer getHaveKerberos() {
        return haveKerberos;
    }

    public void setHaveKerberos(Integer haveKerberos) {
        this.haveKerberos = haveKerberos;
    }

    /**
     * keytab文件路径-数据库字段:KERBEROS_KEYTAB_FILE_PATH
     */
    public String getKerberosKeytabFilePath() {
        return kerberosKeytabFilePath;
    }

    /**
     * keytab文件路径-数据库字段:KERBEROS_KEYTAB_FILE_PATH
     */
    public void setKerberosKeytabFilePath(String kerberosKeytabFilePath) {
        this.kerberosKeytabFilePath = kerberosKeytabFilePath;
    }

    /**
     * Kerberos认证Principal名-数据库字段:KERBEROS_PRINCIPAL
     */
    public String getKerberosPrincipal() {
        return kerberosPrincipal;
    }

    /**
     * Kerberos认证Principal名-数据库字段:KERBEROS_PRINCIPAL
     */
    public void setKerberosPrincipal(String kerberosPrincipal) {
        this.kerberosPrincipal = kerberosPrincipal;
    }

    /**
     * 文件类型-数据库字段:FILE_TYPE
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 文件类型-数据库字段:FILE_TYPE
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 是否能连接-数据库字段:IS_CONN
     */
    public Integer getIsConn() {
        return isConn;
    }

    public void setIsConn(Integer isConn) {
        this.isConn = isConn;
    }

    /**
     * 引用次数-数据库字段:QUOTE_NUM
     */
    public Integer getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }

    public String getDbCatalog() {
        return dbCatalog;
    }

    public void setDbCatalog(String dbCatalog) {
        this.dbCatalog = dbCatalog;
    }

    public String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }
}
