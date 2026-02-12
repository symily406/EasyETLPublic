package com.easy.etl.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.common.db.model.SqlTable;
import com.easy.etl.em.*;
import com.easy.etl.dbSource.dto.DbSourceDTO;
import com.easy.etl.dbSource.mapper.DbSourceMapper;
import com.easy.etl.dbSource.vo.DbSourceVo;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.mapper.RunTaskJobLogMapper;
import com.easy.etl.runTaskJob.mapper.RunTaskJobMapper;
import com.easy.etl.task.service.ITaskService;
import com.easy.etl.taskJob.dto.TaskJobDTO;
import com.easy.etl.taskJob.entity.TaskJob;
import com.easy.etl.taskJob.entity.TaskJobRelyOn;
import com.easy.etl.taskJob.mapper.TaskJobMapper;
import com.easy.etl.taskJob.mapper.TaskJobRelyOnMapper;
import com.easy.etl.taskJob.vo.TaskJobVo;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.spring.boot.ext.kit.DataMap;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private RunTaskJobMapper runTaskJobMapper;
    @Autowired
    private TaskJobRelyOnMapper taskJobRelyOnMapper;
    @Autowired
    private TaskJobMapper taskJobMapper;
    @Autowired
    private DbSourceMapper dbSourceMapper;

    @Autowired
    private RunTaskJobLogMapper runTaskJobLogMapper;

    @Override
    public void handleRunTaskJob() {
        List<RunTaskJob> list = runTaskJobMapper.selectList(new LambdaQueryWrapper<RunTaskJob>().in(RunTaskJob::getStatus, TaskStatusEnum.SUCCED.getStatus(), TaskStatusEnum.FAIL.getStatus()).eq(RunTaskJob::getIsHandle, HandleEnum.UNTREATED.getStatus()).eq(RunTaskJob::getTaskType, TaskTypeEnum.ROUTINE_TASK.getType()).eq(RunTaskJob::getAddType, TaskAddTypeEnum.ADD_TYPE_0.getType()).orderByAsc(RunTaskJob::getJobId));
        if (!ObjectUtils.isEmpty(list)) {

            runTaskJobMapper.update(null, new LambdaUpdateWrapper<RunTaskJob>().in(RunTaskJob::getJobId, list.stream().map(v -> v.getJobId()).collect(Collectors.toList())).set(RunTaskJob::getIsHandle, HandleEnum.PROCESSED.getStatus()));

            List<TaskJobRelyOn> relyOns = taskJobRelyOnMapper.selectList(new LambdaQueryWrapper<TaskJobRelyOn>().select(TaskJobRelyOn::getLazyId, TaskJobRelyOn::getJobId, TaskJobRelyOn::getLazyJobId, TaskJobRelyOn::getFailurePolicy).in(TaskJobRelyOn::getLazyJobId, list.stream().map(v -> v.getJobId()).collect(Collectors.toList())));
            for (RunTaskJob job : list) {
                long between = job.getEndTime().getTime() - job.getStartTime().getTime();
                taskJobMapper.update(null, new LambdaUpdateWrapper<TaskJob>().eq(TaskJob::getJobId, job.getJobId()).set(TaskJob::getStartTime, job.getStartTime()).set(TaskJob::getEndTime, job.getEndTime()).set(TaskJob::getStatus, job.getStatus()).set(TaskJob::getUsageTime, between));
                TaskJobRelyOn taskJobRelyOn = relyOns.stream().filter(v -> v.getLazyJobId().equals(job.getJobId())).findFirst().orElse(null);
                if (!ObjectUtils.isEmpty(taskJobRelyOn)) {
                    TaskStatusEnum taskStatusEnum = TaskStatusEnum.getTaskStatusEnum(job.getStatus());

                    if (taskStatusEnum == TaskStatusEnum.SUCCED) {
                        taskJobRelyOnMapper.update(null, new LambdaUpdateWrapper<TaskJobRelyOn>().eq(TaskJobRelyOn::getLazyJobId, job.getJobId()).set(TaskJobRelyOn::getStatus, job.getStatus()).set(TaskJobRelyOn::getLazyTaskEnable, LazyTaskEnableEnum.ENABLE.getStatus()).set(TaskJobRelyOn::getIsHandle, HandleEnum.UNTREATED.getStatus()));
                        continue;
                    }
                    FailurePolicyEnum failurePolicyEnum = FailurePolicyEnum.getFailurePolicyEnum(taskJobRelyOn.getFailurePolicy());
                    switch (failurePolicyEnum) {
                        case FAIL_CONTINUE:
                            taskJobRelyOnMapper.update(null, new LambdaUpdateWrapper<TaskJobRelyOn>().eq(TaskJobRelyOn::getLazyJobId, job.getJobId()).set(TaskJobRelyOn::getStatus, job.getStatus()).set(TaskJobRelyOn::getLazyTaskEnable, LazyTaskEnableEnum.ENABLE.getStatus()).set(TaskJobRelyOn::getIsHandle, HandleEnum.UNTREATED.getStatus()));
                            break;
                        case FAIL_END:
                            taskJobRelyOnMapper.update(null, new LambdaUpdateWrapper<TaskJobRelyOn>().eq(TaskJobRelyOn::getLazyJobId, job.getJobId()).set(TaskJobRelyOn::getStatus, job.getStatus()).set(TaskJobRelyOn::getIsHandle, HandleEnum.UNTREATED.getStatus()));
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void addRunTaskJob() {
        List<TaskJob> list = taskJobMapper.selectList(new LambdaQueryWrapper<TaskJob>().eq(TaskJob::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus()).le(TaskJob::getCorn, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
        if (!ObjectUtils.isEmpty(list)) {
            DbSourceDTO dbSourceDTO = new DbSourceDTO();
            dbSourceDTO.setUserLeval(100);
            dbSourceDTO.setUserDutyLeval(10);
            List<DbSourceVo> dbSourceVos = dbSourceMapper.queryDbSourceAll(dbSourceDTO);
            Map<String, String> dbMap = dbSourceVos.stream().collect(HashMap::new, (k, v) -> k.put(v.getDbSourceId(), v.getSourceTypeName()), HashMap::putAll);
            for (TaskJob taskJob : list) {

                RunTaskJob runTaskJob = new RunTaskJob();
                runTaskJob.setJobId(taskJob.getJobId());
                runTaskJob.setTaskId(taskJob.getTaskId());
                runTaskJob.setTaskType(TaskTypeEnum.ROUTINE_TASK.getType());
                runTaskJob.setStatus(TaskStatusEnum.TO_BE_ALLOCATED.getStatus());
                runTaskJob.setScriptContent(taskJob.getRunScript());
                runTaskJob.setWorkSpaceType(taskJob.getWorkSpaceType());
                runTaskJob.setTaskFolderId(taskJob.getTaskFolderId());
                runTaskJob.setProjectId(taskJob.getProjectId());
                runTaskJob.setDbSourceId(taskJob.getDbSourceId());
                runTaskJob.setNextRunTime(taskJob.getCorn());
                runTaskJob.setProjectWorkSpaceLayerRelId(taskJob.getProjectWorkSpaceLayerRelId());
                runTaskJob.setProjectWorkSpaceId(taskJob.getProjectWorkSpaceId());
                runTaskJob.setAddType(TaskAddTypeEnum.ADD_TYPE_0.getType());
                runTaskJob.setIsRetry(0);
                runTaskJob.setRunNum(0);
                runTaskJob.setDbType(dbMap.get(taskJob.getDbSourceId()));
                runTaskJob.setPreScriptContent(taskJob.getPreScript());

                if (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(taskJob.getWorkSpaceType())) == WorkSpaceTypeEnum.SQL || WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(taskJob.getWorkSpaceType())) == WorkSpaceTypeEnum.SPARK_SQL) {
                    SqlTable sqlTable = DbKit.parseSqlTables(taskJob.getRunScript(), runTaskJob.getDbType());
                    boolean hasResult = DbKit.sqlHasResult(sqlTable.getTables());
                    runTaskJob.setHasResult(hasResult == true ? 1 : 0);
                }
                runTaskJobMapper.insert(runTaskJob);
                if (WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(taskJob.getWorkSpaceType())) == WorkSpaceTypeEnum.SQL || WorkSpaceTypeEnum.getWorkSpaceTypeEnum(Integer.parseInt(taskJob.getWorkSpaceType())) == WorkSpaceTypeEnum.SPARK_SQL) {
                    RunTaskJobLog log = new RunTaskJobLog();
                    log.setJobId(runTaskJob.getJobId());
                    log.setTaskId(runTaskJob.getTaskId());
                    log.setStatus(runTaskJob.getStatus());
                    log.setLogContent("执行的sql:" + runTaskJob.getScriptContent());
                    runTaskJobLogMapper.insert(log);
                }
                RunTaskJobLog log = new RunTaskJobLog();
                log.setJobId(runTaskJob.getJobId());
                log.setTaskId(runTaskJob.getTaskId());
                log.setStatus(runTaskJob.getStatus());
                log.setLogContent("等待任务分配...");
                runTaskJobLogMapper.insert(log);
            }
            taskJobMapper.update(null, new LambdaUpdateWrapper<TaskJob>().in(TaskJob::getJobId, list.stream().map(v -> v.getJobId()).collect(Collectors.toList())).set(TaskJob::getStatus, TaskStatusEnum.TO_BE_EXECUTED.getStatus()));
        }
    }

    @Override
    public void handleTaskJob() {
        List<TaskJobRelyOn> relyOns = taskJobRelyOnMapper.selectList(new LambdaQueryWrapper<TaskJobRelyOn>().eq(TaskJobRelyOn::getIsHandle, HandleEnum.UNTREATED.getStatus()));
        if (!ObjectUtils.isEmpty(relyOns)) {
            taskJobRelyOnMapper.update(null, new LambdaUpdateWrapper<TaskJobRelyOn>().in(TaskJobRelyOn::getLazyId, relyOns.stream().map(v -> v.getLazyId()).collect(Collectors.toList())).set(TaskJobRelyOn::getIsHandle, HandleEnum.PROCESSED.getStatus()));
            TaskJobDTO dto = new TaskJobDTO();
            dto.setDay(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            dto.setLazyTaskEnable(LazyTaskEnableEnum.ENABLE.getStatus());
            dto.setJobIds(relyOns.stream().map(v -> v.getJobId()).collect(Collectors.toList()));
            List<TaskJobVo> taskJobs = taskJobMapper.queryJobDisableNum(dto);
            if (!ObjectUtils.isEmpty(taskJobs)) {
                for (TaskJobVo vo : taskJobs) {
                    if (vo.getDisableNum() > 0) {
                        continue;
                    }
                    taskJobMapper.update(null, new LambdaUpdateWrapper<TaskJob>().eq(TaskJob::getJobId, vo.getJobId()).set(TaskJob::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus()));
                }
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(TaskSchedule taskSchedule) {
        DataMap param = taskSchedule.getParam();
        if (param.getInt("handleRunTaskJob", 0) == 1) {
            handleRunTaskJob();
        }
        if (param.getInt("handleTaskJob", 0) == 1) {
            handleTaskJob();
        }
        if (param.getInt("addRunTaskJob", 0) == 1) {
            addRunTaskJob();
        }
    }
}
