package com.easy.etl.dbSourceDatax.mapper;


import java.util.List;

import com.easy.etl.dbSourceDatax.dto.DbSourceDataxDTO;
import com.easy.etl.dbSourceDatax.entity.DbSourceDatax;
import com.easy.etl.dbSourceDatax.vo.DbSourceDataxVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 数据源datax模板-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface DbSourceDataxMapper extends MapperDao<DbSourceDatax> {

    //=============代码生成器生成代码开始==============

    /**
     * 分页
     *
     * @param param
     * @return
     */
    List<DbSourceDataxVo> queryDbSourceDataxPage(IPage<DbSourceDataxVo> page, @Param("param") DbSourceDataxDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<DbSourceDataxVo> queryDbSourceDataxAll(@Param("param") DbSourceDataxDTO param);

    /**
     * 根据条件查询单个
     *
     * @param param
     * @return
     */
    DbSourceDataxVo queryDbSourceDataxByCondition(@Param("param") DbSourceDataxDTO param);
    //=============代码生成器生成代码结束==============
}
