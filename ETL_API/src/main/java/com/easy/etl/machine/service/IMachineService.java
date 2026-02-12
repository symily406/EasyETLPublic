package com.easy.etl.machine.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.machine.dto.MachineDTO;
import com.easy.etl.machine.entity.Machine;
import com.easy.etl.machine.model.MachineInfo;
import com.easy.etl.machine.vo.MachineVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

import java.util.List;

/**
 * 服务器-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IMachineService extends IBaseService<Machine> {

    /**
     * 删除服务器
     *
     * @param machineId
     */
    public void deleteMachineById(String machineId);


    //================代码生成器生成代码开始=================

    /**
     * 保存或修改Entity
     *
     * @param machineInfo
     */
    public boolean saveOrUpdateMachine(MachineInfo machineInfo);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<MachineVo> queryMachinePage(IPage<MachineVo> page, MachineDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<MachineVo> queryMachineAll(MachineDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    public MachineVo queryMachineByCondition(MachineDTO param);
    //================代码生成器生成代码结束=================
}
