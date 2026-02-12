package com.easy.etl.machine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * CPU信息-实体类
 *
 * @author 邵勇
 * @table TF_MACHINE_CPU
 * @time 2024-01-17
 */
@TableName(value = "TF_MACHINE_CPU", autoResultMap = true)
public class MachineCpu extends Model<MachineCpu> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "CPU_ID", type = IdType.ASSIGN_ID)
    private String cpuId;

    /**
     * 归属服务器
     */
    @TableField(value = "MACHINE_ID")
    @NotEmpty(message = "machineId归属服务器不能为空")
    @Length(max = 32, message = "machineId归属服务器不能超过32个字符")
    private String machineId;
    /**
     * 核心数
     */
    @TableField(value = "CPU_NUM")
    private Integer cpuNum;
    /**
     * CPU总的使用率
     */
    @TableField(value = "TOTAL")
    private Integer total;
    /**
     * CPU系统使用率
     */
    @TableField(value = "SYS")
    private Integer sys;
    /**
     * CPU用户使用率
     */
    @TableField(value = "USED")
    private Integer used;
    /**
     * CPU当前等待率
     */
    @TableField(value = "WAIT")
    private Integer wait;
    /**
     * CPU当前空闲率
     */
    @TableField(value = "FREE")
    private Integer free;


    public MachineCpu() {
        super();
    }

    /**
     * 主键-数据库字段:CPU_ID
     */
    public String getCpuId() {
        return cpuId;
    }

    /**
     * 主键-数据库字段:CPU_ID
     */
    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
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
     * 核心数-数据库字段:CPU_NUM
     */
    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * CPU总的使用率-数据库字段:TOTAL
     */
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * CPU系统使用率-数据库字段:SYS
     */
    public Integer getSys() {
        return sys;
    }

    public void setSys(Integer sys) {
        this.sys = sys;
    }

    /**
     * CPU用户使用率-数据库字段:USED
     */
    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    /**
     * CPU当前等待率-数据库字段:WAIT
     */
    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    /**
     * CPU当前空闲率-数据库字段:FREE
     */
    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }


}
