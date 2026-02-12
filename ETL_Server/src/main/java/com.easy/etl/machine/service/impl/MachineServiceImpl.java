package com.easy.etl.machine.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.machine.dto.MachineDTO;
import com.easy.etl.machine.entity.*;
import com.easy.etl.machine.mapper.*;
import com.easy.etl.machine.model.MachineInfo;
import com.easy.etl.machine.service.IMachineService;
import com.easy.etl.machine.vo.MachineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务器-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class MachineServiceImpl extends BaseServiceImpl<MachineMapper, Machine> implements IMachineService {

    @Autowired
    private MachineCpuMapper machineCpuMapper;
    @Autowired
    private MachineMemoryMapper machineMemoryMapper;
    @Autowired
    private MachineJvmMapper machineJvmMapper;
    @Autowired
    private MachineHardDiskMapper machineHardDiskMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMachineById(String machineId) {
        machineCpuMapper.deleteById(machineId);
        machineMemoryMapper.deleteById(machineId);
        machineJvmMapper.deleteById(machineId);
        machineHardDiskMapper.deleteById(machineId);
        baseMapper.deleteById(machineId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateMachine(MachineInfo machineInfo) {
        Machine machine =machineInfo.getMachine();

        MachineCpu machineCpu = machineInfo.getMachineCpu();
        machineCpuMapper.delete(new QueryWrapper<MachineCpu>().lambda().eq(MachineCpu::getMachineId, machineCpu.getMachineId()));
        machineCpuMapper.insert(machineCpu);

        MachineMemory machineMemory = machineInfo.getMachineMemory();
        machineMemoryMapper.delete(new QueryWrapper<MachineMemory>().lambda().eq(MachineMemory::getMachineId, machineMemory.getMachineId()));
        machineMemoryMapper.insert(machineMemory);

        MachineJvm machineJvm = machineInfo.getMachineJvm();
        machineJvmMapper.delete(new QueryWrapper<MachineJvm>().lambda().eq(MachineJvm::getMachineId, machineJvm.getMachineId()));
        machineJvmMapper.insert(machineJvm);

        machineHardDiskMapper.delete(new QueryWrapper<MachineHardDisk>().lambda().eq(MachineHardDisk::getMachineId, machine.getMachineId()));
        for (MachineHardDisk disk : machineInfo.getDisks()) {
            machineHardDiskMapper.insert(disk);
        }
        machine.setReportTime(new Date());

        if (machine.getIsMaster() == 1) {
            LambdaUpdateWrapper<Machine> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(Machine::getIsMaster, 0);
            baseMapper.update(null, updateWrapper);
        }
        return saveOrUpdate(machine);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<MachineVo> queryMachinePage(IPage<MachineVo> page, MachineDTO param) {
        return page.setRecords(baseMapper.queryMachinePage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<MachineVo> queryMachineAll(MachineDTO param) {
        return baseMapper.queryMachineAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public MachineVo queryMachineByCondition(MachineDTO param) {
        return baseMapper.queryMachineByCondition(param);
    }
}
