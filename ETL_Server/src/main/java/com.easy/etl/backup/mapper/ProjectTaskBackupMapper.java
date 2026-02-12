package com.easy.etl.backup.mapper;


import java.util.List;

import com.easy.etl.backup.dto.ProjectTaskBackupDTO;
import com.easy.etl.backup.entity.ProjectTaskBackup;
import com.easy.etl.backup.vo.ProjectTaskBackupVo;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.dto.BatchProjectLayerTaskFieldMapDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskFieldMap.vo.BatchProjectLayerTaskFieldMapVo;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.dto.BatchProjectLayerTaskReadFieldDTO;
import com.easy.etl.batchTask.batchProjectLayerTaskReadField.vo.BatchProjectLayerTaskReadFieldVo;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.projectTask.vo.ProjectTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 项目任务备份-接口
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface ProjectTaskBackupMapper extends MapperDao<ProjectTaskBackup> {

	List<ProjectTaskBackupVo> queryDeleteProjectTaskBackup(@Param("taskId") String taskId);

	/**
	  *  根据条件查询
	 * @param param
	 * @return
	 */
	List<ProjectTaskBackupVo> queryProjectTaskBackupAll(@Param("param") ProjectTaskBackupDTO param);

	/**
	 *   分页
	 * @param param
	 * @return
	 */
	List<ProjectTaskLazyVo> queryProjectTaskLazyBackupPage(IPage<ProjectTaskLazyVo> page, @Param("param") ProjectTaskLazyDTO param);

	/**
	 *  根据条件查询
	 * @param param
	 * @return
	 */
	List<BatchProjectLayerTaskReadFieldVo> queryBatchProjectLayerTaskReadFieldBakeupAll(@Param("param") BatchProjectLayerTaskReadFieldDTO param);

	List<BatchProjectLayerTaskFieldMapVo> queryBatchProjectLayerTaskFieldMapBackupAll(@Param("param") BatchProjectLayerTaskFieldMapDTO param);

	ProjectTaskVo queryProjectTaskBackupById(String taskId);
}
