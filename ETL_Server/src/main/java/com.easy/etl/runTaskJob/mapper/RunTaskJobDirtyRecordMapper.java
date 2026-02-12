package com.easy.etl.runTaskJob.mapper;


import java.util.List;

import com.easy.etl.runTaskJob.entity.RunTaskJobDirtyRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.mapper.MapperDao;

/**
 * 任务同步脏数据-接口
 *
 * @author 邵勇
 * @time 2025-10-08
 */
@Mapper
public interface RunTaskJobDirtyRecordMapper extends MapperDao<RunTaskJobDirtyRecord> {


}
