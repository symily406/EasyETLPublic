package com.easy.etl.runTaskJob.model;


import java.io.Serializable;

/**
 * 数据读写性能统计
 */
public class DataTransferStats implements Serializable {
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
