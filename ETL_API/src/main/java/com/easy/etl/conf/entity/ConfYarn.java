package com.easy.etl.conf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.etl.common.conf.Yarn;

/**
 * yarn配置-实体类
 *
 * @author 邵勇
 * @table TD_CONF_YARN
 * @time 2025-10-08
 */
@TableName(value = "TD_CONF_YARN", autoResultMap = true)
public class ConfYarn extends Yarn {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;


}
