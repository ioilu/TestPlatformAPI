package com.huilu.testplatform.zmyquartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class SchedulerConfiguration implements ApplicationListener<ContextRefreshedEvent>{
    @Autowired
    InterfaceAutoTestScheduler interfaceAutoTestScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            interfaceAutoTestScheduler.addCronJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
