package com.easy.etl.run;

import com.easy.etl.core.executor.ExecutorConfig;
import com.easy.etl.core.init.Initialization;
import com.easy.etl.core.scheduler.SchedulerConfig;
import com.easy.etl.redis.stream.consumer.KillConsumer;
import com.easy.etl.redis.stream.consumer.MessageConsumer;
import com.easy.etl.redis.stream.consumer.RunnerConsumer;
import com.easy.etl.redis.stream.handler.RedisStreamExecuteListener;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.easy.etl.runner.StartupRunner;
import com.easy.etl.scheduled.MonitorHandler;
import com.easy.etl.scheduled.TaskHandler;
import com.spring.boot.ext.plugin.cache.RedisConfigurer;
import com.spring.boot.ext.plugin.context.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableRabbit
@EnableScheduling
@SpringBootApplication
@Import({Application.class, Initialization.class, RedisConfigurer.class,
        StartupRunner.class, MonitorHandler.class, TaskHandler.class,
        ExecutorConfig.class,
        SchedulerConfig.class,
        RunnerConsumer.class,
        KillConsumer.class,
        RedisStreamService.class,
        MessageConsumer.class,
        RedisStreamExecuteListener.class
})
@MapperScan({
        "com.easy.etl.**.mapper",
        "com.easy.etl.**.entity"})
@ComponentScan({
        "com.easy.etl.**.controller",
        "com.easy.etl.**.service"
})
public class TaskExecuteApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication application = new SpringApplication(TaskExecuteApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
