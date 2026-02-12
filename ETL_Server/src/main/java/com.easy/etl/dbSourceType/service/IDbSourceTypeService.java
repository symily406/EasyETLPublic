package com.easy.etl.dbSourceType.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.dbSourceType.dto.DbSourceTypeDTO;
import com.easy.etl.dbSourceType.entity.DbSourceType;
import com.easy.etl.dbSourceType.vo.DbSourceTypeVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 数据源类型-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IDbSourceTypeService extends IBaseService<DbSourceType> {

    //================代码生成器生成代码开始=================

    /**
     * 保存或修改Entity
     *
     * @param dbSourceType
     */
    public boolean saveOrUpdateDbSourceType(DbSourceType dbSourceType);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DbSourceTypeVo> queryDbSourceTypePage(IPage<DbSourceTypeVo> page, DbSourceTypeDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<DbSourceTypeVo> queryDbSourceTypeAll(DbSourceTypeDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    public DbSourceTypeVo queryDbSourceTypeByCondition(DbSourceTypeDTO param);
    //================代码生成器生成代码结束=================
}
