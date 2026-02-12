package com.easy.etl.taskJob.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.taskJob.dto.TaskJobDTO;
import com.easy.etl.taskJob.entity.TaskJob;
import com.easy.etl.taskJob.vo.TaskJobVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 待运行任务-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  ITaskJobService extends IBaseService<TaskJob> {

	/**
	 * 任务依懒处理线程
	 */
	public void handleTaskJobRelyOn();

	/**
	 * 任务提交
	 * @param projectTask
	 * @param script
	 * @param parameters
	 * @param startTime
	 * @param endTime
	 * @param lazyVos
	 * @return
	 */
	public boolean submitTaskJob(ProjectTask projectTask, String script, Map<String,String> parameters, Date startTime, Date endTime,List<ProjectTaskLazyVo>  lazyVos);

	//================代码生成器生成代码开始=================
	/**
	   * 保存或修改Entity
	 */
	public boolean saveOrUpdateTaskJob(ProjectTask projectTask, String script, Map<String,String> parameters, Date startTime, Date endTime,List<ProjectTaskLazyVo>  lazyVos,boolean isSubmit);

	/**
	   *分页查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<TaskJobVo> queryTaskJobPage(IPage<TaskJobVo> page, TaskJobDTO param);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<TaskJobVo> queryTaskJobAll(TaskJobDTO param);

}
