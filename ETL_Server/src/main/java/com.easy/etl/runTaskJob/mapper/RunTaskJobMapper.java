package com.easy.etl.runTaskJob.mapper;


import java.util.List;

import com.easy.etl.runTaskJob.dto.RunTaskJobDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJob;
import com.easy.etl.runTaskJob.vo.RunTaskJobVo;
import com.easy.etl.rabbitmq.task.TaskJobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务运行-接口
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Mapper
public interface RunTaskJobMapper extends MapperDao<RunTaskJob> {

    //=============代码生成器生成代码开始==============

    /**
     * 分页
     *
     * @param param
     * @return
     */
    List<RunTaskJobVo> queryRunTaskJobPage(IPage<RunTaskJobVo> page, @Param("param") RunTaskJobDTO param);

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    List<RunTaskJobVo> queryRunTaskJobAll(@Param("param") RunTaskJobDTO param);

    /**
     * 根据条件查询单个
     *
     * @param param
     * @return
     */
    RunTaskJobVo queryRunTaskJobByCondition(@Param("param") RunTaskJobDTO param);
    //=============代码生成器生成代码结束==============

    List<RunTaskJobVo> queryRunTaskJob(@Param("param") RunTaskJobDTO param);

    List<TaskJobInfo> queryTaskJobInfo(int limitSize);

    /**
     * 查询最新运行任务
     *
     * @param taskIds
     * @return
     */
    List<RunTaskJobVo> queryLastRunTaskJob(@Param("taskIds") List<String> taskIds);

    /**
     * 根据taskID返回有运行结果的任务
     * @param taskId
     * @return
     */
    List<RunTaskJobVo> queryTaskJobByTaskId(@Param("taskId") String taskId, @Param("day") String day);
}
