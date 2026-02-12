package com.easy.etl.redis.stream.handler;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.core.init.Config;
import com.easy.etl.redis.stream.consumer.MachineConsumer;
import com.easy.etl.redis.stream.consumer.ResultConsumer;
import com.easy.etl.redis.stream.container.BaseContainer;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.spring.boot.ext.kit.IpKit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedisStreamServerListener extends BaseContainer {
    private List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> totalContainers = new ArrayList<>();
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = null;
    @Resource
    private RedisStreamService redisStreamService;

    @Resource
    private ResultConsumer resultConsumer;

    @Resource
    private MachineConsumer machineConsumer;


    /**
     * 创建任务执行队列
     */
    @Bean
    public List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> resultMessageListenerContainers() {
        List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> containers = new ArrayList<>();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = getListenerContainer();
        listenerContainer.receive(
                Consumer.from(GlobalConstant.consumerResultGroup, GlobalConstant.consumerResultGroup.concat(GlobalConstant.consumerName).concat(IpKit.getIp(System.getProperty("computer.ip")))),
                StreamOffset.create(GlobalConstant.streamResultkey, ReadOffset.lastConsumed()),
                resultConsumer);
        listenerContainer.start();
        containers.add(listenerContainer);
        totalContainers.addAll(containers);
        return containers;
    }

    /**
     * 创建任务执行队列
     */
    @Bean
    public List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> machineMessageListenerContainers() {
        List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> containers = new ArrayList<>();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = getListenerContainer();
        listenerContainer.receive(
                Consumer.from(GlobalConstant.consumerMachineGroup, GlobalConstant.consumerMachineGroup.concat(GlobalConstant.consumerName).concat(IpKit.getIp(System.getProperty("computer.ip")))),
                StreamOffset.create(GlobalConstant.streamMachinekey, ReadOffset.lastConsumed()),
                machineConsumer);
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
