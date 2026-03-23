package com.easy.etl.machine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 服务器硬盘信息-实体类
 *
 * @author 邵勇
 * @table TF_MACHINE_HARD_DISK
 * @time 2024-01-17
 */
@TableName(value = "TF_MACHINE_HARD_DISK", autoResultMap = true)
public class MachineHardDisk extends Model<MachineHardDisk> {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "HARD_DISK_ID", type = IdType.ASSIGN_ID)
    private String hardDiskId;


    @TableField(value = "MACHINE_ID")
    private String machineId;

    @TableField(value = "DIR_NAME")
    private String dirName;

    @TableField(value = "SYS_TYPE_NAME")
    private String sysTypeName;

    @TableField(value = "TYPE_NAME")
    private String typeName;

    @TableField(value = "TOTAL")
    private String total;

    @TableField(value = "FREE")
    private String free;

    @TableField(value = "USED")
    private String used;

    @TableField(value = "USAGE_RATE")
    private Integer usageRate;


    public MachineHardDisk() {
        super();
    }


    public String getHardDiskId() {
        return hardDiskId;
    }


    public void setHardDiskId(String hardDiskId) {
        this.hardDiskId = hardDiskId;
    }



    public String getMachineId() {
        return machineId;
    }


    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }


    public String getDirName() {
        return dirName;
    }


    public void setDirName(String dirName) {
        this.dirName = dirName;
    }


    public String getSysTypeName() {
        return sysTypeName;
    }


    public void setSysTypeName(String sysTypeName) {
        this.sysTypeName = sysTypeName;
    }


    public String getTypeName() {
        return typeName;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public String getTotal() {
        return total;
    }


    public void setTotal(String total) {
        this.total = total;
    }


    public String getFree() {
        return free;
    }


    public void setFree(String free) {
        this.free = free;
    }


    public String getUsed() {
        return used;
    }


    public void setUsed(String used) {
        this.used = used;
    }


    public Integer getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(Integer usageRate) {
        this.usageRate = usageRate;
    }


}
