package com.easy.etl.fieldTypeDict.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.easy.etl.fieldTypeDict.entity.DbFieldTypeDict;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 数据库字段类型映射-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class DbFieldTypeDictVo extends DbFieldTypeDict {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 字段注释
     */
    private String comment;
    /**
     * 小数位数
     */
    private String point;

    private String code;

    private String writeFileType;

    private String columnType;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getWriteFileType() {
        return writeFileType;
    }

    public void setWriteFileType(String writeFileType) {
        this.writeFileType = writeFileType;
    }

    public String getColumnType() {
        StringBuilder column = new StringBuilder();
        if (StringUtils.isEmpty(getWriteFileType())) {
            column.append(getFieldName()).append("varchar(").append(getColumnSize()).append(")");
            return column.toString();
        }
        column.append(getFieldName())
                .append(getWriteFileType())
                .append("(").append(getColumnType());
        if (StringUtils.isNotEmpty(getPoint())) {
            column.append(",").append(getPoint());
        }
        column.append(")");
        return column.toString();
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
}
