package com.easy.etl.runTaskJob.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.runTaskJob.dto.RunTaskJobDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.vo.RunTaskJobVo;
import com.easy.etl.rabbitmq.task.TaskJobResult;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务运行-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IRunTaskJobService extends IBaseService<RunTaskJob> {

    /**
     * 查询正在运行的JOB
     *
     * @param taskId
     * @return
     */
    public RunTaskJob queryRunnerJob(String taskId);


    /**
     * 任务停止
     * @param  jobIds
     * @return
     */
    public boolean jobStop(List<String> jobIds);


    //================代码生成器生成代码开始=================

    /**
     * 保存或修改Entity
     *
     * @param runTaskJob
     */
    public boolean saveOrUpdateRunTaskJob(RunTaskJob runTaskJob);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RunTaskJobVo> queryRunTaskJobPage(IPage<RunTaskJobVo> page, RunTaskJobDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<RunTaskJobVo> queryRunTaskJobAll(RunTaskJobDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    public RunTaskJobVo queryRunTaskJobByCondition(RunTaskJobDTO param);
    //================代码生成器生成代码结束=================

    public List<RunTaskJobVo> queryRunTaskJob(RunTaskJobDTO param);


    public void taskAllot(String ip, int limitSize);

    /**
     * 返回任务的运行结果
     *
     * @param jobId
     * @return
     */
    public String queryJobResult(String jobId);

    /**
     * 根据taskID返回有运行结果的任务
     *
     * @param taskId
     * @return
     */
    public List<RunTaskJobVo> queryTaskJobByTaskId(String taskId);

    /**
     * 移除运行结果
     * @param jobId
     * @param taskId
     * @return
     */
    public List<RunTaskJobVo> removeTaskJobResult(String jobId, String taskId);

    /**
     * 保存运行日志
     *
     * @param result
     */
    public void saveJobResult(TaskJobResult result);

    /**
     * 任务推送消息队列
     *
     * @return
     */
    public void taskPush();

}
