package com.easy.etl.runTaskJob.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.datax.report.RunnerReport;
import com.easy.etl.runTaskJob.dto.RunTaskJobLogDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJobLog;
import com.easy.etl.runTaskJob.model.LogInfo;
import com.easy.etl.runTaskJob.vo.RunTaskJobLogVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务运行日志-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IRunTaskJobLogService extends IBaseService<RunTaskJobLog> {

    /**
     * 保存同步日志
     *
     * @param report
     * @return
     */
    public boolean saveOrUpdateRunTaskJobLogSync(RunnerReport report);


    //================代码生成器生成代码开始=================

    /**
     * 保存或修改Entity
     *
     * @param logInfo
     */
    public boolean saveOrUpdateRunTaskJobLog(LogInfo logInfo, Integer cacheResult);

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RunTaskJobLogVo> queryRunTaskJobLogPage(IPage<RunTaskJobLogVo> page, RunTaskJobLogDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<RunTaskJobLogVo> queryRunTaskJobLogAll(RunTaskJobLogDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    public RunTaskJobLogVo queryRunTaskJobLogByCondition(RunTaskJobLogDTO param);
    //================代码生成器生成代码结束=================
}
