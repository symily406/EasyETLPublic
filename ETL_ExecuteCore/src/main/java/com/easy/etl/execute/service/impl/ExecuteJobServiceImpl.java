package com.easy.etl.execute.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.init.Config;
import com.easy.etl.em.MonitorEnum;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.execute.entity.ExecuteJob;
import com.easy.etl.execute.mapper.ExecuteJobMapper;
import com.easy.etl.execute.service.IExecuteJobService;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 执行器任务服务实现类
 */
@Service
public class ExecuteJobServiceImpl extends BaseServiceImpl<ExecuteJobMapper, ExecuteJob> implements IExecuteJobService {

    @Override
    public void saveOrupdate(ExecuteJob executeJob) {
        executeJob.setStatus(TaskStatusEnum.TO_BE_EXECUTED.getStatus());
        executeJob.setIsMonitor(MonitorEnum.START.getStatus());
        executeJob.setIp(Config.computerIp);
        executeJob.setAddTime(LocalDateTime.now());
        saveOrUpdate(executeJob);
    }

    @Override
    public List<ExecuteJob> queryExecuteJob(String ip, List<Integer> taskTypes, TaskStatusEnum taskStatusEnum) {
        LambdaQueryWrapper<ExecuteJob> queryWrapper = new LambdaQueryWrapper<ExecuteJob>();
        queryWrapper.eq(ExecuteJob::getStatus, taskStatusEnum.getStatus());
        if (StringUtils.isNotBlank(ip)) {
            queryWrapper.eq(ExecuteJob::getIp, ip);
        }
        if (!ObjectUtils.isEmpty(taskTypes)) {
            queryWrapper.in(ExecuteJob::getTaskType, taskTypes);
        }
        List<ExecuteJob> executeJobs = baseMapper.selectList(queryWrapper);
        return Optional.of(executeJobs).orElse(new ArrayList<>());
    }

    @Override
    public void updateTaskStatus(String jobId, String applicationId, int status) {
        switch (TaskStatusEnum.getTaskStatusEnum(status)) {
            case SUCCED:
                baseMapper.update(null,
                        new LambdaUpdateWrapper<ExecuteJob>()
                                .eq(ExecuteJob::getJobId, jobId)
                                .set(ExecuteJob::getCompletionTime, LocalDateTime.now())
                                .set(ExecuteJob::getStatus, status)
                );
                break;
            default:
                baseMapper.update(null,
                        new LambdaUpdateWrapper<ExecuteJob>()
                                .eq(ExecuteJob::getJobId, jobId)
                                .set(ExecuteJob::getStatus, status)
                                .set(ExecuteJob::getApplicationId, applicationId)
                );
        }
    }

}
