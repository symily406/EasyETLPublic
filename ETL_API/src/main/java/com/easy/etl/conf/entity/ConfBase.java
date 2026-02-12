package com.easy.etl.conf.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.common.conf.Base;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 基础配置-实体类
 *
 * @author 邵勇
 * @table TD_CONF_BASE
 * @time 2025-10-08
 */
@TableName(value = "TD_CONF_BASE", autoResultMap = true)
public class ConfBase extends Base {
    public ConfBase(){
        super();
    }
}
