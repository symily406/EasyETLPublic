package com.easy.etl.runTaskJob.service;


import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.runTaskJob.dto.RunTaskJobTimeDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJobTime;
import com.easy.etl.runTaskJob.vo.RunTaskJobTimeVo;
import com.spring.boot.ext.plugin.mybatis.service.IBaseService;

/**
 * 任务运行时间-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
public interface IRunTaskJobTimeService extends IBaseService<RunTaskJobTime> {

    /**
     * 保存或修改Entity
     *
     * @param runTaskJobTime
     */
    public boolean saveOrUpdateRunTaskJobTime(RunTaskJobTime runTaskJobTime);


}
