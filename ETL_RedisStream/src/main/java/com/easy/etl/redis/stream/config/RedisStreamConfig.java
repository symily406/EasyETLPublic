// Redis Stream配置类
package com.easy.etl.redis.stream.config;

import com.easy.etl.redis.stream.consumer.MessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisStreamConfig {

    private String streamKey = "sample-stream";

    private String consumerGroup = "sample-group";

    private String consumerPrefix = "consumer-";

    private int concurrency = 5;

    @Resource
    private RedisConnectionFactory connectionFactory;

    @Resource
    private MessageConsumer messageConsumer;

    private List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> containers = new ArrayList<>();

    @Bean
    public List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> streamMessageListenerContainers() {
        List<StreamMessageListenerContainer<String, ObjectRecord<String, String>>> containers = new ArrayList<>();

        // 确保消费者组存在
        createConsumerGroup(streamKey, consumerGroup);

        // 创建多个消费者容器实现并发处理
        for (int i = 0; i < concurrency; i++) {
            StreamMessageListenerContainer<String, ObjectRecord<String, String>> container =
                    createConsumerContainer("consumer-" + i);
            containers.add(container);
        }

        // 启动所有容器
        containers.forEach(StreamMessageListenerContainer::start);

        this.containers.addAll(containers);
        return containers;
    }

    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> createConsumerContainer(String consumerName) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofMillis(500)) // 轮询超时时间
                        .targetType(String.class)
                        .batchSize(10) // 批处理大小
                        .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer =
                StreamMessageListenerContainer.create(connectionFactory, options);

        listenerContainer.receive(
                Consumer.from(consumerGroup, consumerPrefix + consumerName),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()),
                messageConsumer);

        return listenerContainer;
    }

    @PreDestroy
    public void destroy() {
        containers.forEach(container -> {
            try {
                container.stop();
            } catch (Exception e) {
                System.err.println("Error stopping container: " + e.getMessage());
            }
        });
    }

    private void createConsumerGroup(String streamKey, String consumerGroup) {
        try {
            // 尝试创建消费者组，如果流不存在则创建流
            connectionFactory.getConnection().streamCommands().xGroupCreate(
                    streamKey.getBytes(), consumerGroup, ReadOffset.from("0"), true);
            System.out.println("Created consumer group: " + consumerGroup);
        } catch (Exception e) {
            // 检查是否是因为消费者组已存在导致的异常
            if (e.getMessage().contains("BUSYGROUP")) {
                System.out.println("Consumer group already exists: " + consumerGroup);
            } else {
                System.err.println("Failed to create consumer group: " + e.getMessage());
                throw new RuntimeException("Failed to create consumer group", e);
            }
        }
    }
}
