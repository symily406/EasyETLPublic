package com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.mapper;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.entity.BatchProjectLayerTaskFieldMap;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 批处理分层字段映射-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskFieldMapMapper extends MapperDao<BatchProjectLayerTaskFieldMap> {
    /**
     * 根据条件查询
     * @param param
     * @return
     */
    List<BatchProjectLayerTaskFieldMapVo> queryBatchProjectLayerTaskFieldMapAll(@Param("param") BatchProjectLayerTaskFieldMapDTO param);


}
