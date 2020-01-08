package com.huilu.testplatform.zmyquartz;


import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerSerivce {
    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    private Scheduler scheduler;


    public void pauseJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobName);
        try {
            this.scheduler = schedulerFactoryBean.getScheduler();
            this.scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addJob(String jobName, String jobGroupName,
                              String triggerName, String triggerGroupName, Class jobClass,
                              String cron) {
        this.scheduler = schedulerFactoryBean.getScheduler();
        try {
            JobDetail job = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)
                    .build();
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            // 按新的cronExpression表达式构建一个新的trigger
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(job, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void addInstantInterfaceAutoTestJob(PlannedCasesForExecute plannedCasesForExecute) throws SchedulerException {
        this.scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(CronInterfaceAutoTestJob.class).withIdentity(plannedCasesForExecute.getTestPlan().getPlanname(), plannedCasesForExecute.getServiceManagement().getName()).build();
//            给job传入参数
        jobDetail.getJobDataMap().putIfAbsent("param", plannedCasesForExecute);
        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .build();

        this.scheduler.scheduleJob(jobDetail, simpleTrigger);

    }

}
