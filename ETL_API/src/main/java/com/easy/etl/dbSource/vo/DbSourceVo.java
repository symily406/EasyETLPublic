package com.easy.etl.dbSource.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.easy.etl.dbSource.entity.DbSource;

/**
 * 数据源-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class DbSourceVo extends DbSource {




    private String sourceTypeName;

    private String sourceTypeCode;

    private int sourceType;

    private boolean isConnect;

    private Integer isCatalog;

    private Integer isSchema;

    private Integer isAccount;

    private Integer isPassword;

    private String dbKey;

    public String getDbKey() {
        return "Db_".concat(getDbSourceId());
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }


    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTypeCode() {
        return sourceTypeCode;
    }

    public void setSourceTypeCode(String sourceTypeCode) {
        this.sourceTypeCode = sourceTypeCode;
    }

    public Integer getIsCatalog() {
        return isCatalog;
    }

    public void setIsCatalog(Integer isCatalog) {
        this.isCatalog = isCatalog;
    }

    public Integer getIsSchema() {
        return isSchema;
    }

    public void setIsSchema(Integer isSchema) {
        this.isSchema = isSchema;
    }

    public Integer getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(Integer isAccount) {
        this.isAccount = isAccount;
    }

    public Integer getIsPassword() {
        return isPassword;
    }

    public void setIsPassword(Integer isPassword) {
        this.isPassword = isPassword;
    }
}
