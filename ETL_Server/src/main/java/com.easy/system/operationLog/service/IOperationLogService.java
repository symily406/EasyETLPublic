package com.easy.system.operationLog.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.reflect.MethodSignature;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;
import com.easy.system.operationLog.dto.OperationLogDTO;
import com.easy.system.operationLog.entity.OperationLog;
import com.easy.system.operationLog.vo.OperationLogVo;

/**
 * 操作日志-服务类
 *
 * @author
 * @time
 */
public interface IOperationLogService extends IBaseService<OperationLog> {


	/**
	 * 批量删除-真删
	 * @param ids
	 * @return
	 */
	public boolean batchDelete(List<String> ids);


	// ================代码生成器生成代码开始=================
	/**
	 * 保存操作日志
	 * @param request
	 * @param methodSignature
	 * @param params
	 * @param result
	 * @param resultLength
	 * @param runTimer
	 * @return
	 */
	public boolean saveOrUpdateOperationLog(HttpServletRequest request, MethodSignature methodSignature, String params,String result,int resultLength,Long runTimer);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<OperationLogVo> queryOperationLogPage(IPage<OperationLogVo> page, OperationLogDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	List<OperationLogVo> queryOperationLogAll(OperationLogDTO param);

	/**
	 * 根据条件查询
	 *
	 * @param param
	 * @return
	 */
	public OperationLogVo queryOperationLogByCondition(OperationLogDTO param);
	// ================代码生成器生成代码结束=================
}
