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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "TEMPLATE_ID", type = IdType.ASSIGN_ID)
    private String templateId;

    /**
     * 模板类型
     */
    @TableField(value = "TEMPLATE_CODE")
    @NotEmpty(message = "templateCode模板类型不能为空")
    @Length(max = 32, message = "templateCode模板类型不能超过32个字符")
    private String templateCode;
    /**
     * 名称
     */
    @TableField(value = "NAME")
    @Length(max = 32, message = "name名称不能超过32个字符")
    private String name;
    /**
     * 模板内容
     */
    @TableField(value = "CONTENT")
    @NotEmpty(message = "content模板内容不能为空")
    private String content;
    /**
     * 启用
     */
    @TableField(value = "ENABLE")
    private Integer enable;
    /**
     * 显示排序
     */
    @TableField(value = "SHOW_ORDER")
    private Integer showOrder;


    public DbSourceDatax() {
        super();
    }

    /**
     * 主键-数据库字段:TEMPLATE_ID
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 主键-数据库字段:TEMPLATE_ID
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    /**
     * 模板类型-数据库字段:TEMPLATE_CODE
     */
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * 模板类型-数据库字段:TEMPLATE_CODE
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * 名称-数据库字段:NAME
     */
    public String getName() {
        return name;
    }

    /**
     * 名称-数据库字段:NAME
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 模板内容-数据库字段:CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * 模板内容-数据库字段:CONTENT
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 启用-数据库字段:ENABLE
     */
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 显示排序-数据库字段:SHOW_ORDER
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }


}
