package com.easy.etl.flowRunJob.vo;

public class FlowRunJobNodeStatusVo {
    private String flowRunJobId;
    private int total;

    private int succed;

    private int failed;

    private long totalUsageTime;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSucced() {
        return succed;
    }

    public void setSucced(int succed) {
        this.succed = succed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public String getFlowRunJobId() {
        return flowRunJobId;
    }

    public void setFlowRunJobId(String flowRunJobId) {
        this.flowRunJobId = flowRunJobId;
    }

    public long getTotalUsageTime() {
        return totalUsageTime;
    }

    public void setTotalUsageTime(long totalUsageTime) {
        this.totalUsageTime = totalUsageTime;
    }
}
