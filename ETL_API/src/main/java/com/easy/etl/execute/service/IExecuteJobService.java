package com.easy.etl.execute.service;

import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.execute.entity.ExecuteJob;
import com.easy.etl.rabbitmq.task.TaskJobInfo;

import java.util.List;

/**
 * 执行器任务服务接口
 */
public interface IExecuteJobService {
    public void saveOrupdate(ExecuteJob executeJob);

    public List<ExecuteJob> queryExecuteJob(String ip, List<Integer> taskTypes, TaskStatusEnum taskStatusEnum);

    public void updateTaskStatus(String jobId,String applicationId, int status);
}
