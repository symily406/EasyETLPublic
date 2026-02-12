package com.easy.etl.batchTask.batchProjectLayerTaskRead.vo;


import com.easy.etl.datax.model.ReaderAndWriter;

/**
 * 批处理分层任务来源-视图实体类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public class BatchProjectLayerTaskReadVo extends ReaderAndWriter {
    /**
     * 高级配置
     */
    private Integer fetchSize;

    /**
     * 分片主键，用于数据切分
     */
    private String splitPk;

    /**
     * 查询过滤条件（WHERE子句等）
     */
    private String queryCondition;

    public String getSplitPk() {
        return splitPk;
    }

    public void setSplitPk(String splitPk) {
        this.splitPk = splitPk;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }
}
