package com.easy.system.taskSchedule.service.impl;
import java.util.List;

import com.easy.system.taskSchedule.dto.TaskScheduleLogDTO;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.service.ITaskScheduleLogService;
import com.easy.system.taskSchedule.vo.TaskScheduleLogVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 任务调度日志-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service("taskScheduleLogService")
public class TaskScheduleLogServiceImpl extends BaseServiceImpl<TaskScheduleLogMapper, TaskScheduleLog> implements ITaskScheduleLogService {

	/**
	     * 保存或修改Entity
	 */
	@Override
	public boolean saveOrUpdateTaskScheduleLog(TaskScheduleLog taskScheduleLog) {
		return saveOrUpdate(taskScheduleLog);
	}

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	@Override
	public IPage<TaskScheduleLogVo> queryTaskScheduleLogPage(IPage<TaskScheduleLogVo> page, TaskScheduleLogDTO param) {
		return page.setRecords(baseMapper.queryTaskScheduleLogPage(page, param));
	}

}
