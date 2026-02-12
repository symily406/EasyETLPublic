package com.easy.etl.machine.mapper;


import java.util.List;

import com.easy.etl.machine.dto.MachineDTO;
import com.easy.etl.machine.entity.Machine;
import com.easy.etl.machine.vo.MachineVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 服务器-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface MachineMapper extends MapperDao<Machine> {

    //=============代码生成器生成代码开始==============

    /**
     * 分页
     *
     * @param param
     * @return
     */
    List<MachineVo> queryMachinePage(IPage<MachineVo> page, @Param("param") MachineDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<MachineVo> queryMachineAll(@Param("param") MachineDTO param);

    /**
     * 根据条件查询单个
     *
     * @param param
     * @return
     */
    MachineVo queryMachineByCondition(@Param("param") MachineDTO param);
    //=============代码生成器生成代码结束==============
}
