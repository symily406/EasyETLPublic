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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Integer id;


    @TableField(value = "SOURCE_TYPE_ID")
    private String sourceTypeId;

    @TableField(value = "PARENT_SOURCE_TYPE_ID")
    private String parentSourceTypeId;

    @TableField(value = "CODE")
    private String code;

    @TableField(value = "FIELD")
    private String field;

    @TableField(value = "ENUM")
    private String enumName;

    @TableField(value = "COLUMN_SIZE")
    private Integer columnSize;

    @TableField(value = "COLUMN_DEFINITION")
    private String columnDefinition;

    @TableField(value = "DB_TYPE")
    private String dbType;


    public DbFieldTypeDict() {
        super();
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }



    public String getSourceTypeId() {
        return sourceTypeId;
    }


    public void setSourceTypeId(String sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }


    public String getParentSourceTypeId() {
        return parentSourceTypeId;
    }


    public void setParentSourceTypeId(String parentSourceTypeId) {
        this.parentSourceTypeId = parentSourceTypeId;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getField() {
        return field;
    }


    public void setField(String field) {
        this.field = field;
    }




    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }


    public String getColumnDefinition() {
        return columnDefinition;
    }


    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }


    public String getDbType() {
        return dbType;
    }


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
