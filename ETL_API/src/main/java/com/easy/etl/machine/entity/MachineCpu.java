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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "CPU_ID", type = IdType.ASSIGN_ID)
    private String cpuId;


    @TableField(value = "MACHINE_ID")
    private String machineId;

    @TableField(value = "CPU_NUM")
    private Integer cpuNum;

    @TableField(value = "TOTAL")
    private Integer total;

    @TableField(value = "SYS")
    private Integer sys;

    @TableField(value = "USED")
    private Integer used;

    @TableField(value = "WAIT")
    private Integer wait;

    @TableField(value = "FREE")
    private Integer free;


    public MachineCpu() {
        super();
    }


    public String getCpuId() {
        return cpuId;
    }


    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
    }



    public String getMachineId() {
        return machineId;
    }


    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }


    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
        this.cpuNum = cpuNum;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public Integer getSys() {
        return sys;
    }

    public void setSys(Integer sys) {
        this.sys = sys;
    }


    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }


    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }


    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }


}
