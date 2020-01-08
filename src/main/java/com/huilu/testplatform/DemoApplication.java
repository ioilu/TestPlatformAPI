package com.huilu.testplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@MapperScan("com.huilu.testplatform.mapper") //扫描的mapper
@SpringBootApplication
@EnableScheduling
@Configuration
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		return schedulerFactoryBean;
	}


}