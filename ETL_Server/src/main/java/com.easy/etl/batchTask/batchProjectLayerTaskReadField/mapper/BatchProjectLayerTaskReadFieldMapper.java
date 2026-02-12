package com.easy.etl.batchTask.batchProjectLayerTaskReadField.mapper;


import java.util.List;

import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.entity.BatchProjectLayerTaskReadField;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 批处理分层任务来源字段-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface BatchProjectLayerTaskReadFieldMapper extends MapperDao<BatchProjectLayerTaskReadField> {
    /**
     *  根据条件查询
     * @param param
     * @return
     */
    List<BatchProjectLayerTaskReadFieldVo> queryBatchProjectLayerTaskReadFieldAll(@Param("param") BatchProjectLayerTaskReadFieldDTO param);

    /**
     *  根据条件查询单个
     * @param param
     * @return
     */
    BatchProjectLayerTaskReadFieldVo queryBatchProjectLayerTaskReadFieldByCondition(@Param("param")BatchProjectLayerTaskReadFieldDTO  param);
}
