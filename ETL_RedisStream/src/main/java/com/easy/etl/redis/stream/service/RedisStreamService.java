package com.easy.etl.redis.stream.service;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.redis.stream.consumer.MessageConsumer;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

@Service
public class RedisStreamService {

    @Resource
    private RedisConnectionFactory connectionFactory;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    @Qualifier(GlobalConstant.executorThreadPool)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 发送消息
     *
     * @param stream
     * @param object
     * @return
     */
    public String send(String stream, Object object) {
        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .ofObject(JsonKit.toJsonString(object))
                .withStreamKey(stream);
        return stringRedisTemplate.opsForStream()
                .add(record)
                .toString();
    }

    /**
     * 获取消费者组的待处理消息摘要
     */
    public long getPendingMessagesSummary(String streamKey, String consumerGroup) {
        try {
            PendingMessagesSummary summary = stringRedisTemplate.opsForStream()
                    .pending(streamKey, consumerGroup);
            return summary.getTotalPendingMessages();
        } catch (Exception e) {
            throw new RuntimeException("获取待处理消息失败", e);
        }
    }

    /**
     * 确认消息并删除
     *
     * @param stream        Stream名称
     * @param consumerGroup 消费者组
     * @param message       消息ID
     */
    public void acknowledgeAndDelete(String stream, String consumerGroup, ObjectRecord<String, String> message) {
        try {
            stringRedisTemplate.opsForStream().acknowledge(stream, consumerGroup, message.getId());
            stringRedisTemplate.opsForStream().delete(stream, message.getId());
        } catch (Exception e) {
            throw new RuntimeException("确认并删除消息失败", e);
        }
    }


    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> mcreateConsumerContainer(String streamKey, String consumerGroup) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofMillis(GlobalConstant.redisStreamPollTimeout))
                        .targetType(String.class)
                        .batchSize(GlobalConstant.redisStreamBatchSize)
                        .executor(threadPoolTaskExecutor)
                        .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer =
                StreamMessageListenerContainer.create(connectionFactory, options);
        try {
            connectionFactory.getConnection().streamCommands().xGroupCreate(
                    streamKey.getBytes(), consumerGroup, ReadOffset.from("0"), true);
        } catch (Exception e) {
            System.out.println("消费者组已存在,无需创建");
        }
        return listenerContainer;
    }
}
