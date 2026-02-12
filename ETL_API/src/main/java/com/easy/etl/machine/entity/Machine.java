package com.easy.etl.machine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 服务器-实体类
 *
 * @author 邵勇
 * @table TF_MACHINE
 * @time 2024-01-17
 */
@TableName(value = "TF_MACHINE", autoResultMap = true)
public class Machine extends Model<Machine> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "MACHINE_ID", type = IdType.ASSIGN_ID)
    private String machineId;

    /**
     * 服务器名称
     */
    @TableField(value = "COMPUTER_NAME")
    @Length(max = 64, message = "computerName服务器名称不能超过64个字符")
    private String computerName;
    /**
     * 服务器Ip
     */
    @TableField(value = "COMPUTER_IP")
    @Length(max = 32, message = "computerIp服务器Ip不能超过32个字符")
    private String computerIp;
    /**
     * 项目路径
     */
    @TableField(value = "USER_DIR")
    @Length(max = 256, message = "userDir项目路径不能超过256个字符")
    private String userDir;
    /**
     * 操作系统
     */
    @TableField(value = "OS_NAME")
    @Length(max = 64, message = "osName操作系统不能超过64个字符")
    private String osName;
    /**
     * 系统架构
     */
    @TableField(value = "OS_ARCH")
    @Length(max = 64, message = "osArch系统架构不能超过64个字符")
    private String osArch;
    /**
     * 上报时间
     */
    @TableField(value = "REPORT_TIME")
    private Date reportTime;
    /**
     * 显示排序
     */
    @TableField(value = "SHOW_ORDER")
    private Integer showOrder;

    /**
     * 服务类型
     */
    @TableField(value = "SERVER_TYPE")
    private Integer serverType;

    /**
     * 服务类型
     */
    @TableField(value = "IS_MASTER")
    private Integer isMaster;


    public Machine() {
        super();
    }

    /**
     * 主键-数据库字段:MACHINE_ID
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * 主键-数据库字段:MACHINE_ID
     */
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }


    /**
     * 服务器名称-数据库字段:COMPUTER_NAME
     */
    public String getComputerName() {
        return computerName;
    }

    /**
     * 服务器名称-数据库字段:COMPUTER_NAME
     */
    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    /**
     * 服务器Ip-数据库字段:COMPUTER_IP
     */
    public String getComputerIp() {
        return computerIp;
    }

    /**
     * 服务器Ip-数据库字段:COMPUTER_IP
     */
    public void setComputerIp(String computerIp) {
        this.computerIp = computerIp;
    }

    /**
     * 项目路径-数据库字段:USER_DIR
     */
    public String getUserDir() {
        return userDir;
    }

    /**
     * 项目路径-数据库字段:USER_DIR
     */
    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    /**
     * 操作系统-数据库字段:OS_NAME
     */
    public String getOsName() {
        return osName;
    }

    /**
     * 操作系统-数据库字段:OS_NAME
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * 系统架构-数据库字段:OS_ARCH
     */
    public String getOsArch() {
        return osArch;
    }

    /**
     * 系统架构-数据库字段:OS_ARCH
     */
    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    /**
     * 上报时间-数据库字段:REPORT_TIME
     */
    public Date getReportTime() {
        return reportTime;
    }

    /**
     * 上报时间-数据库字段:REPORT_TIME
     */
    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    /**
     * 显示排序-数据库字段:SHOW_ORDER
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Integer getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }
}
