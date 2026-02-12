package com.easy.etl.runTaskJob.model;

import cn.hutool.core.util.StrUtil;
import com.easy.etl.em.TaskStatusEnum;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogInfo implements Serializable {
    private String date;
    private String message;
    private String jobId;

    private Integer status;

    /**
     * 成功写入的记录数
     */
    private long writeSucceedRecords;

    /**
     * 成功读取的记录数
     */
    private long readSucceedRecords;

    /**
     * 错误总字节数（通常为0表示无错误）
     */
    private long totalErrorBytes;

    /**
     * 成功写入的字节数
     */
    private long writeSucceedBytes;

    /**
     * 当前字节传输速率（字节/秒）
     */
    private long byteSpeed;

    /**
     * 错误记录总数
     */
    private long totalErrorRecords;

    /**
     * 当前记录处理速率（记录数/秒）
     */
    private long recordSpeed;

    /**
     * 等待读取器的累计时间（毫秒）
     */
    private long waitReaderTime;

    /**
     * 接收到的待写入字节总数
     */
    private long writeReceivedBytes;

    /**
     * 等待写入器的累计时间（毫秒）
     */
    private long waitWriterTime;

    /**
     * 完成百分比（0.0-1.0）
     */
    private double percentage;

    /**
     * 总读取记录数（包含成功和失败）
     */
    private long totalReadRecords;

    /**
     * 接收到的待写入记录总数
     */
    private long writeReceivedRecords;

    /**
     * 成功读取的字节数
     */
    private long readSucceedBytes;

    /**
     * 总读取字节数（包含成功和失败）
     */
    private long totalReadBytes;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getDataxMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("读取记录:").append(getReadSucceedRecords()).append("条");
        sb.append("写入记录:").append(getWriteSucceedRecords()).append("条");
        sb.append("读取字节:").append(getReadSucceedBytes()).append("字节");
        sb.append("写入字节:").append(getWriteSucceedBytes()).append("字节");
        sb.append("错误记录:").append(getTotalErrorRecords()).append("条");
        sb.append("错误字节:").append(getTotalErrorBytes()).append("字节");
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getWriteSucceedRecords() {
        return writeSucceedRecords;
    }

    public void setWriteSucceedRecords(long writeSucceedRecords) {
        this.writeSucceedRecords = writeSucceedRecords;
    }

    public long getReadSucceedRecords() {
        return readSucceedRecords;
    }

    public void setReadSucceedRecords(long readSucceedRecords) {
        this.readSucceedRecords = readSucceedRecords;
    }

    public long getTotalErrorBytes() {
        return totalErrorBytes;
    }

    public void setTotalErrorBytes(long totalErrorBytes) {
        this.totalErrorBytes = totalErrorBytes;
    }

    public long getWriteSucceedBytes() {
        return writeSucceedBytes;
    }

    public void setWriteSucceedBytes(long writeSucceedBytes) {
        this.writeSucceedBytes = writeSucceedBytes;
    }

    public long getByteSpeed() {
        return byteSpeed;
    }

    public void setByteSpeed(long byteSpeed) {
        this.byteSpeed = byteSpeed;
    }

    public long getTotalErrorRecords() {
        return totalErrorRecords;
    }

    public void setTotalErrorRecords(long totalErrorRecords) {
        this.totalErrorRecords = totalErrorRecords;
    }

    public long getRecordSpeed() {
        return recordSpeed;
    }

    public void setRecordSpeed(long recordSpeed) {
        this.recordSpeed = recordSpeed;
    }

    public long getWaitReaderTime() {
        return waitReaderTime;
    }

    public void setWaitReaderTime(long waitReaderTime) {
        this.waitReaderTime = waitReaderTime;
    }

    public long getWriteReceivedBytes() {
        return writeReceivedBytes;
    }

    public void setWriteReceivedBytes(long writeReceivedBytes) {
        this.writeReceivedBytes = writeReceivedBytes;
    }

    public long getWaitWriterTime() {
        return waitWriterTime;
    }

    public void setWaitWriterTime(long waitWriterTime) {
        this.waitWriterTime = waitWriterTime;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public long getTotalReadRecords() {
        return totalReadRecords;
    }

    public void setTotalReadRecords(long totalReadRecords) {
        this.totalReadRecords = totalReadRecords;
    }

    public long getWriteReceivedRecords() {
        return writeReceivedRecords;
    }

    public void setWriteReceivedRecords(long writeReceivedRecords) {
        this.writeReceivedRecords = writeReceivedRecords;
    }

    public long getReadSucceedBytes() {
        return readSucceedBytes;
    }

    public void setReadSucceedBytes(long readSucceedBytes) {
        this.readSucceedBytes = readSucceedBytes;
    }

    public long getTotalReadBytes() {
        return totalReadBytes;
    }

    public void setTotalReadBytes(long totalReadBytes) {
        this.totalReadBytes = totalReadBytes;
    }
}
