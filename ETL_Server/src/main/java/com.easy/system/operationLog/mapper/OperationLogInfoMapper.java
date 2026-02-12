package com.easy.system.operationLog.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.operationLog.entity.OperationLogInfo;

/**
 * 操作日志详情-接口
 * @author
 * @time
 */
@Mapper
public interface OperationLogInfoMapper extends MapperDao<OperationLogInfo> {

}
