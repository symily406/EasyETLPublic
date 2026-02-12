package com.easy.etl.runTaskJob.dto.result;

import cn.hutool.db.Entity;

import java.util.List;

public class JobResult {
    List<Entity> result;
    List<JobHeader> headers;

    private String runSql;

    private Long usageTime;

    public JobResult() {
        super();
    }

    public JobResult(String runSql, Long usageTime) {
        this.runSql = runSql;
        this.usageTime = usageTime;
    }

    public JobResult(List<Entity> result, List<JobHeader> headers, String runSql, Long usageTime) {
        this.result = result;
        this.headers = headers;
        this.runSql = runSql;
        this.usageTime = usageTime;
    }

    public List<Entity> getResult() {
        return result;
    }

    public void setResult(List<Entity> result) {
        this.result = result;
    }

    public List<JobHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<JobHeader> headers) {
        this.headers = headers;
    }

    public String getRunSql() {
        return runSql;
    }

    public void setRunSql(String runSql) {
        this.runSql = runSql;
    }

    public Long getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Long usageTime) {
        this.usageTime = usageTime;
    }
}
