package com.easy.etl.conf.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.core.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 系统配置主表-实体类
 *
 * @author 邵勇
 * @table TD_CONF
 * @time 2025-10-08
 */
@TableName(value = "TD_CONF", autoResultMap = true)
public class Conf extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "CONF_ID", type = IdType.ASSIGN_ID)
    private String confId;

    /**
     * 配置名称
     */
    @TableField(value = "CONF_NAME")
    @Length(max = 64, message = "confName配置名称不能超过64个字符")
    private String confName;
    /**
     * 默认配置
     */
    @TableField(value = "IS_DEFAULT")
    private Integer isDefault;



    public Conf() {
        super();
    }

    /**
     * 主键-数据库字段:CONF_ID
     */
    public String getConfId() {
        return confId;
    }

    /**
     * 主键-数据库字段:CONF_ID
     */
    public void setConfId(String confId) {
        this.confId = confId;
    }


    /**
     * 配置名称-数据库字段:CONF_NAME
     */
    public String getConfName() {
        return confName;
    }

    /**
     * 配置名称-数据库字段:CONF_NAME
     */
    public void setConfName(String confName) {
        this.confName = confName;
    }

    /**
     * 默认配置-数据库字段:IS_DEFAULT
     */
    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
