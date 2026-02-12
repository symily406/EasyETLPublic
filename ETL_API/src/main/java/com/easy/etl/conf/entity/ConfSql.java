package com.easy.etl.conf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.common.conf.Sql;

/**
 * SQL执行器配置-实体类
 *
 * @author 邵勇
 * @table TD_CONF_SQL
 * @time 2025-10-08
 */
@TableName(value = "TD_CONF_SQL", autoResultMap = true)
public class ConfSql extends Sql {
    public ConfSql() {
        super();
    }
}

