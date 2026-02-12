package com.easy.etl.redis.stream.consumer;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.em.TaskStatusEnum;
import com.easy.etl.runTaskJob.service.IRunTaskJobService;
import com.easy.etl.rabbitmq.task.TaskJobResult;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

import javax.annotation.Resource;

public class ResultConsumer implements StreamListener<String, ObjectRecord<String, String>> {
    private Logger LOG = LoggerFactory.getLogger(ResultConsumer.class);
    @Resource
    private RedisStreamService redisStreamService;
    @Resource
    private IRunTaskJobService iRunTaskJobService;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        String stream = message.getStream();
        try {
            String messageBody = message.getValue();
            LOG.info("接收到执行结果消息：{}", messageBody);
            TaskJobResult result = JsonKit.parseObject(messageBody, TaskJobResult.class);
            iRunTaskJobService.saveJobResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redisStreamService.acknowledgeAndDelete(stream, GlobalConstant.consumerResultGroup, message);
        }
    }
}
