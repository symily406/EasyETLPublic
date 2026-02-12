package com.easy.etl.flowRunJob.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.flowRunJob.dto.FlowRunJobDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.vo.FlowRunJobVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 工作流运行JOB-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IFlowRunJobService extends IBaseService<FlowRunJob> {

    /**
     * 查询正在运行的任务
     *
     * @param taskId
     * @return
     */
    public FlowRunJob queryRunnerFlow(String taskId);


    /**
     * 保存或修改Entity
     *
     * @param flowRunJob
     */
    public boolean saveOrUpdateFlowRunJob(FlowRunJob flowRunJob);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FlowRunJobVo> queryFlowRunJobPage(IPage<FlowRunJobVo> page, FlowRunJobDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<FlowRunJobVo> queryFlowRunJobAll(FlowRunJobDTO param);
}
