package com.easy.etl.utils;

import cn.hutool.core.io.FileUtil;
import com.easy.etl.core.init.Config;
import com.easy.etl.em.ExecuteTaskTypeEnum;
import com.easy.etl.rabbitmq.task.TaskJobInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JobUtils {

    /**
     * 创建脚本文件
     *
     * @param taskJobInfo
     */
    public static TaskJobInfo createScriptFile(TaskJobInfo taskJobInfo) {
        switch (ExecuteTaskTypeEnum.getExecuteTaskType(taskJobInfo.getTaskType())) {
            case DATAX_TASK://数据集成
                taskJobInfo.setScriptPath(taskJobInfo.getDatax().getScriptPath());
                break;
            case SQL_TASK://脚本
                taskJobInfo.setScriptPath(taskJobInfo.getSql().getScriptPath());
                break;
        }
        String folder = String.format("%s/%s", taskJobInfo.getScriptPath(), DateFormatUtils.format(new Date(), "yyyyMMdd"));
        FileUtil.mkdir(folder);
        String scriptFile = String.format("%s/%s.%s", folder, taskJobInfo.getJobId(), "json");
        FileUtil.writeString(taskJobInfo.getScriptContent(), scriptFile, Config.charset);
        taskJobInfo.setScriptFile(scriptFile);
        return taskJobInfo;
    }

    public static String syncCommand(TaskJobInfo taskJobInfo) {
        StringBuilder sync = new StringBuilder();
        sync.append(" -server ").append(" -Xms").append(taskJobInfo.getDatax().getDataxJvm()).append("m").append(" -Xmx").append(taskJobInfo.getDatax().getDataxJvm()).append("m");
        sync.append(" -XX:+HeapDumpOnOutOfMemoryError").append(" -Xms").append(taskJobInfo.getDatax().getDataxJvm()).append("m").append(" -Xmx").append(taskJobInfo.getDatax().getDataxJvm()).append("m");
        sync.append(" -XX:+HeapDumpOnOutOfMemoryError").append(" -XX:HeapDumpPath=").append(taskJobInfo.getDatax().getDataxPath()).append("/log");
        sync.append(" -Dloglevel=info -Dfile.encoding=UTF-8 -Dlogback.statusListenerClass=ch.qos.logback.core.status.NopStatusListener -Djava.security.egd=file:///dev/urandom");
        sync.append(" -Dlog.file.name=").append(taskJobInfo.getJobId());
        sync.append(" -Ddatax.home=").append(taskJobInfo.getDatax().getDataxPath());
        sync.append(" -Dlogback.configurationFile").append(taskJobInfo.getDatax().getDataxPath()).append("/conf/logback.xml");
        sync.append(" -classpath").append(" ").append(taskJobInfo.getDatax().getDataxPath());
        if (SystemUtils.IS_OS_WINDOWS) {
            sync.append("/lib/*");
        } else {
            sync.append("/lib/*:.");
        }
        sync.append(" com.alibaba.datax.core.Engine");
        sync.append(" -mode standalone");
        sync.append(" -jobid").append(" ").append(taskJobInfo.getJobId());
        sync.append(" -job").append(" ").append(taskJobInfo.getScriptFile());
        return sync.toString();
    }

    public static String[] yarnCommand(TaskJobInfo taskJobInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(taskJobInfo.getBase().getJdkPath());
        List<String> commands = new ArrayList<>();
        commands.add("--jar");
        commands.add(taskJobInfo.getYarn().getYarnPath());
        commands.add("--shell_command");
        switch (ExecuteTaskTypeEnum.getExecuteTaskType(taskJobInfo.getTaskType())) {
            case DATAX_TASK://数据集成
                sb.append(syncCommand(taskJobInfo));
                commands.add(sb.toString());
                commands.add("--appname");
                commands.add(taskJobInfo.getDatax().getDataxAppName());
                commands.add("--queue");
                commands.add(taskJobInfo.getDatax().getDataxQueue());
                break;
            case SQL_TASK://脚本
                sb.append(" -server -Dfile.encoding=UTF-8");
                sb.append(" -Dlogback.configurationFile=");
                sb.append(taskJobInfo.getSql().getLogbackPath());
                sb.append(" -Djava.security.egd=file:///dev/urandom ");
                sb.append(" -Dlog.home=").append(taskJobInfo.getSql().getLogPath());
                sb.append(" -Xms").append(taskJobInfo.getSql().getExecuteXms()).append("m").append(" -Xmx").append(taskJobInfo.getSql().getExecuteXms()).append("m");
                sb.append(" -cp ").append(taskJobInfo.getSql().getExecutePath());
                sb.append(":").append(taskJobInfo.getSql().getLibPath());
                sb.append(" ").append(taskJobInfo.getSql().getMainClass()).append(" ");
                sb.append(" -jobId ").append("'").append(taskJobInfo.getJobId()).append("'");
                sb.append(" -taskId ").append("'").append(taskJobInfo.getTaskId()).append("'");
                sb.append(" -dbSourceId ").append("'").append(taskJobInfo.getDbSourceId()).append("'");
                sb.append(" -jdbcUrl ").append("'").append(taskJobInfo.getJdbcUrl()).append("'");
                sb.append(" -jdbcDrive ").append(taskJobInfo.getJdbcDrive());
                sb.append(" -userName ").append("'").append(taskJobInfo.getUserName()).append("'");
                sb.append(" -password ").append("'").append(taskJobInfo.getPassword()).append("'");
                sb.append(" -scriptFilePath ").append("'").append(taskJobInfo.getScriptFile()).append("'");
                sb.append(" -hasResult ").append(taskJobInfo.getHasResult());
                sb.append(" -ip ").append(taskJobInfo.getIp());
                sb.append(" -redisHost ").append(taskJobInfo.getRedis().getRedisHost());
                sb.append(" -redisPort ").append(taskJobInfo.getRedis().getRedisPort());
                if (StringUtils.isNotBlank(taskJobInfo.getRedis().getRedisPassword())) {
                    sb.append(" -redisPassword ").append("'").append(taskJobInfo.getRedis().getRedisPassword()).append("'");
                }
                sb.append(" -redisDatabase ").append(taskJobInfo.getRedis().getRedisDatabase());
                sb.append(" -redisTimeout ").append(taskJobInfo.getRedis().getRedisTimeout());
                sb.append(" -redisMaxTotal ").append(taskJobInfo.getRedis().getRedisMaxTotal());
                sb.append(" -redisMaxIdle ").append(taskJobInfo.getRedis().getRedisMaxIdle());
                sb.append(" -redisMinIdle ").append(taskJobInfo.getRedis().getRedisMinIdle());
                commands.add(sb.toString());
                commands.add("--appname");
                commands.add(taskJobInfo.getSql().getExecuteAppName());
                commands.add("--queue");
                commands.add(taskJobInfo.getSql().getExecuteQueue());
                break;
            default:
        }
        commands.add("--num_containers");
        commands.add(taskJobInfo.getYarn().getNumContainers().toString());
        commands.add("--container_memory");
        commands.add(taskJobInfo.getYarn().getContainerMemory().toString());
        commands.add("--container_vcores");
        commands.add(taskJobInfo.getYarn().getContainerVcores().toString());
        commands.add("--master_memory");
        commands.add(taskJobInfo.getYarn().getMasterMemory().toString());
        commands.add("--master_vcores");
        commands.add(taskJobInfo.getYarn().getMasterVcores().toString());
        commands.add("--priority");
        commands.add(taskJobInfo.getYarn().getPriority().toString());
        System.out.println(sb.toString());
        return Arrays.copyOf(commands.toArray(), commands.size(), String[].class);
    }
}
