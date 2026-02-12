package com.easy.etl.runTaskJob.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.datax.report.Counter;
import com.easy.etl.datax.report.RunnerReport;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.runTaskJob.dto.RunTaskJobDTO;
import com.easy.etl.runTaskJob.dto.RunTaskJobLogDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.mapper.RunTaskJobLogMapper;
import com.easy.etl.runTaskJob.mapper.RunTaskJobMapper;
import com.easy.etl.runTaskJob.model.LogInfo;
import com.easy.etl.runTaskJob.service.IRunTaskJobLogService;
import com.easy.etl.runTaskJob.vo.RunTaskJobLogVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.zookeeper.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 任务运行日志-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class RunTaskJobLogServiceImpl extends BaseServiceImpl<RunTaskJobLogMapper, RunTaskJobLog> implements IRunTaskJobLogService {

    @Autowired
    private RunTaskJobMapper runTaskJobMapper;

    /**
     * 保存同步任务
     *
     * @param report
     * @return
     */
    @Override
    public boolean saveOrUpdateRunTaskJobLogSync(RunnerReport report) {
        if (ObjectUtils.isEmpty(report.getJobId())) {
            return false;
        }

        if (ObjectUtils.isEmpty(report.getCounter())) {
            return false;
        }
        RunTaskJob job = runTaskJobMapper.selectById(report.getJobId());

        if (ObjectUtils.isEmpty(job)) {
            return false;
        }
        Counter counter = report.getCounter();
        StringBuilder sb = new StringBuilder();
        sb.append("读取记录数: ").append(counter.getReadSucceedRecords()).append("，");
        sb.append("读取字节数: ").append(counter.getReadSucceedBytes()).append("，");
        sb.append("写入记录数: ").append(counter.getWriteSucceedRecords()).append("，");
        sb.append("写入字节数: ").append(counter.getWriteSucceedBytes()).append("，");
        sb.append("已接收: ").append(counter.getWriteReceivedRecords()).append("，");
        sb.append("读写速率: ").append(counter.getByteSpeed()).append("B/S，");
        sb.append("错误记录: ").append(counter.getTotalErrorRecords());
        if (report.getIsDirty() == 1) {
            runTaskJobMapper.update(null, new LambdaUpdateWrapper<RunTaskJob>()
                    .eq(RunTaskJob::getJobId, report.getJobId())
                    .set(RunTaskJob::getIsDirtyRecord, 1));
        }
        if (report.isFinished()) {
            runTaskJobMapper.update(null, new LambdaUpdateWrapper<RunTaskJob>()
                    .eq(RunTaskJob::getJobId, report.getJobId())
                    .set(RunTaskJob::getReadRecords, counter.getTotalReadRecords())
                    .set(RunTaskJob::getReadWriteFailures, counter.getTotalErrorRecords()));
        }
        RunTaskJobLog jobLog = new RunTaskJobLog();
        jobLog.setJobId(String.valueOf(report.getJobId()));
        jobLog.setLogContent(sb.toString());
        jobLog.setStatus(TaskStatusEnum.PARENT_RUN.getStatus());
        jobLog.setAddTime(LocalDateTime.now());
        jobLog.setTaskId(job.getTaskId());
        return saveOrUpdate(jobLog);
    }

    /**
     * 保存或修改Entity
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateRunTaskJobLog(LogInfo logInfo, Integer cacheResult) {
        if (StringUtils.isEmpty(logInfo.getJobId())) {
            return false;
        }
        RunTaskJob job = runTaskJobMapper.selectById(logInfo.getJobId());

        if (ObjectUtils.isEmpty(job)) {
            return false;
        }
        if (TaskStatusEnum.getTaskStatusEnum(logInfo.getStatus()) == TaskStatusEnum.SUCCED) {
            runTaskJobMapper.update(null, new LambdaUpdateWrapper<RunTaskJob>()
                    .eq(RunTaskJob::getJobId, logInfo.getJobId())
                    .set(RunTaskJob::getCacheResult, cacheResult)
            );
        }
        RunTaskJobLog jobLog = new RunTaskJobLog();
        jobLog.setJobId(logInfo.getJobId());
        jobLog.setLogContent(logInfo.getMessage());
        jobLog.setStatus(logInfo.getStatus());
        jobLog.setAddTime(DateUtil.parseLocalDateTime(logInfo.getDate()));
        jobLog.setTaskId(job.getTaskId());
        return saveOrUpdate(jobLog);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RunTaskJobLogVo> queryRunTaskJobLogPage(IPage<RunTaskJobLogVo> page, RunTaskJobLogDTO param) {
        return page.setRecords(baseMapper.queryRunTaskJobLogPage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<RunTaskJobLogVo> queryRunTaskJobLogAll(RunTaskJobLogDTO param) {
        return baseMapper.queryRunTaskJobLogAll(param);
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public RunTaskJobLogVo queryRunTaskJobLogByCondition(RunTaskJobLogDTO param) {
        return baseMapper.queryRunTaskJobLogByCondition(param);
    }
}
