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

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "JVM_ID", type = IdType.ASSIGN_ID)
    private String jvmId;

    /**
     * 归属服务器
     */
    @TableField(value = "MACHINE_ID")
    @NotEmpty(message = "machineId归属服务器不能为空")
    @Length(max = 32, message = "machineId归属服务器不能超过32个字符")
    private String machineId;
    /**
     * 当前JVM占用的内存总数(M)
     */
    @TableField(value = "TOTAL")
    private Integer total;
    /**
     * JVM最大可用内存总数(M)
     */
    @TableField(value = "MAX")
    private Integer max;
    /**
     * JVM空闲内存(M)
     */
    @TableField(value = "FREE")
    private Integer free;
    /**
     * JDK路径
     */
    @TableField(value = "HOME")
    @Length(max = 256, message = "homeJDK路径不能超过256个字符")
    private String home;


    public MachineJvm() {
        super();
    }

    /**
     * 主键-数据库字段:JVM_ID
     */
    public String getJvmId() {
        return jvmId;
    }

    /**
     * 主键-数据库字段:JVM_ID
     */
    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
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
     * 当前JVM占用的内存总数(M)-数据库字段:TOTAL
     */
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * JVM最大可用内存总数(M)-数据库字段:MAX
     */
    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * JVM空闲内存(M)-数据库字段:FREE
     */
    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    /**
     * JDK路径-数据库字段:HOME
     */
    public String getHome() {
        return home;
    }

    /**
     * JDK路径-数据库字段:HOME
     */
    public void setHome(String home) {
        this.home = home;
    }


}
