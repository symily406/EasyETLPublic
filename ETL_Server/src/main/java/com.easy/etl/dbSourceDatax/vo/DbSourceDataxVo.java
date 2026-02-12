package com.easy.etl.dbSourceDatax.vo;


import com.easy.etl.dbSourceDatax.entity.DbSourceDatax;

/**
*  数据源datax模板-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class DbSourceDataxVo extends DbSourceDatax {

    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
