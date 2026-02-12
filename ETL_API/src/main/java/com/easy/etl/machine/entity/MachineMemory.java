package com.easy.etl.machine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 服务器内存-实体类
 *
 * @author 邵勇
 * @table TF_MACHINE_MEMORY
 * @time 2024-01-17
 */
@TableName(value = "TF_MACHINE_MEMORY", autoResultMap = true)
public class MachineMemory extends Model<MachineMemory> {

    /**
     *
     */
    private static final long serialVersionUID = 1492831266398250588L;

    /**
     * 主键
     */
    @TableId(value = "MEMORY_ID", type = IdType.ASSIGN_ID)
    private String memoryId;

    /**
     * 归属服务器
     */
    @TableField(value = "MACHINE_ID")
    @NotEmpty(message = "machineId归属服务器不能为空")
    @Length(max = 32, message = "machineId归属服务器不能超过32个字符")
    private String machineId;
    /**
     * 总内存
     */
    @TableField(value = "TOTAL")
    private Integer total;
    /**
     * 已用内存
     */
    @TableField(value = "USED")
    private Integer used;
    /**
     * 剩余内存
     */
    @TableField(value = "FREE")
    private Integer free;


    public MachineMemory() {
        super();
    }

    /**
     * 主键-数据库字段:MEMORY_ID
     */
    public String getMemoryId() {
        return memoryId;
    }

    /**
     * 主键-数据库字段:MEMORY_ID
     */
    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
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
     * 总内存-数据库字段:TOTAL
     */
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 已用内存-数据库字段:USED
     */
    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    /**
     * 剩余内存-数据库字段:FREE
     */
    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }


}
