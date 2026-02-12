package com.easy.system.taskSchedule.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.system.taskSchedule.dto.TaskScheduleLogDTO;
import com.easy.system.taskSchedule.entity.TaskScheduleLog;
import com.easy.system.taskSchedule.vo.TaskScheduleLogVo;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务调度日志-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface TaskScheduleLogMapper extends MapperDao<TaskScheduleLog> {


    /**
     * 分页
     *
     * @param param
     * @return
     */
    List<TaskScheduleLogVo> queryTaskScheduleLogPage(IPage<TaskScheduleLogVo> page, @Param("param") TaskScheduleLogDTO param);

}
