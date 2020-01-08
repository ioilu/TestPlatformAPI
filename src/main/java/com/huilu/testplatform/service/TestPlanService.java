package com.huilu.testplatform.service;

import com.huilu.testplatform.entity.dao.PlannedCases;
import com.huilu.testplatform.entity.dao.ServiceManagement;
import com.huilu.testplatform.entity.dao.TestCases;
import com.huilu.testplatform.entity.dao.TestPlan;
import com.huilu.testplatform.entity.service.PlannedCasesForExecute;
import com.huilu.testplatform.mapper.PlannedCasesMapper;
import com.huilu.testplatform.mapper.ServiceManagementMapper;
import com.huilu.testplatform.mapper.TestCasesMapper;
import com.huilu.testplatform.mapper.TestPlanMapper;
import com.huilu.testplatform.zmyquartz.UpdateTestCasesForExceuteSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPlanService {
    @Autowired
    UpdateTestCasesForExceuteSchedule updateTestCasesForExceuteSchedule;

    @Autowired
    TestPlanMapper testPlanMapper;

    @Autowired
    ServiceManagementMapper serviceManagementMapper;

    @Autowired
    PlannedCasesMapper plannedCasesMapper;

    @Autowired
    TestCasesMapper testCasesMapper;

    public boolean checkParams(TestPlan testPlan){
        boolean flag = true;
        if (testPlan.getServiceid()<=0 || testPlan.getSchedule() == null || testPlan.getPlanname() == null){
            flag = false;
        }
        return flag;
    }

    public int update(TestPlan testPlan){
        int result = testPlanMapper.update(testPlan);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();;
        return result;
    }

    public int addPlan(TestPlan testPlan){
        int result = testPlanMapper.addPlan(testPlan);
        updateTestCasesForExceuteSchedule.updateTestCasesForExceuteSchedule();
        return result;
    }

    public PlannedCasesForExecute getCasesForExcecute(int planId){
        TestPlan testPlan = testPlanMapper.getOnePlan(planId);
        ServiceManagement serviceManagement = serviceManagementMapper.getOneService(testPlan.getServiceid());
        List<PlannedCases> plannedCasesList = plannedCasesMapper.getByPlan(planId);
        List<TestCases> testCasesList = testCasesMapper.getCasesByIDs(plannedCasesList);
        PlannedCasesForExecute plannedCasesForExecute = new PlannedCasesForExecute();
        plannedCasesForExecute.setServiceManagement(serviceManagement);
        plannedCasesForExecute.setTestCasesList(testCasesList);
        plannedCasesForExecute.setTestPlan(testPlan);
        return plannedCasesForExecute;
    }

}
