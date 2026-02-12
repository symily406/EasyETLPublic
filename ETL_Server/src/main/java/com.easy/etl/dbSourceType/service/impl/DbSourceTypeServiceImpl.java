package com.easy.etl.dbSourceType.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.dbSourceType.dto.DbSourceTypeDTO;
import com.easy.etl.dbSourceType.entity.DbSourceType;
import com.easy.etl.dbSourceType.mapper.DbSourceTypeMapper;
import com.easy.etl.dbSourceType.service.IDbSourceTypeService;
import com.easy.etl.dbSourceType.vo.DbSourceTypeVo;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源类型-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class DbSourceTypeServiceImpl extends BaseServiceImpl<DbSourceTypeMapper, DbSourceType> implements IDbSourceTypeService {

    /**
     * 保存或修改Entity
     */
    @Override
    public boolean saveOrUpdateDbSourceType(DbSourceType dbSourceType) {
        return saveOrUpdate(dbSourceType);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<DbSourceTypeVo> queryDbSourceTypePage(IPage<DbSourceTypeVo> page, DbSourceTypeDTO param) {
        return page.setRecords(baseMapper.queryDbSourceTypePage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<DbSourceTypeVo> queryDbSourceTypeAll(DbSourceTypeDTO param) {
        return baseMapper.queryDbSourceTypeAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public DbSourceTypeVo queryDbSourceTypeByCondition(DbSourceTypeDTO param) {
        return baseMapper.queryDbSourceTypeByCondition(param);
    }
}
