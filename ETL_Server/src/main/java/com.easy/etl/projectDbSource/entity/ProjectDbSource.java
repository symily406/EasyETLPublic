package com.easy.etl.projectDbSource.entity;

import com.baomidou.mybatisplus.annotation.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 引用的数据源-实体类
 *
 * @author 邵勇
 * @table TF_PROJECT_DB_SOURCE
 * @time 2024-01-17
 */
@TableName(value = "TF_PROJECT_DB_SOURCE", autoResultMap = true)
public class ProjectDbSource {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "QUOTE_ID", type = IdType.ASSIGN_ID)
    private String quoteId;

    /**
     * 归属项目
     */
    @TableField(value = "PROJECT_ID")
    @NotEmpty(message = "projectId归属项目不能为空")
    @Length(max = 32, message = "projectId归属项目不能超过32个字符")
    private String projectId;
    /**
     * 数据源
     */
    @TableField(value = "DB_SOURCE_ID")
    @NotEmpty(message = "dbSourceId数据源不能为空")
    @Length(max = 32, message = "dbSourceId数据源不能超过32个字符")
    private String dbSourceId;

    @TableLogic
    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public ProjectDbSource() {
        super();
    }

    /**
     * 主键-数据库字段:QUOTE_ID
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * 主键-数据库字段:QUOTE_ID
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }


    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 归属项目-数据库字段:PROJECT_ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * 数据源-数据库字段:DB_SOURCE_ID
     */
    public String getDbSourceId() {
        return dbSourceId;
    }

    /**
     * 数据源-数据库字段:DB_SOURCE_ID
     */
    public void setDbSourceId(String dbSourceId) {
        this.dbSourceId = dbSourceId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
