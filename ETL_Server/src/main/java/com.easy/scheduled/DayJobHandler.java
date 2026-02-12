package com.easy.scheduled;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.core.init.Config;
import com.easy.etl.em.CornStatusEnum;
import com.easy.etl.em.TaskSubmitEnum;
import com.easy.etl.projectTask.dto.ProjectTaskDTO;
import com.easy.etl.projectTask.dto.ProjectTaskLazyDTO;
import com.easy.etl.projectTask.entity.ProjectTask;
import com.easy.etl.projectTask.service.IProjectTaskLazyService;
import com.easy.etl.projectTask.service.IProjectTaskService;
import com.easy.etl.projectTask.service.impl.ProjectTaskLazyServiceImpl;
import com.easy.etl.projectTask.service.impl.ProjectTaskServiceImpl;
import com.easy.etl.projectTask.vo.ProjectTaskLazyVo;
import com.easy.etl.projectTaskParameter.entity.ProjectTaskParameter;
import com.easy.etl.projectTaskParameter.service.IProjectTaskParameterService;
import com.easy.etl.projectTaskParameter.service.impl.ProjectTaskParameterServiceImpl;
import com.easy.etl.projectTaskScript.entity.ProjectTaskScript;
import com.easy.etl.projectTaskScript.service.IProjectTaskScriptService;
import com.easy.etl.projectTaskScript.service.impl.ProjectTaskScriptServiceImpl;
import com.easy.etl.taskJob.service.ITaskJobService;
import com.easy.etl.taskJob.service.impl.TaskJobServiceImpl;
import com.easy.system.taskSchedule.compont.AbstractBaseCronTask;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.model.TaskScheduleResult;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.plugin.context.Application;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 生成每天要运行的定时任务在每天晚上12:30分生成第二天要执行的任务
 */
public class DayJobHandler extends AbstractBaseCronTask {

    private ITaskJobService taskJobService;

    private IProjectTaskService projectTaskService;

    private IProjectTaskParameterService projectTaskParameterService;

    private IProjectTaskScriptService projectTaskScriptService;
    private IProjectTaskLazyService projectTaskLazyService;

    public DayJobHandler(TaskSchedule taskSchedule, TaskScheduleMapper taskScheduleMapper, TaskScheduleLogMapper taskScheduleLogMapper) {
        super(taskSchedule, taskScheduleMapper, taskScheduleLogMapper);
    }

    @Override
    public void beforeJob(TaskSchedule taskSchedule) {
        if (ObjectUtils.isEmpty(taskJobService)) {
            taskJobService = Application.getBean(TaskJobServiceImpl.class);
        }
        if (ObjectUtils.isEmpty(projectTaskService)) {
            projectTaskService = Application.getBean(ProjectTaskServiceImpl.class);
        }
        if (ObjectUtils.isEmpty(projectTaskParameterService)) {
            projectTaskParameterService = Application.getBean(ProjectTaskParameterServiceImpl.class);
        }
        if (ObjectUtils.isEmpty(projectTaskScriptService)) {
            projectTaskScriptService = Application.getBean(ProjectTaskScriptServiceImpl.class);
        }
        if (ObjectUtils.isEmpty(projectTaskLazyService)) {
            projectTaskLazyService = Application.getBean(ProjectTaskLazyServiceImpl.class);
        }
    }

    @Override
    public void startJob(TaskSchedule taskSchedule) {
        if (!Config.isMaster) {
            return;
        }
        String day = DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyy-MM-dd");
        //=================普通任务生成===================
        List<ProjectTask> projectTasks = projectTaskService.list(
                new LambdaQueryWrapper<ProjectTask>()
                        .eq(ProjectTask::getIsSubmit, TaskSubmitEnum.SUBMIT.getStatus())
                        .eq(ProjectTask::getIsDel, 1)
                        .eq(ProjectTask::getCornStatus, CornStatusEnum.ENABLE.getStatus())
                        .le(ProjectTask::getStartDate, day)
                        .gt(ProjectTask::getEndDate, day)
        );
        if (!ObjectUtils.isEmpty(projectTasks)) {
            //运行时间
            Date startTime =  DateUtil.parseDateTime(String.format("%s %s", day, "00:00:00"));
            Date endTime = DateUtil.parseDateTime(String.format("%s %s", day, "23:59:59"));
            //任务ID
            List<String> taskIds = projectTasks.stream().map(ProjectTask::getTaskId).collect(Collectors.toList());
            //任务脚本
            List<ProjectTaskScript> projectTaskScripts = projectTaskScriptService.list(
                    new LambdaQueryWrapper<ProjectTaskScript>()
                            .in(ProjectTaskScript::getTaskId, taskIds)
            );
            //依懒任务
            ProjectTaskLazyDTO lazyDTO = new ProjectTaskLazyDTO();
            lazyDTO.setTaskIds(taskIds);
            List<ProjectTaskLazyVo> lazyAllVos = projectTaskLazyService.queryProjectTaskLazyAll(lazyDTO);
            //任务参数
            ProjectTaskDTO dto = new ProjectTaskDTO();
            dto.setTaskIds(taskIds);
            List<ProjectTaskParameter> projectTaskParameters = projectTaskParameterService.queryParametersByTaskId(dto);

            for (ProjectTask projectTask : projectTasks) {
                String taskId = projectTask.getTaskId();
                ProjectTaskScript projectTaskScript = projectTaskScripts.stream().filter(v -> v.getTaskId().equals(taskId)).findFirst().orElse(new ProjectTaskScript());
                //依懒任务
                List<ProjectTaskLazyVo> lazyVos = lazyAllVos.stream().filter(v -> v.getTaskId().equals(taskId)).collect(Collectors.toList());
                if (!ObjectUtils.isEmpty(lazyVos)) {
                    for (ProjectTaskLazyVo vo : lazyVos) {
                        vo.setRunTimes(CronCalculator.getAllRunTimes(vo.getCorn(), startTime, endTime));
                    }
                }
                //任务参数
                Map<String, String> parameters = new HashMap<>();
                if (!ObjectUtils.isEmpty(projectTaskParameters)){
                    List<ProjectTaskParameter> list = Optional.of(projectTaskParameters.stream().filter(v -> v.getTaskId().equals(taskId)).collect(Collectors.toList())).orElse(new ArrayList<>());
                    for (ProjectTaskParameter parameter : list) {
                        parameters.put("${" + parameter.getParamKey() + "}", parameter.getParamValue());
                    }
                }
                taskJobService.saveOrUpdateTaskJob(projectTask, projectTaskScript.getScriptContent(), parameters, startTime, endTime, lazyVos, false);
            }
        }
    }

    @Override
    public void afterJob(TaskScheduleResult taskScheduleResult) {

    }
}
