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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "MACHINE_ID", type = IdType.ASSIGN_ID)
    private String machineId;


    @TableField(value = "COMPUTER_NAME")
    private String computerName;

    @TableField(value = "COMPUTER_IP")
    private String computerIp;

    @TableField(value = "USER_DIR")
    private String userDir;

    @TableField(value = "OS_NAME")
    private String osName;

    @TableField(value = "OS_ARCH")
    private String osArch;

    @TableField(value = "REPORT_TIME")
    private Date reportTime;

    @TableField(value = "SHOW_ORDER")
    private Integer showOrder;


    @TableField(value = "SERVER_TYPE")
    private Integer serverType;


    @TableField(value = "IS_MASTER")
    private Integer isMaster;


    public Machine() {
        super();
    }


    public String getMachineId() {
        return machineId;
    }


    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }



    public String getComputerName() {
        return computerName;
    }


    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }


    public String getComputerIp() {
        return computerIp;
    }


    public void setComputerIp(String computerIp) {
        this.computerIp = computerIp;
    }


    public String getUserDir() {
        return userDir;
    }


    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }


    public String getOsName() {
        return osName;
    }


    public void setOsName(String osName) {
        this.osName = osName;
    }


    public String getOsArch() {
        return osArch;
    }


    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }


    public Date getReportTime() {
        return reportTime;
    }


    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }


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
