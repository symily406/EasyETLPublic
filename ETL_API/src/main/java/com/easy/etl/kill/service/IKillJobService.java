package com.easy.etl.kill.service;


import java.util.List;

import com.easy.etl.kill.entity.KillJob;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 执行器杀死任务表-服务类
 *
 * @author 邵勇
 * @time 2025-10-08
 */
public interface IKillJobService extends IBaseService<KillJob> {
    public void saveOrUpdateKillJob(KillJob killJob);

    public List<KillJob> queryKillJobAll(String ip);

    public void updateStatus(String jobId, int status);
}
