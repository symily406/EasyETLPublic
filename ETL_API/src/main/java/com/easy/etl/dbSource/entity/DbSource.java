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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "DB_SOURCE_ID", type = IdType.ASSIGN_ID)
    private String dbSourceId;


    @TableField(value = "SOURCE_TYPE_ID")
    private String sourceTypeId;

    @TableField(value = "DB_SOURCE_NAME")
    private String dbSourceName;

    @TableField(value = "JDBC_URL")
    private String jdbcUrl;

    @TableField(value = "JDBC_DRIVE")
    private String jdbcDrive;

    @TableField(value = "USER_NAME")
    private String userName;

    @TableField(value = "PASSWORD")
    private String password;

    @TableField(value = "SALT")
    private String salt;

    @TableField(value = "DB_CATALOG")
    private String dbCatalog;

    @TableField(value = "DB_SCHEMA")
    private String dbSchema;

    @TableField(value = "DESCRIPTION")
    private String description;

    @TableField(value = "DB_DIALECT")
    private String dbDialect;

    @TableField(value = "DEFAULT_FS")
    private String defaultFs;

    @TableField(value = "HAVE_KERBEROS")
    private Integer haveKerberos;

    @TableField(value = "KERBEROS_KEYTAB_FILE_PATH")
    private String kerberosKeytabFilePath;

    @TableField(value = "KERBEROS_PRINCIPAL")
    private String kerberosPrincipal;

    @TableField(value = "FILE_TYPE")
    private String fileType;

    @TableField(value = "IS_CONN")
    private Integer isConn;

    @TableField(value = "QUOTE_NUM")
    private Integer quoteNum;


    public DbSource() {
        super();
    }


    public String getDbSourceId() {
        return dbSourceId;
    }


    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }



    public String getSourceTypeId() {
        return sourceTypeId;
    }


    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }


    public String getDbSourceName() {
        return dbSourceName;
    }


    public void setDbSourceName(String dbSourceName) {
        this.dbSourceName = dbSourceName;
    }


    public String getJdbcUrl() {
        return jdbcUrl;
    }


    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }


    public String getJdbcDrive() {
        return jdbcDrive;
    }


    public void setJdbcDrive(String jdbcDrive) {
        this.jdbcDrive = jdbcDrive;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getSalt() {
        return salt;
    }


    public void setSalt(String salt) {
        this.salt = salt;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getDbDialect() {
        return dbDialect;
    }


    public void setDbDialect(String dbDialect) {
        this.dbDialect = dbDialect;
    }


    public String getDefaultFs() {
        return defaultFs;
    }


    public void setDefaultFs(String defaultFs) {
        this.defaultFs = defaultFs;
    }


    public Integer getHaveKerberos() {
        return haveKerberos;
    }

    public void setHaveKerberos(Integer haveKerberos) {
        this.haveKerberos = haveKerberos;
    }


    public String getKerberosKeytabFilePath() {
        return kerberosKeytabFilePath;
    }


    public void setKerberosKeytabFilePath(String kerberosKeytabFilePath) {
        this.kerberosKeytabFilePath = kerberosKeytabFilePath;
    }


    public String getKerberosPrincipal() {
        return kerberosPrincipal;
    }


    public void setKerberosPrincipal(String kerberosPrincipal) {
        this.kerberosPrincipal = kerberosPrincipal;
    }


    public String getFileType() {
        return fileType;
    }


    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public Integer getIsConn() {
        return isConn;
    }

    public void setIsConn(Integer isConn) {
        this.isConn = isConn;
    }


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
