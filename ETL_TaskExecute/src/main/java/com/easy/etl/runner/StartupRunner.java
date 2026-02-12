package com.easy.etl.runner;


import com.easy.etl.scheduled.MonitorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private MonitorHandler monitorHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        jobScheduler.scheduleRecurrently(Cron.minutely(), () -> {
//            monitorHandler.run();
//        });
    }
}
