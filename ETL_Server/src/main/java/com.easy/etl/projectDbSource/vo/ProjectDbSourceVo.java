package com.easy.etl.projectDbSource.vo;


import com.easy.etl.projectDbSource.entity.ProjectDbSource;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 引用的数据源-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDbSourceVo extends ProjectDbSource {

    /**
     * 数据源类型
     */
    private String sourceTypeId;
    /**
     * 数据源名称
     */
    private String dbSourceName;
    /**
     * JDBC_URL
     */
    private String jdbcUrl;
    /**
     * 驱动
     */
    private String jdbcDrive;
    /**
     * 文件系统
     */
    private String defaultFs;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密盐值
     */
    private String salt;
    /**
     * 描述
     */
    private String description;
    /**
     * 开启Kerberos认证
     */
    private Integer isKerberos;
    /**
     * Kerberos路径
     */
    private String kerberosPath;
    /**
     * 是否能连接
     */
    private Integer isConn;

    private String dbCatalog;

    private String dbSchema;
    /**
     * 数据源数量
     */
    private Integer dbSourceNum;

    /**
     * 启用
     */
    private Integer enable;
    /**
     * 显示排序
     */
    private Integer showOrder;

    /**
     * 名称
     */
    private String sourceTypeName;
    /**
     * 图片
     */
    private String sourceTypeImage;

    /**
     * 数据源类型
     */
    private Integer sourceType;

    private String projectName;

    private int isSchema;

    private int isCatalog;

    private String label;

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

    public String getDefaultFs() {
        return defaultFs;
    }

    public void setDefaultFs(String defaultFs) {
        this.defaultFs = defaultFs;
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

    public Integer getIsKerberos() {
        return isKerberos;
    }

    public void setIsKerberos(Integer isKerberos) {
        this.isKerberos = isKerberos;
    }

    public String getKerberosPath() {
        return kerberosPath;
    }

    public void setKerberosPath(String kerberosPath) {
        this.kerberosPath = kerberosPath;
    }

    public Integer getIsConn() {
        return isConn;
    }

    public void setIsConn(Integer isConn) {
        this.isConn = isConn;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getSourceTypeImage() {
        return sourceTypeImage;
    }

    public void setSourceTypeImage(String sourceTypeImage) {
        this.sourceTypeImage = sourceTypeImage;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDbSourceNum() {
        return dbSourceNum;
    }

    public void setDbSourceNum(Integer dbSourceNum) {
        this.dbSourceNum = dbSourceNum;
    }


    public String getLabel() {
        return getDbSourceName().concat("（").concat(getSourceTypeName()).concat("）");
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIsCatalog() {
        return isCatalog;
    }

    public void setIsCatalog(int isCatalog) {
        this.isCatalog = isCatalog;
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

    public int getIsSchema() {
        return isSchema;
    }

    public void setIsSchema(int isSchema) {
        this.isSchema = isSchema;
    }
}
