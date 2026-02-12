package com.easy.system.taskSchedule.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.system.taskSchedule.dto.TaskScheduleLogDTO;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.easy.system.taskSchedule.vo.TaskScheduleLogVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务调度日志-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  ITaskScheduleLogService extends IBaseService<TaskScheduleLog> {

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 * @param taskScheduleLog
	 */
	public boolean saveOrUpdateTaskScheduleLog(TaskScheduleLog taskScheduleLog);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<TaskScheduleLogVo> queryTaskScheduleLogPage(IPage<TaskScheduleLogVo> page, TaskScheduleLogDTO param);
}
