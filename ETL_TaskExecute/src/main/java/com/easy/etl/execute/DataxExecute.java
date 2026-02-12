package com.easy.etl.execute;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.easy.etl.common.db.em.DbEnum;
import com.easy.etl.common.db.kit.DbFactory;
import com.easy.etl.common.db.kit.DbInfo;
import com.easy.etl.common.db.kit.DbKit;
import com.easy.etl.core.callback.ICallBack;
import com.easy.etl.core.init.Config;
import com.easy.etl.core.thread.ThreadCore;
import com.easy.etl.datax.model.PreScriptContent;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.utils.JobUtils;
import com.easy.etl.rabbitmq.task.TaskJobInfo;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.sql.DataSource;
import java.util.Date;

public class DataxExecute implements ExecuteResultHandler, Runnable {
    private static Logger log = LoggerFactory.getLogger("JOB_RUN_INFO");
    private TimeInterval timer;
    private TaskJobInfo taskJobInfo;
    private ICallBack callBack;
    private DefaultExecutor executor;
    private String jobId;

    private String applicationId;

    public DataxExecute(TaskJobInfo taskJobInfo, ICallBack callBack) {
        this.taskJobInfo = taskJobInfo;
        this.callBack = callBack;
        executor = new DefaultExecutor();
        timer = DateUtil.timer();
    }

    @Override
    public void run() {
        try {
            taskJobInfo = JobUtils.createScriptFile(taskJobInfo);
            setJobId(taskJobInfo.getJobId());
            setApplicationId(taskJobInfo.getApplicationId());
            callBack.start(getJobId(), getApplicationId(), "任务开始运行,任务执行节点:" + Config.computerIp, TaskStatusEnum.RUN.getStatus(), new Date());
            executePreScriptContent(taskJobInfo);
            StringBuilder cmd = new StringBuilder();
            cmd.append(taskJobInfo.getBase().getJdkPath());
            cmd.append(JobUtils.syncCommand(taskJobInfo));
            log.info("任务脚本:" + cmd.toString());
            int exitValue = executor.execute(CommandLine.parse(cmd.toString()));
            onProcessComplete(exitValue);
        } catch (ExecuteException e) {
            log.info("任务执行异常:" + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
            callBack.completed(getJobId(), null, TaskStatusEnum.FAIL.getDesc() + ",错误信息:" + e, null, TaskStatusEnum.FAIL.getStatus(), new Date(), timer.interval());
        } finally {
            releaseResources();
            ThreadCore.removeThread(getJobId());
        }
    }

    private void executePreScriptContent(TaskJobInfo taskInfo) throws Exception {
        if (StringUtils.isNotBlank(taskInfo.getPreScriptContent())) {
            PreScriptContent preScriptContent = JsonKit.parseObject(taskInfo.getPreScriptContent(), PreScriptContent.class);
            DbInfo dbInfo = new DbInfo();
            BeanUtils.copyProperties(preScriptContent, dbInfo);
            try {
                switch (DbEnum.getByDriverClassName(preScriptContent.getJdbcDrive())) {
                    case SPARK:
                    case HIVE:
                        DataSource ds = DbFactory.getDataSource(dbInfo);
                        DbKit.use(ds, dbInfo.getJdbcDrive()).execute(preScriptContent.getScript());
                        break;
                }
            } catch (Exception e) {
                DbFactory.destroyDataSource(dbInfo);
                throw new Exception("创建分区失败:" + e.getMessage());
            }
        }
    }

    private void releaseResources() {
        if (executor != null && executor instanceof DefaultExecutor) {
            try {
                executor = null;
            } catch (Exception e) {
                System.err.println("释放执行器资源时出错: " + e.getMessage());
            }
        }
    }

    @Override
    public void onProcessComplete(int exitValue) {
        if (exitValue == 0) {
            callBack.completed(getJobId(), getApplicationId(), "任务运行成功,耗时:" + timer.interval() + "豪秒", null, TaskStatusEnum.SUCCED.getStatus(), new Date(), timer.interval());
        } else {
            callBack.completed(getJobId(), getApplicationId(), TaskStatusEnum.TASK_STOP.getDesc(), null, TaskStatusEnum.TASK_STOP.getStatus(), new Date(), timer.interval());
        }
    }

    @Override
    public void onProcessFailed(ExecuteException e) {
        callBack.completed(getJobId(), getApplicationId(), "任务运行失败," + e, null, TaskStatusEnum.FAIL.getStatus(), new Date(), timer.interval());
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
