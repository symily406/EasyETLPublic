package com.easy.etl.scheduled;

import com.easy.etl.core.init.Monitor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MonitorHandler {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void run() {
        Monitor.monitor(stringRedisTemplate);
    }
}
