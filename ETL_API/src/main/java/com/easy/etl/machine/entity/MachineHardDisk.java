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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "HARD_DISK_ID", type = IdType.ASSIGN_ID)
    private String hardDiskId;

    /**
     * 归属服务器
     */
    @TableField(value = "MACHINE_ID")
    @NotEmpty(message = "machineId归属服务器不能为空")
    @Length(max = 32, message = "machineId归属服务器不能超过32个字符")
    private String machineId;
    /**
     * 盘符路径
     */
    @TableField(value = "DIR_NAME")
    @Length(max = 64, message = "dirName盘符路径不能超过64个字符")
    private String dirName;
    /**
     * 盘符类型
     */
    @TableField(value = "SYS_TYPE_NAME")
    @Length(max = 64, message = "sysTypeName盘符类型不能超过64个字符")
    private String sysTypeName;
    /**
     * 文件类型
     */
    @TableField(value = "TYPE_NAME")
    @Length(max = 32, message = "typeName文件类型不能超过32个字符")
    private String typeName;
    /**
     * 总大小
     */
    @TableField(value = "TOTAL")
    @Length(max = 32, message = "total总大小不能超过32个字符")
    private String total;
    /**
     * 剩余大小
     */
    @TableField(value = "FREE")
    @Length(max = 32, message = "free剩余大小不能超过32个字符")
    private String free;
    /**
     * 已经使用量
     */
    @TableField(value = "USED")
    @Length(max = 32, message = "used已经使用量不能超过32个字符")
    private String used;
    /**
     * 资源的使用率
     */
    @TableField(value = "USAGE_RATE")
    private Integer usageRate;


    public MachineHardDisk() {
        super();
    }

    /**
     * 主键-数据库字段:HARD_DISK_ID
     */
    public String getHardDiskId() {
        return hardDiskId;
    }

    /**
     * 主键-数据库字段:HARD_DISK_ID
     */
    public void setHardDiskId(String hardDiskId) {
        this.hardDiskId = hardDiskId;
    }


    /**
     * 归属服务器-数据库字段:MACHINE_ID
     */
    public String getMachineId() {
        return machineId;
    }

    /**
     * 归属服务器-数据库字段:MACHINE_ID
     */
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    /**
     * 盘符路径-数据库字段:DIR_NAME
     */
    public String getDirName() {
        return dirName;
    }

    /**
     * 盘符路径-数据库字段:DIR_NAME
     */
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    /**
     * 盘符类型-数据库字段:SYS_TYPE_NAME
     */
    public String getSysTypeName() {
        return sysTypeName;
    }

    /**
     * 盘符类型-数据库字段:SYS_TYPE_NAME
     */
    public void setSysTypeName(String sysTypeName) {
        this.sysTypeName = sysTypeName;
    }

    /**
     * 文件类型-数据库字段:TYPE_NAME
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 文件类型-数据库字段:TYPE_NAME
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 总大小-数据库字段:TOTAL
     */
    public String getTotal() {
        return total;
    }

    /**
     * 总大小-数据库字段:TOTAL
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * 剩余大小-数据库字段:FREE
     */
    public String getFree() {
        return free;
    }

    /**
     * 剩余大小-数据库字段:FREE
     */
    public void setFree(String free) {
        this.free = free;
    }

    /**
     * 已经使用量-数据库字段:USED
     */
    public String getUsed() {
        return used;
    }

    /**
     * 已经使用量-数据库字段:USED
     */
    public void setUsed(String used) {
        this.used = used;
    }

    /**
     * 资源的使用率-数据库字段:USAGE_RATE
     */
    public Integer getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(Integer usageRate) {
        this.usageRate = usageRate;
    }


}
