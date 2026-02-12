package com.easy.etl.fieldTypeDict.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 数据库字段类型映射-实体类
 *
 * @author 邵勇
 * @table TD_DB_FIELD_TYPE_DICT
 * @time 2024-01-17
 */
@TableName(value = "TD_DB_FIELD_TYPE_DICT", autoResultMap = true)
public class DbFieldTypeDict extends Model<DbFieldTypeDict> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 数据库类型ID
     */
    @TableField(value = "SOURCE_TYPE_ID")
    @Length(max = 32, message = "sourceTypeId数据库类型ID不能超过32个字符")
    private String sourceTypeId;
    /**
     * 父级数据库类型ID
     */
    @TableField(value = "PARENT_SOURCE_TYPE_ID")
    @Length(max = 32, message = "parentSourceTypeId父级数据库类型ID不能超过32个字符")
    private String parentSourceTypeId;
    /**
     * 编码
     */
    @TableField(value = "CODE")
    @Length(max = 32, message = "code编码不能超过32个字符")
    private String code;
    /**
     * 字段
     */
    @TableField(value = "FIELD")
    @Length(max = 32, message = "field字段不能超过32个字符")
    private String field;
/**
 *字段枚举
 */
    @TableField(value = "ENUM")
    @Length(max = 32, message = "enum字段枚举不能超过32个字符")
    private String enumName;
    /**
     * 字段大小
     */
    @TableField(value = "COLUMN_SIZE")
    private Integer columnSize;
    /**
     * 字段定义
     */
    @TableField(value = "COLUMN_DEFINITION")
    @Length(max = 32, message = "columnDefinition字段定义不能超过32个字符")
    private String columnDefinition;
    /**
     * 数据库类型
     */
    @TableField(value = "DB_TYPE")
    @Length(max = 32, message = "dbType数据库类型不能超过32个字符")
    private String dbType;


    public DbFieldTypeDict() {
        super();
    }

    /**
     * 主键-数据库字段:ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键-数据库字段:ID
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * 数据库类型ID-数据库字段:SOURCE_TYPE_ID
     */
    public String getSourceTypeId() {
        return sourceTypeId;
    }

    /**
     * 数据库类型ID-数据库字段:SOURCE_TYPE_ID
     */
    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    /**
     * 父级数据库类型ID-数据库字段:PARENT_SOURCE_TYPE_ID
     */
    public String getParentSourceTypeId() {
        return parentSourceTypeId;
    }

    /**
     * 父级数据库类型ID-数据库字段:PARENT_SOURCE_TYPE_ID
     */
    public void setParentSourceTypeId(String parentSourceTypeId) {
        this.parentSourceTypeId = parentSourceTypeId;
    }

    /**
     * 编码-数据库字段:CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * 编码-数据库字段:CODE
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 字段-数据库字段:FIELD
     */
    public String getField() {
        return field;
    }

    /**
     * 字段-数据库字段:FIELD
     */
    public void setField(String field) {
        this.field = field;
    }



    /**
     * 字段大小-数据库字段:COLUMN_SIZE
     */
    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    /**
     * 字段定义-数据库字段:COLUMN_DEFINITION
     */
    public String getColumnDefinition() {
        return columnDefinition;
    }

    /**
     * 字段定义-数据库字段:COLUMN_DEFINITION
     */
    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    /**
     * 数据库类型-数据库字段:DB_TYPE
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * 数据库类型-数据库字段:DB_TYPE
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }
}
