package com.easy.etl.redis.stream.container;

import com.easy.etl.core.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;

public class BaseContainer {
    @Resource
    protected RedisConnectionFactory connectionFactory;

    @Resource
    @Qualifier(GlobalConstant.executorThreadPool)
    protected ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 创建任务执行队列
     */
    @Bean
    public void initRrunnerContainers() {
        try {
            connectionFactory.getConnection().streamCommands().xGroupCreate(
                    GlobalConstant.streamRunnerkey.getBytes(),
                    GlobalConstant.consumerRunnerGroup,
                    ReadOffset.from("0"),
                    true);
            System.out.println("成功创建消费者组: " + GlobalConstant.consumerRunnerGroup);
        } catch (Exception e) {
            System.out.println("消费者组已存在: " + GlobalConstant.consumerRunnerGroup);
        }
    }

    /**
     * 创建任务执行队列
     */
    @Bean
    public void initKillContainers() {
        try {
            connectionFactory.getConnection().streamCommands().xGroupCreate(
                    GlobalConstant.streamKillkey.getBytes(),
                    GlobalConstant.consumerKillGroup,
                    ReadOffset.from("0"),
                    true);
            System.out.println("成功创建消费者组: " + GlobalConstant.consumerKillGroup);
        } catch (Exception e) {
            System.out.println("消费者组已存在: " + GlobalConstant.consumerKillGroup);
        }
    }

    /**
     * 创建任务执行队列
     */
    @Bean
    public void initResultContainers() {
        try {
            connectionFactory.getConnection().streamCommands().xGroupCreate(
                    GlobalConstant.streamResultkey.getBytes(),
                    GlobalConstant.consumerResultGroup,
                    ReadOffset.from("0"),
                    true);
            System.out.println("成功创建消费者组: " + GlobalConstant.consumerResultGroup);
        } catch (Exception e) {
            System.out.println("消费者组已存在: " + GlobalConstant.consumerResultGroup);
        }
    }

    /**
     * 创建任务执行队列
     */
    @Bean
    public void initMachineContainers() {
        try {
            connectionFactory.getConnection().streamCommands().xGroupCreate(
                    GlobalConstant.streamMachinekey.getBytes(),
                    GlobalConstant.consumerMachineGroup,
                    ReadOffset.from("0"),
                    true);
            System.out.println("成功创建消费者组: " + GlobalConstant.consumerResultGroup);
        } catch (Exception e) {
            System.out.println("消费者组已存在: " + GlobalConstant.consumerResultGroup);
        }
    }

    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> getListenerContainer() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofMillis(GlobalConstant.redisStreamPollTimeout))
                        .targetType(String.class)
                        .batchSize(GlobalConstant.redisStreamBatchSize) // 启用批处理
                        .executor(threadPoolTaskExecutor) // 使用线程池
                        .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer =
                StreamMessageListenerContainer.create(connectionFactory, options);
        return listenerContainer;
    }
}
