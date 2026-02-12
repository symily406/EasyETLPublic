package com.easy.etl.fieldTypeDict.mapper;


import java.util.List;

import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.fieldTypeDict.dto.DbFieldTypeDictDTO;
import com.easy.etl.fieldTypeDict.entity.DbFieldTypeDict;
import com.easy.etl.fieldTypeDict.vo.DbFieldTypeDictVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 数据库字段类型映射-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface DbFieldTypeDictMapper extends MapperDao<DbFieldTypeDict> {



    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<ColumnInfo> queryReadToWriteColumn(@Param("param") DbFieldTypeDictDTO param);


}
