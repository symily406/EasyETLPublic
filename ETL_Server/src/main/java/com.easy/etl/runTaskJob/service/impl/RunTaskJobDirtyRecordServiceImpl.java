package com.easy.etl.runTaskJob.service.impl;

import com.easy.etl.runTaskJob.entity.RunTaskJobDirtyRecord;
import com.easy.etl.runTaskJob.mapper.RunTaskJobDirtyRecordMapper;
import com.easy.etl.runTaskJob.service.IRunTaskJobDirtyRecordService;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 任务同步脏数据-服务类
 * @author 邵勇
 * @time 2025-10-08
 */
@Service
public class RunTaskJobDirtyRecordServiceImpl extends BaseServiceImpl<RunTaskJobDirtyRecordMapper, RunTaskJobDirtyRecord> implements IRunTaskJobDirtyRecordService {



}
