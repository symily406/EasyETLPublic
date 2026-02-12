package com.easy.etl.projectTaskFlow.service.impl;

import com.easy.etl.projectTaskFlow.entity.FlowJobContent;
import com.easy.etl.projectTaskFlow.mapper.FlowJobContentMapper;
import com.easy.etl.projectTaskFlow.service.IFlowJobContentService;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 工作流JOB_内容-服务类
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class FlowJobContentServiceImpl extends BaseServiceImpl<FlowJobContentMapper, FlowJobContent> implements IFlowJobContentService {

}
