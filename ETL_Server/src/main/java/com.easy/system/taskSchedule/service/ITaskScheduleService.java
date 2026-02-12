package com.easy.system.taskSchedule.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.system.dict.entity.Dict;
import com.easy.system.taskSchedule.dto.TaskScheduleDTO;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.vo.TaskScheduleVo;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务调度-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  ITaskScheduleService extends IBaseService<TaskSchedule> {

	/**
	 * 手动执行一次任务
	 * @param taskSchedule
	 * @return
	 */
	public boolean manuallyOnceTaskSchedule(TaskSchedule taskSchedule);

	/**
	 * 启动定时任务
	 * @param taskSchedule
	 * @return
	 */
	public boolean startTaskSchedule(TaskSchedule taskSchedule);

	/**
	 *
	 * @param taskScheduleId
	 * @return
	 */
	public boolean stopTaskSchedule(String taskScheduleId);

	/**
	 * 添加定时任务
	 * @param taskSchedule
	 * @return
	 */
	public boolean addTaskSchedule(TaskSchedule taskSchedule);

	/**
	 * 取消定时任务
	 * @param taskScheduleId
	 * @return
	 */
	public boolean cancelTaskSchedule(String taskScheduleId);

	/**
	 * 停止任务
	 * @param taskScheduleId
	 * @return
	 */
	public boolean stopedTaskSchedule(String taskScheduleId);

	/**
	 * 开始执行任务
	 * @param taskScheduleId
	 * @return
	 */
	public boolean beginTaskSchedule(String taskScheduleId);

	/**
	   * 保存或修改Entity
	 * @param taskSchedule
	 */
	public boolean saveOrUpdateTaskSchedule(TaskSchedule taskSchedule);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<TaskScheduleVo> queryTaskSchedulePage(IPage<TaskScheduleVo> page, TaskScheduleDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<TaskScheduleVo> queryTaskScheduleAll(TaskScheduleDTO param);

	/**
	 * 开机启动任务
	 * @return
	 */
	boolean bootUpTaskSchedule();

	/**
	 * 起动系统级定时任务
	 */
	void systemTaskScheduleStart();
}
