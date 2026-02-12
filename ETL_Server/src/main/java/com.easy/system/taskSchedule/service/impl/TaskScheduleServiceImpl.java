package com.easy.system.taskSchedule.service.impl;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easy.etl.common.exception.util.ServiceExceptionUtil;
import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Config;
import com.easy.system.dict.entity.Dict;
import com.easy.system.taskSchedule.dto.TaskScheduleDTO;
import com.easy.system.taskSchedule.em.RunNodeEnum;
import com.easy.system.taskSchedule.em.TaskScheduleLevalEnum;
import com.easy.system.taskSchedule.em.TaskScheduleStatusEnum;
import com.easy.system.taskSchedule.entity.TaskSchedule;
import com.easy.system.taskSchedule.mapper.TaskScheduleLogMapper;
import com.easy.system.taskSchedule.mapper.TaskScheduleMapper;
import com.easy.system.taskSchedule.service.ITaskScheduleService;
import com.easy.system.taskSchedule.vo.TaskScheduleVo;
import com.easy.etl.utils.CronCalculator;
import com.spring.boot.ext.api.ApiResult;
import com.spring.boot.ext.kit.IpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.boot.ext.plugin.mybatis.service.impl.BaseServiceImpl;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * 任务调度-服务类
 *
 * @author 邵勇
 * @time 2024-01-17
 */
@Service("taskScheduleService")
public class TaskScheduleServiceImpl extends BaseServiceImpl<TaskScheduleMapper, TaskSchedule> implements ITaskScheduleService {
    @Resource
    private TaskScheduleMapper taskScheduleMapper;
    @Resource
    private TaskScheduleLogMapper taskScheduleLogMapper;
    /**
     * 添加定时任务
     */
    private static ConcurrentHashMap<String, ScheduledFuture> runningTasks = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier(GlobalConstant.schedulerThreadPool)
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public boolean manuallyOnceTaskSchedule(TaskSchedule taskSchedule) {
        boolean success = true;
        try {
            Class clazz = Class.forName(taskSchedule.getClassPath());
            Constructor constructor = clazz.getConstructor(TaskSchedule.class, TaskScheduleMapper.class, TaskScheduleLogMapper.class);
            constructor.setAccessible(true);
            Runnable runnable = (Runnable) constructor.newInstance(new Object[]{taskSchedule, taskScheduleMapper, taskScheduleLogMapper});
            runnable.run();
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean startTaskSchedule(TaskSchedule taskSchedule) {
        boolean success = true;
        if (runningTasks.containsKey(taskSchedule.getTaskScheduleId())) {
            return true;
        }
        try {
            Class clazz = Class.forName(taskSchedule.getClassPath());
            Constructor constructor = clazz.getConstructor(TaskSchedule.class, TaskScheduleMapper.class, TaskScheduleLogMapper.class);
            constructor.setAccessible(true);
            Runnable runnable = (Runnable) constructor.newInstance(new Object[]{taskSchedule, taskScheduleMapper, taskScheduleLogMapper});
            CronTrigger cron = new CronTrigger(taskSchedule.getCorn());
            runningTasks.put(taskSchedule.getTaskScheduleId(), Objects.requireNonNull(this.threadPoolTaskScheduler.schedule(runnable, cron)));
        } catch (Exception e) {
            success = false;
            throw ServiceExceptionUtil.exception(String.format("任务%s运行失败,错误信息:%s", taskSchedule.getName(), e));
        }
        return success;
    }

    @Override
    public boolean stopTaskSchedule(String taskScheduleId) {
        boolean success = true;
        try {
            ScheduledFuture<?> schedule = runningTasks.get(taskScheduleId);
            if (schedule == null) {
                runningTasks.remove(taskScheduleId);
                success = true;
            }
            schedule.cancel(true);
            runningTasks.remove(taskScheduleId);
        } catch (Exception e) {
            success = false;
            throw ServiceExceptionUtil.exception(String.format("任务编号为%s的任务停止失败，错误信息:%s", taskScheduleId, e));
        }
        return success;
    }

    /**
     * 任务添加定时
     *
     * @param taskSchedule
     * @return
     */
    @Override
    public boolean addTaskSchedule(TaskSchedule taskSchedule) {
        if (Config.scheduledCluster) {
            baseMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                    .set(TaskSchedule::getStatus, TaskScheduleStatusEnum.TO_BE_EXECUTED.getStatus())
                    .set(TaskSchedule::getRunNextTime, CronCalculator.calculator(taskSchedule.getCorn()))
                    .eq(TaskSchedule::getTaskScheduleId, taskSchedule.getTaskScheduleId())
            );
            return true;
        }
        baseMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                .set(TaskSchedule::getStatus, TaskScheduleStatusEnum.BEING_EXECUTED.getStatus())
                .set(TaskSchedule::getRunNextTime, CronCalculator.calculator(taskSchedule.getCorn()))
                .eq(TaskSchedule::getTaskScheduleId, taskSchedule.getTaskScheduleId())
        );
        return startTaskSchedule(taskSchedule);
    }

