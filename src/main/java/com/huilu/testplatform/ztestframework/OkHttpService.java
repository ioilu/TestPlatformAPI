package com.huilu.testplatform.ztestframework;

import com.huilu.testplatform.entity.dao.*;
import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.*;
import com.huilu.testplatform.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OkHttpService {
    @Autowired
    JobLogMapper jobLogMapper;

    @Autowired
    JobMapper jobMapper;


    OkHttpUtil okHttpUtil = new OkHttpUtil();

    public String runSingleCase(String host, TestCases testCases){
        JobLog jobLog = new JobLog();
        String result = "";
        String method = testCases.getMethod();
        OkHttpUtil okHttpUtil = new OkHttpUtil();
        Map<String, Object> postParamMap = null;

        result = okHttpUtil.doHttpRequest(host,testCases);
        jobLog.setPlanid(0);
        jobLog.setCaseid(testCases.getId());
        jobLog.setStatus(1);
        jobLog.setResponse(result);
        jobLog.setJobid(0);
        jobLog.setCreatetime(DateUtil.getDate());
        jobLogMapper.addJobLog(jobLog);
        return result;
    }

    /**
     *
     * @param plannedCasesForExecute
     * @param mapper
     * 解决动态获取数据库信息的问题，老版本当用例、计划、服务修改之后无法动态获取最新的消息
     */
    public void runForJobV2(PlannedCasesForExecute plannedCasesForExecute, Map<String , Object> mapper){
        //获取最新的service信息并赋值给PlannedCasesForExecute对象
        ServiceManagementMapper serviceManagementMapper = (ServiceManagementMapper) mapper.get("ServiceManagementMapper");
        ServiceManagement serviceManagement = serviceManagementMapper.getOneService(plannedCasesForExecute.getServiceManagement().getId());
        plannedCasesForExecute.setServiceManagement(serviceManagement);
        //获取最新的plan信息并赋值给PlannedCasesForExecute对象
        TestPlanMapper testPlanMapper = (TestPlanMapper) mapper.get("TestPlanMapper");
        TestPlan testPlan = testPlanMapper.getOnePlan(plannedCasesForExecute.getTestPlan().getId());
        plannedCasesForExecute.setTestPlan(testPlan);
        //获取最新的case信息并赋值给PlannedCasesForExecute对象
        PlannedCasesMapper plannedCasesMapper = (PlannedCasesMapper) mapper.get("PlannedCasesMapper");
        List<PlannedCases> plannedCases = plannedCasesMapper.getByPlan(plannedCasesForExecute.getTestPlan().getId());
        TestCasesMapper testCasesMapper = (TestCasesMapper) mapper.get("TestCasesMapper");
        List<TestCases> testCasesList = testCasesMapper.getCasesByIDs(plannedCases);
        plannedCasesForExecute.setTestCasesList(testCasesList);

        runForJob(plannedCasesForExecute,(JobMapper) mapper.get("JobMapper"),(JobLogMapper) mapper.get("JobLogMapper"));
    }



    public void runForJob(PlannedCasesForExecute plannedCasesForExecute, JobMapper jobMapper, JobLogMapper jobLogMapper){
//        System.out.println(Thread.currentThread().getName()+ "执行任务：" + plannedCasesForExecute.getTestPlan().getPlanname());
        JobLog jobLog = new JobLog();
        com.huilu.testplatform.entity.dao.Job job = new com.huilu.testplatform.entity.dao.Job();
        String host = plannedCasesForExecute.getServiceManagement().getHost();
        String result = "";
        if (plannedCasesForExecute.getTestPlan().getStatus() == 1) {
            String date = DateUtil.getDate();
            job.setJobname(plannedCasesForExecute.getTestPlan().getPlanname()+DateUtil.getDate());
            job.setCreatetime(date);
            job.setPlanid(plannedCasesForExecute.getTestPlan().getId());
            jobMapper.addJob(job);
            int jobId = job.getId();
            for (TestCases testCases : plannedCasesForExecute.getTestCasesList()){
//                System.out.println(Thread.currentThread().getName() + ":" + plannedCasesForExecute.getServiceManagement().getName() + plannedCasesForExecute.getTestPlan().getPlanname()+testCases.getId());
                System.out.printf("参数：host : %s ,api : %s , params: %s",plannedCasesForExecute.getServiceManagement().getHost() , testCases.getApi() , testCases.getParams());
                result = okHttpUtil.doHttpRequest(host,testCases);
                jobLog.setPlanid(plannedCasesForExecute.getTestPlan().getId());
                jobLog.setCaseid(testCases.getId());
                jobLog.setStatus(1);
                jobLog.setResponse(result);
                jobLog.setJobid(jobId);
                jobLog.setCreatetime(DateUtil.getDate());
                jobLogMapper.addJobLog(jobLog);
            }
        }
    }


}
