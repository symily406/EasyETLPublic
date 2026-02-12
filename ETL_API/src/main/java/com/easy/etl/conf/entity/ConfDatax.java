package com.easy.etl.conf.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.easy.etl.common.conf.Datax;
import com.easy.etl.core.entity.Entity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * DATAX配置-实体类
 *
 * @author 邵勇
 * @table TD_CONF_DATAX
 * @time 2025-10-08
 */
@TableName(value = "TD_CONF_DATAX", autoResultMap = true)
public class ConfDatax extends Datax {
    public ConfDatax(){
        super();
    }
}