    /**
     * 停止任务
     *
     * @param taskScheduleId
     * @return
     */
    @Override
    public boolean cancelTaskSchedule(String taskScheduleId) {
        if (Config.scheduledCluster) {
            baseMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                    .set(TaskSchedule::getStatus, TaskScheduleStatusEnum.STOPPING.getStatus())
                    .eq(TaskSchedule::getTaskScheduleId, taskScheduleId)
            );
            return true;
        }
        baseMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                .set(TaskSchedule::getStatus, TaskScheduleStatusEnum.STOP_EXECUTED.getStatus())
                .eq(TaskSchedule::getTaskScheduleId, taskScheduleId)
        );
        return stopTaskSchedule(taskScheduleId);
    }

    @Override
    public boolean stopedTaskSchedule(String taskScheduleId) {
        baseMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                .set(TaskSchedule::getStatus, TaskScheduleStatusEnum.STOP_EXECUTED.getStatus())
                .eq(TaskSchedule::getTaskScheduleId, taskScheduleId)
        );
        return true;
    }

    @Override
    public boolean beginTaskSchedule(String taskScheduleId) {
        baseMapper.update(null, new LambdaUpdateWrapper<TaskSchedule>()
                .set(TaskSchedule::getStatus, TaskScheduleStatusEnum.BEING_EXECUTED.getStatus())
                .eq(TaskSchedule::getTaskScheduleId, taskScheduleId)
        );
        return true;
    }

    /**
     * 保存或修改Entity
     */
    @Override
    public boolean saveOrUpdateTaskSchedule(TaskSchedule taskSchedule) {
        return saveOrUpdate(taskSchedule);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<TaskScheduleVo> queryTaskSchedulePage(IPage<TaskScheduleVo> page, TaskScheduleDTO param) {
        return page.setRecords(baseMapper.queryTaskSchedulePage(page, param));
    }

    /**
     * 根据条件查询
     *
     * @param param
     * @return
     */
    @Override
    public List<TaskScheduleVo> queryTaskScheduleAll(TaskScheduleDTO param) {
        return baseMapper.queryTaskScheduleAll(param);
    }

    /**
     * 开机启动任务
     *
     * @return
     */
    @Override
    public boolean bootUpTaskSchedule() {
        List<TaskSchedule> taskSchedules = baseMapper.selectList(new LambdaQueryWrapper<TaskSchedule>()
                .eq(TaskSchedule::getEnable, 1)
                .eq(TaskSchedule::getBootUp, 1)
                .eq(TaskSchedule::getIsDel, 1)
                .in(TaskSchedule::getStatus, TaskScheduleStatusEnum.TO_BE_EXECUTED.getStatus(), TaskScheduleStatusEnum.BEING_EXECUTED.getStatus(), TaskScheduleStatusEnum.STOPPING.getStatus())
                .in(TaskSchedule::getLevel, TaskScheduleLevalEnum.INTERNAL.getLeval(), TaskScheduleLevalEnum.NORMAL.getLeval())
        );
        if (ObjectUtils.isEmpty(taskSchedules)) {
            return false;
        }
        //起动定时任务
        for (TaskSchedule taskSchedule : taskSchedules) {
            switch (RunNodeEnum.getEnum(taskSchedule.getRunNode())) {
                case CURRENT_NODE:
                    addTaskSchedule(taskSchedule);
                    break;
                case APPOINT_NODE:
                    if (taskSchedule.getRunNodeIp().equals(Config.computerIp)) {
                        addTaskSchedule(taskSchedule);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public void systemTaskScheduleStart() {
        List<TaskSchedule> taskSchedules = baseMapper.selectList(new LambdaQueryWrapper<TaskSchedule>()
                .eq(TaskSchedule::getEnable, 1)
                .eq(TaskSchedule::getBootUp, 1)
                .eq(TaskSchedule::getIsDel, 1)
                .eq(TaskSchedule::getLevel, TaskScheduleLevalEnum.SYSTEM.getLeval())
                .in(TaskSchedule::getStatus, TaskScheduleStatusEnum.TO_BE_EXECUTED.getStatus(), TaskScheduleStatusEnum.BEING_EXECUTED.getStatus())
        );
        if (ObjectUtils.isEmpty(taskSchedules)) {
            return;
        }
        for (TaskSchedule taskSchedule : taskSchedules) {
            startTaskSchedule(taskSchedule);
        }
    }

}
