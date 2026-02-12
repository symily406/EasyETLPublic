package com.easy.etl.conf.dto;


import com.easy.etl.conf.entity.*;

/**
*  系统配置-数据传输对象实体类
 * @author 邵勇
 * @time 2025-10-08
 */
public class ConfDTO extends Conf{
    private String conf;
    private String confDatax;
    private String confSql;
    private String confYarn;
    private String confBase;

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getConfDatax() {
        return confDatax;
    }

    public void setConfDatax(String confDatax) {
        this.confDatax = confDatax;
    }

    public String getConfSql() {
        return confSql;
    }

    public void setConfSql(String confSql) {
        this.confSql = confSql;
    }

    public String getConfYarn() {
        return confYarn;
    }

    public void setConfYarn(String confYarn) {
        this.confYarn = confYarn;
    }

    public String getConfBase() {
        return confBase;
    }

    public void setConfBase(String confBase) {
        this.confBase = confBase;
    }
}
