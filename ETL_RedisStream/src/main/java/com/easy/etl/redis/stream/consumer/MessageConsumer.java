package com.easy.etl.redis.stream.consumer;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.machine.model.Sys;
import com.easy.etl.redis.stream.service.RedisStreamService;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class MessageConsumer implements StreamListener<String, ObjectRecord<String, String>> {
    @Resource
    private RedisStreamService redisStreamService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        try {
            String stream = message.getStream();
            String messageId = message.getId().getValue();
            String messageBody = message.getValue();

            String consumerName = Thread.currentThread().getName();

            System.out.println("==================================");
            System.out.println("Consumer [" + consumerName + "] received message from Stream '" + stream + "'");
            System.out.println("Message ID: " + messageId);
            System.out.println("Message body: " + messageBody);


            redisTemplate.opsForStream().acknowledge(stream, "sample-group", message.getId());
            redisTemplate.opsForStream().delete(stream, message.getId());

            System.out.println("Message " + messageId + " processed, acknowledged and deleted successfully");

        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
