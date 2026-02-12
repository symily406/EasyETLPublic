package com.easy.etl.kill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.core.init.Config;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.kill.entity.KillJob;
import com.easy.etl.kill.mapper.KillJobMapper;
import com.easy.etl.kill.service.IKillJobService;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 执行器杀死任务表-服务类
 *
 * @author 邵勇
 * @time 2025-10-08
 */
@Service
public class KillJobServiceImpl extends BaseServiceImpl<KillJobMapper, KillJob> implements IKillJobService {

    /**
     * 保存或修改Entity
     */
    @Override
    public void saveOrUpdateKillJob(KillJob killJob) {
        killJob.setStatus(TaskStatusEnum.WATE_TASK_STOP.getStatus());
        saveOrUpdate(killJob);
    }

    @Override
    public List<KillJob> queryKillJobAll(String ip) {
        LambdaQueryWrapper<KillJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KillJob::getStatus, TaskStatusEnum.WATE_TASK_STOP.getStatus());
        if (StringUtils.isNotBlank(ip)) {
            queryWrapper.eq(KillJob::getIp, Config.computerIp);
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void updateStatus(String jobId, int status) {
        baseMapper.update(null, new LambdaUpdateWrapper<KillJob>().eq(KillJob::getJobId, jobId).set(KillJob::getCompletionTime, LocalDateTime.now()).set(KillJob::getStatus, status));
    }

}
