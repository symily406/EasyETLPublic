package com.easy.etl.machine.entity;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 服务器JVM-实体类
 *
 * @author 邵勇
 * @table TF_MACHINE_JVM
 * @time 2024-01-17
 */
@TableName(value = "TF_MACHINE_JVM", autoResultMap = true)
public class MachineJvm extends Model<MachineJvm> {


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "JVM_ID", type = IdType.ASSIGN_ID)
    private String jvmId;


    @TableField(value = "MACHINE_ID")
    private String machineId;

    @TableField(value = "TOTAL")
    private Integer total;

    @TableField(value = "MAX")
    private Integer max;

    @TableField(value = "FREE")
    private Integer free;

    @TableField(value = "HOME")
    private String home;


    public MachineJvm() {
        super();
    }


    public String getJvmId() {
        return jvmId;
    }


    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }



    public String getMachineId() {
        return machineId;
    }


    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }


    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }


    public String getHome() {
        return home;
    }


    public void setHome(String home) {
        this.home = home;
    }


}
