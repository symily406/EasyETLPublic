package com.easy.etl.redis.stream.consumer;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.execute.entity.ExecuteJob;
import com.easy.etl.execute.service.IExecuteJobService;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

import javax.annotation.Resource;

public class RunnerConsumer implements StreamListener<String, ObjectRecord<String, String>> {
    @Resource
    private RedisStreamService redisStreamService;
    @Resource
    private IExecuteJobService executeJobService;


    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        String stream = message.getStream();
        try {
            String messageBody = message.getValue();
            ExecuteJob executeJob = JsonKit.parseObject(messageBody, ExecuteJob.class);
            executeJobService.saveOrupdate(executeJob);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisStreamService.acknowledgeAndDelete(stream, GlobalConstant.consumerRunnerGroup, message);
        }
    }
}
