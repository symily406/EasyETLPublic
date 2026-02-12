package com.easy.etl.flowRunJob.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.flowRunJob.dto.FlowRunJobDTO;
import com.easy.etl.flowRunJob.entity.FlowRunJob;
import com.easy.etl.flowRunJob.entity.FlowRunJobNode;
import com.easy.etl.flowRunJob.entity.FlowRunJobPnode;
import com.easy.etl.flowRunJob.mapper.FlowRunJobMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobNodeMapper;
import com.easy.etl.flowRunJob.mapper.FlowRunJobPnodeMapper;
import com.easy.etl.flowRunJob.service.IFlowRunJobService;
import com.easy.etl.flowRunJob.vo.FlowRunJobNodeStatusVo;
import com.easy.etl.flowRunJob.vo.FlowRunJobVo;
import com.easy.etl.projectTaskFlow.entity.FlowJob;
import com.easy.etl.projectTaskFlow.entity.FlowJobParameter;
import com.easy.etl.projectTaskFlow.entity.FlowJobPnode;
import com.easy.etl.projectTaskFlow.mapper.FlowJobMapper;
import com.easy.etl.projectTaskFlow.mapper.FlowJobNodeMapper;
import com.easy.etl.projectTaskFlow.mapper.FlowJobParameterMapper;
import com.easy.etl.projectTaskFlow.mapper.FlowJobPnodeMapper;
import com.easy.etl.projectTaskFlow.vo.FlowJobNodeVo;
import com.easy.etl.projectTaskFlow.vo.FlowJobVo;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 工作流运行JOB-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class FlowRunJobServiceImpl extends BaseServiceImpl<FlowRunJobMapper, FlowRunJob> implements IFlowRunJobService {

    @Autowired
    private FlowJobMapper flowJobMapper;

    @Autowired
    private FlowJobPnodeMapper flowJobPnodeMapper;
    @Autowired
    private FlowJobNodeMapper flowJobNodeMapper;
    @Autowired
    private FlowJobParameterMapper flowJobParameterMapper;
    @Autowired
    private FlowRunJobPnodeMapper flowRunJobPnodeMapper;
    @Autowired
    private FlowRunJobNodeMapper flowRunJobNodeMapper;

    @Override
    public FlowRunJob queryRunnerFlow(String taskId) {
        List<FlowRunJob> list = baseMapper.selectList(
                new LambdaQueryWrapper<FlowRunJob>()
                        .select(FlowRunJob::getFlowRunJobId, FlowRunJob::getFlowJobId, FlowRunJob::getTaskId, FlowRunJob::getStatus)
                        .eq(FlowRunJob::getTaskId, taskId)
                        .in(FlowRunJob::getStatus, TaskStatusEnum.TO_BE_ALLOCATED.getStatus(),
                                TaskStatusEnum.ACCEPTED.getStatus(),
                                TaskStatusEnum.TO_BE_EXECUTED.getStatus(),
                                TaskStatusEnum.RUN.getStatus(),
                                TaskStatusEnum.WATE_TASK_STOP.getStatus())
                        .orderByDesc(FlowRunJob::getAddTime)
        );
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 保存或修改Entity
     *
     * @param flowRunJob
     */
    @Override
    public boolean saveOrUpdateFlowRunJob(FlowRunJob flowRunJob) {
        return saveOrUpdate(flowRunJob);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FlowRunJobVo> queryFlowRunJobPage(IPage<FlowRunJobVo> page, FlowRunJobDTO param) {
        return page.setRecords(baseMapper.queryFlowRunJobPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<FlowRunJobVo> queryFlowRunJobAll(FlowRunJobDTO param) {
        return baseMapper.queryFlowRunJobAll(param);
    }


}
