package com.easy.etl.backup.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.backup.dto.ProjectTaskBackupDTO;
import com.easy.etl.backup.entity.*;
import com.easy.etl.backup.vo.ProjectTaskBackupVo;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.entity.BatchProjectLayerTaskWrite;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.entity.BatchProjectLayerTaskWriteField;
import com.easy.etl.batchTask.batchProjectLayerTaskWriteField.vo.BatchProjectLayerTaskWriteFieldVo;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTaskSchedue;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 项目任务备份-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
public interface  ITaskBackupService {


	boolean rollBack(String taskId);

	/**
	 * 同步任务备份
	 */
	void taskBackup(String taskId,String remarks);

	/**
	 * 删除任务备份
	 * @param taskId
	 */
	void  deleteLastTaskBackup(String taskId);


	/**
	 *
	 * @return
	 */
	List<ProjectTaskBackupVo> backups(ProjectTaskBackupDTO dto);

	public List<ProjectTaskParameterBackup> queryParametersBackupByTaskId(ProjectTaskDTO param);

	ProjectTaskVo queryProjectTaskBackupById(String taskId);

	ProjectTaskSchedueBackup queryProjectTaskSchedueBackupById(String taskId);

	public IPage<ProjectTaskLazyVo> queryProjectTaskLazyBackupPage(IPage<ProjectTaskLazyVo> page, ProjectTaskLazyDTO param);

	BatchProjectLayerTaskWriteBackup queryBatchProjectLayerTaskWriteBakeByTaskId(String taskId);

	public List<BatchProjectLayerTaskReadFieldVo> queryBatchProjectLayerTaskReadFieldBakeAll(BatchProjectLayerTaskReadFieldDTO param);

	List<BatchProjectLayerTaskWriteFieldVo> queryBatchProjectLayerTaskWriteFieldBackup(String taskId);

	/**
	 * 根据条件查询
	 * @param param
	 * @return
	 */
	List<BatchProjectLayerTaskFieldMapVo> queryBatchProjectLayerTaskFieldMapBackupAll(BatchProjectLayerTaskFieldMapDTO param);

	BatchProjectLayerTaskChannelBackup queryBatchProjectLayerTaskChannelBackupByTaskId(String taskId);

	BatchProjectLayerTaskReadBackup getBatchTaskReadByTaskId(String taskId);

	ProjectTaskScriptBackup queryProjectTaskScriptBackupById(String taskId);

	ProjectTaskFlowBackup queryProjectTaskFlowBackupById(String taskId);

	ProjectTaskEvnBackup queryTaskEvnById(String taskId);
}
