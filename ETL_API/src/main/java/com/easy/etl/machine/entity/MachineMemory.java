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


    private static final long serialVersionUID = 1492831266398250588L;


    @TableId(value = "MEMORY_ID", type = IdType.ASSIGN_ID)
    private String memoryId;


    @TableField(value = "MACHINE_ID")
    private String machineId;

    @TableField(value = "TOTAL")
    private Integer total;

    @TableField(value = "USED")
    private Integer used;

    @TableField(value = "FREE")
    private Integer free;


    public MachineMemory() {
        super();
    }


    public String getMemoryId() {
        return memoryId;
    }


    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
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


    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }


    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }


}
