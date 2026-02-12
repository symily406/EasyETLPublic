package com.easy.etl.machine.vo;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.easy.etl.machine.entity.*;

import java.util.Date;
import java.util.List;

/**
*  服务器-视图实体类
 * @author 邵勇
 * @time 2024-01-17
 */
public class MachineVo extends Machine {

    private int status;

    private MachineCpu cpu;

    private MachineJvm jvm;

    private MachineMemory memory;

    private List<MachineHardDisk> disks;

    public MachineCpu getCpu() {
        return cpu;
    }

    public void setCpu(MachineCpu cpu) {
        this.cpu = cpu;
    }

    public MachineJvm getJvm() {
        return jvm;
    }

    public void setJvm(MachineJvm jvm) {
        this.jvm = jvm;
    }

    public MachineMemory getMemory() {
        return memory;
    }

    public void setMemory(MachineMemory memory) {
        this.memory = memory;
    }

    public List<MachineHardDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<MachineHardDisk> disks) {
        this.disks = disks;
    }

    public int getStatus() {
        long betweenDay = DateUtil.between(getReportTime(), new Date(), DateUnit.MINUTE);
        if (betweenDay>5){
            return 0;
        }
        return 1;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
