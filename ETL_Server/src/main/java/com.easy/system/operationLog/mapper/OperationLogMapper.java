package com.easy.system.operationLog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import com.easy.system.operationLog.dto.OperationLogDTO;
import com.easy.system.operationLog.entity.OperationLog;
import com.easy.system.operationLog.vo.OperationLogVo;

/**
 * 操作日志-接口
 * @author
 * @time
 */
@Mapper
public interface OperationLogMapper extends MapperDao<OperationLog> {

	// =============代码生成器生成代码开始==============
	/**
	 * 分页
	 *
	 * @param param
	 * @return
	 */
	List<OperationLogVo> queryOperationLogPage(IPage<OperationLogVo> page, @Param("param") OperationLogDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<OperationLogVo> queryOperationLogAll(@Param("param") OperationLogDTO param);

	/**
	 * 根据条件查询单个
	 *
	 * @param param
	 * @return
	 */
	OperationLogVo queryOperationLogByCondition(@Param("param") OperationLogDTO param);
	// =============代码生成器生成代码结束==============
}
