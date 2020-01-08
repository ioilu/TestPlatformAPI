package com.huilu.testplatform.zmyquartz;

import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.*;
import com.huilu.testplatform.service.GetAllInfoForJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InterfaceAutoTestScheduler {
    @Autowired
    JobMapper jobMapper;

    @Autowired
    JobLogMapper jobLogMapper;

    @Autowired
    ServiceManagementMapper serviceManagementMapper;

    @Autowired
    TestCasesMapper testCasesMapper;

    @Autowired
    TestPlanMapper testPlanMapper;

    @Autowired
    GetAllInfoForJob getAllInfoForJob;

    @Autowired
    PlannedCasesMapper plannedCasesMapper;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    private Scheduler scheduler;

    public List<PlannedCasesForExecute> plannedCasesForExecuteList;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void addCronJobs() throws SchedulerException {
        scheduler = schedulerFactoryBean.getScheduler();
        plannedCasesForExecuteList = new ArrayList<PlannedCasesForExecute>();
        plannedCasesForExecuteList = getAllInfoForJob.getInfo();
        if (plannedCasesForExecuteList == null || plannedCasesForExecuteList.size() < 1) {
            return;
        }
        for (PlannedCasesForExecute plannedCasesForExecute : plannedCasesForExecuteList) {
            this.addCronInterfaceAutoTestJob(plannedCasesForExecute);
        }

    }

    public void addCronInterfaceAutoTestJob(PlannedCasesForExecute plannedCasesForExecute) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(CronInterfaceAutoTestJob.class).withIdentity(plannedCasesForExecute.getTestPlan().getPlanname(), plannedCasesForExecute.getServiceManagement().getName()).build();
//            给job传入参数
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.putIfAbsent("JobMapper",jobMapper);
        jobDataMap.putIfAbsent("JobLogMapper",jobLogMapper);
        jobDataMap.putIfAbsent("ServiceManagementMapper",serviceManagementMapper);
        jobDataMap.putIfAbsent("TestCasesMapper",testCasesMapper);
        jobDataMap.putIfAbsent("TestPlanMapper",testPlanMapper);
        jobDataMap.putIfAbsent("PlannedCasesMapper", plannedCasesMapper);

        jobDataMap.putIfAbsent("param", plannedCasesForExecute);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(plannedCasesForExecute.getTestPlan().getSchedule());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(plannedCasesForExecute.getTestPlan().getPlanname(), plannedCasesForExecute.getServiceManagement().getName())
                .withSchedule(scheduleBuilder).build();
        this.scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public void addInstantInterfaceAutoTestJob(PlannedCasesForExecute plannedCasesForExecute) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(InstantInterfaceAutoTestJob.class).withIdentity(plannedCasesForExecute.getTestPlan().getPlanname(), plannedCasesForExecute.getServiceManagement().getName()).build();
//            给job传入参数
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.putIfAbsent("JobMapper",jobMapper);
        jobDataMap.putIfAbsent("JobLogMapper",jobLogMapper);
        jobDetail.getJobDataMap().putIfAbsent("param", plannedCasesForExecute);
        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(plannedCasesForExecute.getTestPlan().getPlanname(), plannedCasesForExecute.getServiceManagement().getName())
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
                .build();
        System.out.println("==============================================================================");
        System.out.println("===============================  即时任务开始  ===============================");
        System.out.println("==============================================================================");
        this.scheduler.scheduleJob(jobDetail, simpleTrigger);
    }

}
