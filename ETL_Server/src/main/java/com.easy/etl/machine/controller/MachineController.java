package com.easy.etl.machine.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.core.controller.BaseController;
import com.easy.etl.machine.dto.MachineDTO;
import com.easy.etl.machine.entity.*;
import com.easy.etl.machine.service.*;
import com.easy.etl.machine.vo.MachineVo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.debug.DebugAnnotation;
import com.spring.boot.ext.plugin.mybatis.page.Condition;
import com.spring.boot.ext.plugin.mybatis.page.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 服务器-控制器
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Validated
@RestController
@RequestMapping(value = "/admin/etl/machine")
public class MachineController extends BaseController {

    @Autowired
    private IMachineService machineService;
    @Autowired
    private IMachineCpuService machineCpuService;
    @Autowired
    private IMachineJvmService machineJvmService;
    @Autowired
    private IMachineMemoryService machineMemoryService;
    @Autowired
    private IMachineHardDiskService machineHardDiskService;


    /**
     * 分页查询
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "服务器列表")
    @GetMapping(value = "/queryAll")
    public ApiResult<Dict> queryAll(MachineDTO param) {
        List<MachineVo> machines = machineService.queryMachineAll(param);
        List<MachineCpu> cpus = machineCpuService.list();
        List<MachineJvm> jvms = machineJvmService.list();
        List<MachineMemory> memorys = machineMemoryService.list();
        List<MachineHardDisk> disks = machineHardDiskService.list();

        for (MachineVo vo : machines) {
            vo.setCpu(cpus.stream().filter(v -> v.getMachineId().equals(vo.getMachineId())).findFirst().orElse(new MachineCpu()));
            vo.setMemory(memorys.stream().filter(v -> v.getMachineId().equals(vo.getMachineId())).findFirst().orElse(new MachineMemory()));
            vo.setJvm(jvms.stream().filter(v -> v.getMachineId().equals(vo.getMachineId())).findFirst().orElse(new MachineJvm()));
            vo.setDisks(disks.stream().filter(v -> v.getMachineId().equals(vo.getMachineId())).collect(Collectors.toList()));
        }
        List<MachineVo> webs = machines.stream().filter(v -> v.getServerType() == 1).collect(Collectors.toList());
        List<MachineVo> executes = machines.stream().filter(v -> v.getServerType() == 2).collect(Collectors.toList());
        return ApiResult.ok(new Dict().set("webs", webs).set("executes", executes));
    }

    /**
     * 删除服务器
     */
    @SaCheckLogin
    @DebugAnnotation(desc = "删除服务器")
    @GetMapping(value = "/deleteById")
    public ApiResult deleteById(@NotNull(message = "machineId不能为空") String machineId) {
        machineService.deleteMachineById(machineId);
        return ApiResult.ok();
    }
}
