package com.easy.etl.projectTaskParameter.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.mapper.ProjectTaskMapper;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.mapper.ProjectTaskParameterMapper;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务参数-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service
public class ProjectTaskParameterServiceImpl extends BaseServiceImpl<ProjectTaskParameterMapper, ProjectTaskParameter> implements IProjectTaskParameterService {

    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    /**
     * 保存或修改Entity
     *
     * @param projectTaskParameter
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateProjectTaskParameter(String taskId, List<ProjectTaskParameter> projectTaskParameter, int taskStep) {
        Map<String, Object> param = new HashMap<>();
        param.put("taskId", taskId);
        baseMapper.deleteByCondition("DELETE FROM TF_PROJECT_TASK_PARAMETER WHERE TASK_ID=:taskId", param);

        //修改任务步骤
        ProjectTask task = new ProjectTask();
        task.setTaskStep(taskStep);
        task.setTaskId(taskId);
        projectTaskMapper.updateById(task);
        return saveOrUpdateBatch(projectTaskParameter);
    }

    @Override
    public List<ProjectTaskParameter> queryParametersByTaskId(ProjectTaskDTO param) {
        LambdaQueryWrapper<ProjectTaskParameter> queryWrapper = new LambdaQueryWrapper<ProjectTaskParameter>();
        queryWrapper.select(ProjectTaskParameter::getParamKey, ProjectTaskParameter::getParamValue, ProjectTaskParameter::getTaskId);
        if (StringUtils.isNotBlank(param.getTaskId())) {
            queryWrapper.eq(ProjectTaskParameter::getTaskId, param.getTaskId());
        }
        if (ObjectUtil.isNotEmpty(param.getTaskIds())) {
            queryWrapper.in(ProjectTaskParameter::getTaskId, param.getTaskIds());
        }
        queryWrapper.orderByAsc(ProjectTaskParameter::getId);
        return baseMapper.selectList(queryWrapper).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public Map<String, String> parameters(String taskId) {
        Map<String, String> map = new HashMap<>();
        ProjectTaskDTO param = new ProjectTaskDTO();
        param.setTaskId(taskId);
        List<ProjectTaskParameter> list = queryParametersByTaskId(param);
        for (ProjectTaskParameter parameter : list) {
            String value = Expression.keyExpression(parameter.getParamValue());
            map.put("${" + parameter.getParamKey() + "}", value);
        }
        return map;
    }

    @Override
    public Map<String, String> dynamicParameters(String taskId) {
        Map<String, String> map = new HashMap<>();
        ProjectTaskDTO param = new ProjectTaskDTO();
        param.setTaskId(taskId);
        List<ProjectTaskParameter> list = queryParametersByTaskId(param);
        for (ProjectTaskParameter parameter : list) {
            map.put("${" + parameter.getParamKey() + "}", parameter.getParamValue());
        }
        return map;
    }
}
