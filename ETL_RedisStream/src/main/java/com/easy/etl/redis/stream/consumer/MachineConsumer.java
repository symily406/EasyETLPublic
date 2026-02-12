package com.easy.etl.redis.stream.consumer;

import com.easy.etl.core.constant.GlobalConstant;
import com.easy.etl.machine.model.MachineInfo;
import com.easy.etl.machine.service.IMachineService;
import com.easy.etl.redis.stream.service.RedisStreamService;
import com.spring.boot.ext.kit.jackson.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

import javax.annotation.Resource;

public class MachineConsumer implements StreamListener<String, ObjectRecord<String, String>> {
    @Resource
    private RedisStreamService redisStreamService;
    @Autowired
    private IMachineService machineService;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        String stream = message.getStream();
        String messageBody = message.getValue();
        try {
            MachineInfo machineInfo = JsonKit.parseObject(messageBody, MachineInfo.class);
            machineService.saveOrUpdateMachine(machineInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisStreamService.acknowledgeAndDelete(stream, GlobalConstant.consumerMachineGroup, message);
        }
    }
}
