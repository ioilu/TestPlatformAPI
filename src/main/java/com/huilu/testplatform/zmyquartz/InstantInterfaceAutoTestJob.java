package com.huilu.testplatform.zmyquartz;

import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.*;
import com.huilu.testplatform.ztestframework.OkHttpService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Configuration
public class InstantInterfaceAutoTestJob implements Job {

    private PlannedCasesForExecute plannedCasesForExecute;
    private JobMapper jobMapper;
    private JobLogMapper jobLogMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        OkHttpService okHttpService = new OkHttpService();

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.plannedCasesForExecute = (PlannedCasesForExecute) jobExecutionContext.getJobDetail().getJobDataMap().get("param");
        this.jobMapper = (JobMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("JobMapper");
        this.jobLogMapper = (JobLogMapper) jobExecutionContext.getJobDetail().getJobDataMap().get("JobLogMapper");
//        System.out.println(DateUtil.getDate()+ "              " + plannedCasesForExecute + "                  " + Thread.currentThread().getName());

        okHttpService.runForJob(plannedCasesForExecute, jobMapper, jobLogMapper);
    }
}
