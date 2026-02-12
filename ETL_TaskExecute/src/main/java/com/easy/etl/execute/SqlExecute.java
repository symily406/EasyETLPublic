package com.easy.etl.execute;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.db.Entity;
import com.easy.etl.cache.CacheCore;
import com.easy.etl.common.db.em.DbEnum;
import com.easy.etl.common.db.kit.DbFactory;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.core.callback.ICallBack;
import com.easy.etl.core.init.Config;
import com.easy.etl.core.thread.ThreadCore;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.rabbitmq.task.TaskJobInfo;
import com.spring.boot.ext.kit.jackson.JsonKit;
import com.spring.boot.ext.plugin.cache.CacheKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public class SqlExecute implements Runnable {
    private static Logger log = LoggerFactory.getLogger("JOB_RUN_INFO");

    private TaskJobInfo taskJobInfo;
    private ICallBack callBack;

    private String jobId;

    private String applicationId;

    public SqlExecute(TaskJobInfo taskJobInfo, ICallBack callBack) {
        this.taskJobInfo = taskJobInfo;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        setJobId(taskJobInfo.getJobId());
        setApplicationId(taskJobInfo.getApplicationId());
        TimeInterval timer = DateUtil.timer();
        try {
            callBack.start(getJobId(), getApplicationId(), "任务开始运行,任务执行节点:" + Config.computerIp, TaskStatusEnum.RUN.getStatus(), new Date());
            log.info("任务1编号:" + taskJobInfo.getJobId() + "任务开始运行,任务执行节点:" + Config.computerIp);
            DataSource ds = DbFactory.getDataSource(taskJobInfo);
            DbEnum dbType = DbEnum.getByDriverClassName(taskJobInfo.getJdbcDrive());
            List<String> sqls = DbKit.parseSql(DbKit.removeComments(taskJobInfo.getScriptContent()), dbType.getDbType());
            if (sqls.size() == 0) {
                log.error("任务2编号:" + taskJobInfo.getJobId() + ",运行失败:脚本中没有可以执行的sql");
                safeCallbackCompleted(getJobId(), getApplicationId(), "任务编号:" + taskJobInfo.getJobId() + "运行错误,脚本中没有可以执行的sql", null, TaskStatusEnum.FAIL.getStatus(), new Date(), timer.interval());
                return;
            }
            if (taskJobInfo.getHasResult() == 0) {
                for (String sql : sqls) {
                    DbKit.use(ds, taskJobInfo.getJdbcDrive()).execute(sql);
                }
                log.info("任务3编号:" + taskJobInfo.getJobId() + ",运行成功,用时:" + timer.interval() + "毫秒");
                safeCallbackCompleted(getJobId(), getApplicationId(), "任务编号:" + taskJobInfo.getJobId() + ",运行成功,用时:" + timer.interval() + "毫秒", null, TaskStatusEnum.SUCCED.getStatus(), new Date(), timer.interval());
                return;
            }
            if (sqls.size() > 1) {
                log.error("任务4编号:" + taskJobInfo.getJobId() + ",运行失败:脚本中有多个返回值");
                safeCallbackCompleted(getJobId(), getApplicationId(), "任务编号:" + taskJobInfo.getJobId() + "运行错误,脚本中有多个返回值", null, TaskStatusEnum.FAIL.getStatus(), new Date(), timer.interval());
                return;
            }
            List<Entity> list = DbKit.use(ds, taskJobInfo.getJdbcDrive()).query(DbEnum.getLimitSqlByDriverClassName(taskJobInfo.getJdbcDrive(), sqls.get(0)));
            log.info("任务5编号:" + taskJobInfo.getJobId() + ",运行成功,用时:" + timer.interval() + "毫秒");
            String key = CacheCore.getSqlResultKey(getJobId());
            String name = CacheCore.getSqlResultName();
            CacheKit.put(name, key, JsonKit.toJsonString(list));
            safeCallbackCompleted(getJobId(), getApplicationId(), "任务6编号:" + taskJobInfo.getJobId() + ",运行成功,用时:" + timer.interval() + "毫秒", "1", TaskStatusEnum.SUCCED.getStatus(), new Date(), timer.interval());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            safeCallbackCompleted(getJobId(), getApplicationId(), "任务编号:" + taskJobInfo.getJobId() + "运行错误," + e.getMessage(), null, TaskStatusEnum.FAIL.getStatus(), new Date(), timer.interval());
            log.error("任务6编号:" + taskJobInfo.getJobId() + ",运行失败:" + e.getMessage());
            e.printStackTrace();
        } finally {
            ThreadCore.removeThread(getJobId());
        }
    }

    private void safeCallbackCompleted(String jobId, String applicationId, String message, String result, int status, Date time, long timer) {
        try {
            callBack.completed(jobId, applicationId, message, result, status, time, timer);
        } catch (Exception e) {
            log.error("回调通知失败，任务ID: " + jobId + ", 错误信息: " + e.getMessage(), e);
            if (status == TaskStatusEnum.SUCCED.getStatus()) {
                log.info("任务执行成功但回调失败，任务ID: {}, 消息: {}, 用时: {}毫秒", jobId, message, timer);
            } else {
                log.error("任务执行失败且回调失败，任务ID: {}, 消息: {}, 用时: {}毫秒", jobId, message, timer);
            }
        }
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
