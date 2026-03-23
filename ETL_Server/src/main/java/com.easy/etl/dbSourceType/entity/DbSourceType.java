package com.easy.etl.dbSourceType.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.easy.etl.core.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 数据源类型-实体类
 *
 * @author 邵勇
 * @table TD_DB_SOURCE_TYPE
 * @time 2025-10-08
 */
@TableName(value = "TD_DB_SOURCE_TYPE", autoResultMap = true)
public class DbSourceType extends BaseEntity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "SOURCE_TYPE_ID", type = IdType.ASSIGN_ID)
    private String sourceTypeId;



    @TableField(value = "SOURCE_TYPE_CODE")
    private String sourceTypeCode;


    @TableField(value = "SOURCE_TYPE_NAME")
    private String sourceTypeName;

    @TableField(value = "SOURCE_TYPE_IMAGE")
    private String sourceTypeImage;

    @TableField(value = "JDBC_DRIVE")
    private String jdbcDrive;

    @TableField(value = "SOURCE_TYPE")
    private Integer sourceType;
    @TableField(value = "IS_CATALOG")
    private Integer isCatalog;


    @TableField(value = "IS_SCHEMA")
    private Integer isSchema;

    @TableField(value = "IS_ACCOUNT")
    private Integer isAccount;

    @TableField(value = "IS_PASSWORD")
    private Integer isPassword;

    @TableField(value = "ENABLE_CREATE_DDL")
    private Integer enableCreateDdl;


    public DbSourceType() {
        super();
    }


    public String getSourceTypeId() {
        return sourceTypeId;
    }


    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
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


    public String getJdbcDrive() {
        return jdbcDrive;
    }


    public void setJdbcDrive(String jdbcDrive) {
        this.jdbcDrive = jdbcDrive;
    }


    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getIsSchema() {
        return isSchema;
    }

    public void setIsSchema(Integer isSchema) {
        this.isSchema = isSchema;
    }


    public Integer getEnableCreateDdl() {
        return enableCreateDdl;
    }

    public void setEnableCreateDdl(Integer enableCreateDdl) {
        this.enableCreateDdl = enableCreateDdl;
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
