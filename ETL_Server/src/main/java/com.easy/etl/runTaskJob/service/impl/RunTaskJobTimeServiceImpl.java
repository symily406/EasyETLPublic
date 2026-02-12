package com.easy.etl.runTaskJob.service.impl;
import java.util.List;

import com.easy.etl.runTaskJob.dto.RunTaskJobTimeDTO;
import com.easy.etl.runTaskJob.entity.RunTaskJobTime;
import com.easy.etl.runTaskJob.mapper.RunTaskJobTimeMapper;
import com.easy.etl.runTaskJob.service.IRunTaskJobTimeService;
import com.easy.etl.runTaskJob.vo.RunTaskJobTimeVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;

/**
 * 任务运行时间-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class RunTaskJobTimeServiceImpl extends BaseServiceImpl<RunTaskJobTimeMapper, RunTaskJobTime> implements IRunTaskJobTimeService {

	/**
	     * 保存或修改Entity
	 * @param runTaskJobTime
	 */
	@Override
	public boolean saveOrUpdateRunTaskJobTime(RunTaskJobTime runTaskJobTime) {
		return saveOrUpdate(runTaskJobTime);
	}

}
