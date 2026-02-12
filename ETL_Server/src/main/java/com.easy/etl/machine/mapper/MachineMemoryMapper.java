package com.easy.etl.machine.mapper;


import java.util.List;

import com.easy.etl.machine.entity.MachineMemory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 服务器内存-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface MachineMemoryMapper extends MapperDao<MachineMemory> {

}
