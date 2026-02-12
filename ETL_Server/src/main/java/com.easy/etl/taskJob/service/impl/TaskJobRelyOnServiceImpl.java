package com.easy.etl.taskJob.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.etl.taskJob.entity.TaskJobRelyOn;
import com.easy.etl.taskJob.mapper.TaskJobRelyOnMapper;
import com.easy.etl.taskJob.service.ITaskJobRelyOnService;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 待运行任务依赖-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class TaskJobRelyOnServiceImpl extends BaseServiceImpl<TaskJobRelyOnMapper, TaskJobRelyOn> implements ITaskJobRelyOnService {

}
