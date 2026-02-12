package com.easy.etl.conf.vo;


import com.easy.etl.conf.entity.*;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
*  系统配置-视图实体类
 * @author 邵勇
 * @time 2025-10-08
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfVo extends  Conf {
    private ConfBase confBase;
    private ConfYarn confYarn;
    private ConfDatax confDatax;
    private ConfSql confSql;

    public ConfBase getConfBase() {
        return confBase;
    }

    public void setConfBase(ConfBase confBase) {
        this.confBase = confBase;
    }

    public ConfYarn getConfYarn() {
        return confYarn;
    }

    public void setConfYarn(ConfYarn confYarn) {
        this.confYarn = confYarn;
    }

    public ConfDatax getConfDatax() {
        return confDatax;
    }

    public void setConfDatax(ConfDatax confDatax) {
        this.confDatax = confDatax;
    }

    public ConfSql getConfSql() {
        return confSql;
    }

    public void setConfSql(ConfSql confSql) {
        this.confSql = confSql;
    }
}
