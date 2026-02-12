package com.easy.etl.fieldTypeDict.service.impl;
import java.util.List;

import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.fieldTypeDict.dto.DbFieldTypeDictDTO;
import com.easy.etl.fieldTypeDict.entity.DbFieldTypeDict;
import com.easy.etl.fieldTypeDict.mapper.DbFieldTypeDictMapper;
import com.easy.etl.fieldTypeDict.service.IDbFieldTypeDictService;
import com.easy.etl.fieldTypeDict.vo.DbFieldTypeDictVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 数据库字段类型映射-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class DbFieldTypeDictServiceImpl extends BaseServiceImpl<DbFieldTypeDictMapper, DbFieldTypeDict> implements IDbFieldTypeDictService {

	@Override
	public List<ColumnInfo> queryReadToWriteColumn(DbFieldTypeDictDTO param) {
		return baseMapper.queryReadToWriteColumn(param);
	}
}
