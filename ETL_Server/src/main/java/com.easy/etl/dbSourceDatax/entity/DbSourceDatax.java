package com.easy.etl.dbSourceDatax.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.core.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 数据源datax模板-实体类
 *
 * @author 邵勇
 * @table TD_DB_SOURCE_DATAX
 * @time 2024-01-17
 */
@TableName(value = "TD_DB_SOURCE_DATAX", autoResultMap = true)
public class DbSourceDatax extends BaseEntity {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "TEMPLATE_ID", type = IdType.ASSIGN_ID)
    private String templateId;


    @TableField(value = "TEMPLATE_CODE")
    private String templateCode;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "CONTENT")
    private String content;

    @TableField(value = "ENABLE")
    private Integer enable;

    @TableField(value = "SHOW_ORDER")
    private Integer showOrder;


    public DbSourceDatax() {
        super();
    }


    public String getTemplateId() {
        return templateId;
    }


    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }



    public String getTemplateCode() {
        return templateCode;
    }


    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
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


}
