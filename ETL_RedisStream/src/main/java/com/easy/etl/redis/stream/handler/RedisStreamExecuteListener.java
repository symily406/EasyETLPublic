package com.easy.etl.redis.stream.handler;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Config;
import com.easy.etl.redis.stream.consumer.KillConsumer;
import com.easy.etl.redis.stream.consumer.MessageConsumer;
import com.easy.etl.redis.stream.consumer.RunnerConsumer;
import com.easy.etl.redis.stream.container.BaseContainer;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.spring.boot.ext.kit.IpKit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RedisStreamExecuteListener extends BaseContainer {
    private List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> totalContainers = new ArrayList<>();
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = null;
    @Resource
    private RedisStreamService redisStreamService;

    @Resource
    private RunnerConsumer runnerConsumer;

    @Resource
    private KillConsumer killConsumer;


    @Resource
    private MessageConsumer messageConsumer;


    /**
     * 创建任务执行队列
     */
    @Bean
    public List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> runnerMessageListenerContainers() {
        List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> containers = new ArrayList<>();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = getListenerContainer();
        listenerContainer.receive(
                Consumer.from(GlobalConstant.consumerRunnerGroup, GlobalConstant.consumerRunnerGroup.concat(GlobalConstant.consumerName).concat(IpKit.getIp(System.getProperty("computer.ip")))),
                StreamOffset.create(GlobalConstant.streamRunnerkey, ReadOffset.lastConsumed()),
                runnerConsumer);
        listenerContainer.start();
        containers.add(listenerContainer);
        totalContainers.addAll(containers);
        return containers;
    }


    /**
     * 创建任务执行队列
     */
    @Bean
    public List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> killMessageListenerContainers() {
        List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> containers = new ArrayList<>();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = getListenerContainer();
        listenerContainer.receive(
                Consumer.from(GlobalConstant.consumerKillGroup, GlobalConstant.consumerKillGroup.concat(GlobalConstant.consumerName).concat(IpKit.getIp(System.getProperty("computer.ip")))),
                StreamOffset.create(GlobalConstant.streamKillkey, ReadOffset.lastConsumed()),
                killConsumer);
        listenerContainer.start();
        containers.add(listenerContainer);
        totalContainers.addAll(containers);
        return containers;
    }

    @PreDestroy
    public void destroy() {
        totalContainers.forEach(StreamMessageListenerContainer::stop);
    }

}
