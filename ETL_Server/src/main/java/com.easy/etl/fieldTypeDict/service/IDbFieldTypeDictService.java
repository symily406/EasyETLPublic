package com.easy.etl.fieldTypeDict.service;


import java.util.List;

import com.easy.etl.common.db.model.ColumnInfo;
import com.easy.etl.fieldTypeDict.dto.DbFieldTypeDictDTO;
import com.easy.etl.fieldTypeDict.entity.DbFieldTypeDict;
import com.easy.etl.fieldTypeDict.vo.DbFieldTypeDictVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 数据库字段类型映射-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  IDbFieldTypeDictService extends IBaseService<DbFieldTypeDict> {



	/**
	 * @param param
	 * @return
	 */
	List<ColumnInfo> queryReadToWriteColumn(DbFieldTypeDictDTO param);

}
