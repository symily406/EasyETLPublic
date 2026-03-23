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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "CONF_ID", type = IdType.ASSIGN_ID)
    private String confId;


    @TableField(value = "CONF_NAME")
    private String confName;

    @TableField(value = "IS_DEFAULT")
    private Integer isDefault;



    public Conf() {
        super();
    }


    public String getConfId() {
        return confId;
    }


    public void setConfId(String confId) {
        this.confId = confId;
    }



    public String getConfName() {
        return confName;
    }


    public void setConfName(String confName) {
        this.confName = confName;
    }


    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
