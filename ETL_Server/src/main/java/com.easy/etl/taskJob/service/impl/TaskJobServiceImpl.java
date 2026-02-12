package com.easy.etl.taskJob.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.func.Func;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.db.em.DbEnum;
import com.easy.etl.common.db.kit.HiveKit;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.datax.model.PreScriptContent;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.em.TaskSubmitEnum;
import com.easy.etl.em.WorkSpaceTypeEnum;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.mapper.BatchProjectLayerTaskWriteMapper;
import com.easy.etl.batchTask.batchProjectLayerTaskWrite.vo.BatchProjectLayerTaskWriteVo;
import com.easy.etl.dbSource.entity.DbSource;
import com.easy.etl.dbSource.mapper.DbSourceMapper;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.mapper.RunTaskJobMapper;
import com.easy.etl.runTaskJob.vo.RunTaskJobVo;
import com.easy.etl.taskJob.dto.TaskJobDTO;
import com.easy.etl.taskJob.entity.TaskJob;
import com.easy.etl.taskJob.entity.TaskJobRelyOn;
import com.easy.etl.taskJob.mapper.TaskJobMapper;
import com.easy.etl.taskJob.mapper.TaskJobRelyOnMapper;
import com.easy.etl.taskJob.service.ITaskJobService;
import com.easy.etl.taskJob.vo.TaskJobVo;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * 待运行任务-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class TaskJobServiceImpl extends BaseServiceImpl<TaskJobMapper, TaskJob> implements ITaskJobService {
    @Resource
    private TaskJobRelyOnMapper taskJobRelyOnMapper;

    @Resource
    private RunTaskJobMapper runTaskJobMapper;

    @Autowired
    private BatchProjectLayerTaskWriteMapper batchProjectLayerTaskWriteMapper;
    @Autowired
    private ProjectTaskMapper projectTaskMapper;


    @Override
    public void handleTaskJobRelyOn() {

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submitTaskJob(ProjectTask projectTask, String script, Map<String, String> parameters, Date startTime, Date endTime, List<ProjectTaskLazyVo> lazyVos) {
        projectTaskMapper.update(null, new LambdaUpdateWrapper<ProjectTask>()
                .eq(ProjectTask::getTaskId, projectTask.getTaskId())
                .set(ProjectTask::getIsSubmit, TaskSubmitEnum.SUBMIT.getStatus())
        );
        return saveOrUpdateTaskJob(projectTask, script, parameters, startTime, endTime, lazyVos, true);
    }

    /**
     * 保存或修改Entity
     */
    @Override
    public boolean saveOrUpdateTaskJob(ProjectTask projectTask, String script, Map<String, String> parameters, Date startTime, Date endTime, List<ProjectTaskLazyVo> lazyVos, boolean isSubmit) {
        String runScript = Expression.parse(script, parameters);
        //开启定时任务
        List<Date> cornTimes = CronCalculator.getAllRunTimes(projectTask.getCorn(), startTime, endTime);
        //当前任务没有运行时间
        if (ObjectUtils.isEmpty(cornTimes)) {
            return true;
        }
        if (!isSubmit) {
            taskJobRelyOnMapper.delete(new LambdaUpdateWrapper<TaskJobRelyOn>()
                    .eq(TaskJobRelyOn::getStatus, TaskStatusEnum.PARENT_RUN.getStatus())
                    .eq(TaskJobRelyOn::getTaksId, projectTask.getTaskId()));
        }
        if (projectTask.getCornStatus() == 1) {
            //清除未运行的任务
            baseMapper.delete(new LambdaUpdateWrapper<TaskJob>()
                    .eq(TaskJob::getTaskId, projectTask.getTaskId())
                    .in(TaskJob::getStatus, Arrays.asList(TaskStatusEnum.TO_BE_ALLOCATED.getStatus(), TaskStatusEnum.PARENT_RUN.getStatus()))
            );
            //清除任务依懒
            taskJobRelyOnMapper.delete(
                    new LambdaUpdateWrapper<TaskJobRelyOn>()
                            .eq(TaskJobRelyOn::getTaksId, projectTask.getTaskId())
                            .eq(TaskJobRelyOn::getStatus,
                                    TaskStatusEnum.PARENT_RUN.getStatus()
                            )
            );

            //如果是同步任务
            BatchProjectLayerTaskWriteVo write = null;
            if (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(projectTask.getWorkSpaceType())) == WorkSpaceTypeEnum.SYNC) {
                write = batchProjectLayerTaskWriteMapper.queryBatchProjectLayerTaskWrite(projectTask.getTaskId());
            }

            for (Date runTime : cornTimes) {
                String corn = DateFormatUtils.format(runTime, "yyyy-MM-dd HH:mm:ss");
                String jobId = String.valueOf(runTime.getTime() + Long.parseLong(projectTask.getTaskId()));
                TaskStatusEnum taskStatusEnum = TaskStatusEnum.TO_BE_ALLOCATED;
                if (!isSubmit) {
                    //处理依懒
                    for (ProjectTaskLazyVo vo : lazyVos) {
                        Date lazyDate = CronCalculator.queryRecentDate(vo.getRunTimes(), runTime);
                        if (lazyDate == null) {
                            continue;
                        }
                        String lazyCorn = DateFormatUtils.format(lazyDate, "yyyy-MM-dd HH:mm:ss");
                        String lazyJobId = String.valueOf(lazyDate.getTime() + Long.parseLong(vo.getLazyTaskId()));
                        TaskJobRelyOn taskJobRelyOn = new TaskJobRelyOn();
                        taskJobRelyOn.setJobId(jobId);
                        taskJobRelyOn.setTaksId(projectTask.getTaskId());
                        taskJobRelyOn.setLazyJobId(lazyJobId);
                        taskJobRelyOn.setLazyTaskId(vo.getLazyTaskId());
                        taskJobRelyOn.setLazyJobCorn(lazyCorn);
                        taskJobRelyOn.setStatus(TaskStatusEnum.PARENT_RUN.getStatus());
                        taskJobRelyOnMapper.insert(taskJobRelyOn);
                        taskStatusEnum = TaskStatusEnum.PARENT_RUN;
                    }
                }

                TaskJob taskJob = new TaskJob();
                taskJob.setJobId(jobId);
                taskJob.setTaskId(projectTask.getTaskId());
                taskJob.setProjectId(projectTask.getProjectId());
                taskJob.setProjectWorkSpaceLayerRelId(projectTask.getProjectWorkSpaceLayerRelId());
                taskJob.setProjectWorkSpaceId(projectTask.getProjectWorkSpaceId());
                taskJob.setWorkSpaceType(projectTask.getWorkSpaceType());
                taskJob.setTaskFolderId(projectTask.getTaskFolderId());
                taskJob.setDbSourceId(projectTask.getDbSourceId());
                taskJob.setCornStatus(projectTask.getCornStatus());
                taskJob.setStartDate(projectTask.getStartDate());
                taskJob.setEndDate(projectTask.getEndDate());
                taskJob.setCorn(runTime);
                taskJob.setUsageTime(0l);
                taskJob.setRunScript(Expression.dynamicExpression(runScript, corn));

                if (!ObjectUtils.isEmpty(write) && StringUtils.isNotBlank(write.getPt())) {
                    PreScriptContent preScriptContent = new PreScriptContent();
                    preScriptContent.setDbSourceId(write.getDbSourceId());
                    preScriptContent.setJdbcUrl(write.getJdbcUrl());
                    preScriptContent.setJdbcDrive(write.getJdbcDrive());
                    preScriptContent.setUserName(write.getUserName());
                    preScriptContent.setPassword(write.getPassword());
                    preScriptContent.setScript(Expression.parse(HiveKit.createPtSql(write.getDbCatalog(), write.getDbSchema(), write.getTableName(), write.getPt()), parameters));
                    taskJob.setPreScript(JsonKit.toJsonString(preScriptContent));
                }
                taskJob.setStatus(taskStatusEnum.getStatus());
                if (baseMapper.selectCount(new LambdaUpdateWrapper<TaskJob>().eq(TaskJob::getJobId, jobId)) > 0) {
                    baseMapper.updateById(taskJob);
                } else {
                    baseMapper.insert(taskJob);
                }
            }
            return true;
        }
        //将未运行的定时任务关闭并设为停止
        LambdaUpdateWrapper<TaskJob> taskJobLambdaUpdateWrapper = new LambdaUpdateWrapper<TaskJob>();
        taskJobLambdaUpdateWrapper.eq(TaskJob::getTaskId, projectTask.getTaskId());
        taskJobLambdaUpdateWrapper.eq(TaskJob::getStatus, TaskStatusEnum.PARENT_RUN.getStatus());
        taskJobLambdaUpdateWrapper.set(TaskJob::getCornStatus, 0);
        taskJobLambdaUpdateWrapper.set(TaskJob::getStatus, TaskStatusEnum.TASK_STOP.getStatus());
        baseMapper.update(null, taskJobLambdaUpdateWrapper);
        return true;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<TaskJobVo> queryTaskJobPage(IPage<TaskJobVo> page, TaskJobDTO param) {
        return page.setRecords(baseMapper.queryTaskJobPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<TaskJobVo> queryTaskJobAll(TaskJobDTO param) {
        return baseMapper.queryTaskJobAll(param);
    }

}
