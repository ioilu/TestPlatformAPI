package com.huilu.testplatform.zmyquartz;

import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.*;
import com.huilu.testplatform.ztestframework.OkHttpService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CronInterfaceAutoTestJob implements Job {
    private PlannedCasesForExecute plannedCasesForExecute;

    private JobMapper jobMapper;
    private JobLogMapper jobLogMapper;
    private ServiceManagementMapper serviceManagementMapper;
    private TestCasesMapper testCasesMapper;
    private TestPlanMapper testPlanMapper;
    private PlannedCasesMapper plannedCasesMapper;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        OkHttpService okHttpService = new OkHttpService();
        this.plannedCasesForExecute = (PlannedCasesForExecute) jobExecutionContext.getJobDetail().getJobDataMap().get("param");
        this.jobMapper = (JobMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("JobMapper");
        this.jobLogMapper = (JobLogMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("JobLogMapper");
        this.serviceManagementMapper = (ServiceManagementMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("ServiceManagementMapper");
        this.testCasesMapper = (TestCasesMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("TestCasesMapper");
        this.testPlanMapper = (TestPlanMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("TestPlanMapper");
        this.plannedCasesMapper = (PlannedCasesMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("PlannedCasesMapper");


//        System.out.println(DateUtil.getDate()+ "              " + plannedCasesForExecute + "                  " + Thread.currentThread().getName());
        Map<String , Object> mapper = new HashMap<>();
        mapper.put("JobMapper",jobMapper);
        mapper.put("JobLogMapper",jobLogMapper);
        mapper.put("ServiceManagementMapper",serviceManagementMapper);
        mapper.put("TestCasesMapper",testCasesMapper);
        mapper.put("TestPlanMapper",testPlanMapper);
        mapper.put("PlannedCasesMapper",plannedCasesMapper);


        okHttpService.runForJobV2(plannedCasesForExecute,mapper);
    }

}
