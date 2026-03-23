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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "QUOTE_ID", type = IdType.ASSIGN_ID)
    private String quoteId;


    @TableField(value = "PROJECT_ID")
    private String projectId;

    @TableField(value = "DB_SOURCE_ID")
    private String dbSourceId;

    @TableLogic
    @TableField(value = "IS_DEL", fill = FieldFill.INSERT)
    private Integer isDel;


    public ProjectDbSource() {
        super();
    }


    public String getQuoteId() {
        return quoteId;
    }


    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }



    public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getDbSourceId() {
        return dbSourceId;
    }


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
