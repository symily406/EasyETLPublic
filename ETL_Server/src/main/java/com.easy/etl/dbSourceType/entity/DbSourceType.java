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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "SOURCE_TYPE_ID", type = IdType.ASSIGN_ID)
    private String sourceTypeId;


    /**
     * 名称
     */
    @TableField(value = "SOURCE_TYPE_CODE")
    @NotEmpty(message = "sourceTypeCode名称不能为空")
    @Length(max = 64, message = "sourceTypeCode名称不能超过64个字符")
    private String sourceTypeCode;

    /**
     * 名称
     */
    @TableField(value = "SOURCE_TYPE_NAME")
    @NotEmpty(message = "sourceTypeName名称不能为空")
    @Length(max = 64, message = "sourceTypeName名称不能超过64个字符")
    private String sourceTypeName;
    /**
     * 图片
     */
    @TableField(value = "SOURCE_TYPE_IMAGE")
    @NotEmpty(message = "sourceTypeImage图片不能为空")
    @Length(max = 64, message = "sourceTypeImage图片不能超过64个字符")
    private String sourceTypeImage;
    /**
     * 驱动
     */
    @TableField(value = "JDBC_DRIVE")
    @Length(max = 64, message = "jdbcDrive驱动不能超过64个字符")
    private String jdbcDrive;
    /**
     * 数据源类型
     */
    @TableField(value = "SOURCE_TYPE")
    private Integer sourceType;
    @TableField(value = "IS_CATALOG")
    private Integer isCatalog;

    /**
     * 是否有scheam
     */
    @TableField(value = "IS_SCHEMA")
    private Integer isSchema;

    @TableField(value = "IS_ACCOUNT")
    private Integer isAccount;

    @TableField(value = "IS_PASSWORD")
    private Integer isPassword;
    /**
     * 生成建表语句
     */
    @TableField(value = "ENABLE_CREATE_DDL")
    private Integer enableCreateDdl;


    public DbSourceType() {
        super();
    }

    /**
     * 主键-数据库字段:SOURCE_TYPE_ID
     */
    public String getSourceTypeId() {
        return sourceTypeId;
    }

    /**
     * 主键-数据库字段:SOURCE_TYPE_ID
     */
    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }


    /**
     * 名称-数据库字段:SOURCE_TYPE_NAME
     */
    public String getSourceTypeName() {
        return sourceTypeName;
    }

    /**
     * 名称-数据库字段:SOURCE_TYPE_NAME
     */
    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    /**
     * 图片-数据库字段:SOURCE_TYPE_IMAGE
     */
    public String getSourceTypeImage() {
        return sourceTypeImage;
    }

    /**
     * 图片-数据库字段:SOURCE_TYPE_IMAGE
     */
    public void setSourceTypeImage(String sourceTypeImage) {
        this.sourceTypeImage = sourceTypeImage;
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
     * 数据源类型-数据库字段:SOURCE_TYPE
     */
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

    /**
     * 生成建表语句-数据库字段:ENABLE_CREATE_DDL
     */
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
