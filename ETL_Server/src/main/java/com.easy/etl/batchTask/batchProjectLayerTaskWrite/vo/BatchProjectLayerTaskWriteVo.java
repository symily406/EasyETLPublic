package com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo;


import com.easy.etl.datax.model.ReaderAndWriter;

/**
*  批处理分层任务写入-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class   BatchProjectLayerTaskWriteVo extends ReaderAndWriter {
    private String preSql;
    private String postSql;

    private String writeMode;

    private String hiveMode;



    public String getPreSql() {
        return preSql;
    }

    public void setPreSql(String preSql) {
        this.preSql = preSql;
    }

    public String getPostSql() {
        return postSql;
    }

    public void setPostSql(String postSql) {
        this.postSql = postSql;
    }


    public String getWriteMode() {
        return writeMode;
    }

    public void setWriteMode(String writeMode) {
        this.writeMode = writeMode;
    }

    public String getHiveMode() {
        return hiveMode;
    }

    public void setHiveMode(String hiveMode) {
        this.hiveMode = hiveMode;
    }
}
