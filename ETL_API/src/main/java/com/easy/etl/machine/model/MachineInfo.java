package com.easy.etl.machine.model;

import com.easy.etl.machine.entity.Machine;
import com.easy.etl.machine.entity.MachineCpu;
import com.easy.etl.machine.entity.MachineHardDisk;
import com.easy.etl.machine.entity.MachineJvm;
import com.easy.etl.machine.entity.MachineMemory;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.ext.kit.jackson.JsonKit;

import java.util.List;

public class MachineInfo {

    @JsonProperty("sys")
    private Machine machine;

    @JsonProperty("cpu")
    private MachineCpu machineCpu;

    @JsonProperty("jvm")
    private MachineJvm machineJvm;

    @JsonProperty("mem")
    private MachineMemory machineMemory;

    @JsonProperty("sysFiles")
    private List<MachineHardDisk> disks;

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public MachineCpu getMachineCpu() {
        return machineCpu;
    }

    public void setMachineCpu(MachineCpu machineCpu) {
        this.machineCpu = machineCpu;
    }

    public MachineJvm getMachineJvm() {
        return machineJvm;
    }

    public void setMachineJvm(MachineJvm machineJvm) {
        this.machineJvm = machineJvm;
    }

    public MachineMemory getMachineMemory() {
        return machineMemory;
    }

    public void setMachineMemory(MachineMemory machineMemory) {
        this.machineMemory = machineMemory;
    }

    public List<MachineHardDisk> getDisks() {
        return disks;
    }

    public void setDisks(List<MachineHardDisk> disks) {
        this.disks = disks;
    }
}
